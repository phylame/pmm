package pmm.pbm.service.cms;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.google.common.collect.Maps;

import lombok.Getter;
import lombok.NonNull;
import lombok.val;
import pmm.pbm.data.base.support.Paged;
import pmm.pbm.util.WebUtils;
import pmm.pbm.util.cms.HiddenField;
import pmm.pbm.util.cms.QueryField;
import pw.phylame.ycl.util.Function;
import pw.phylame.ycl.util.Reflections;
import pw.phylame.ycl.util.StringUtils;
import pw.phylame.ycl.value.Pair;

@Service
public class CMSService {
    private static final Logger logger = LoggerFactory.getLogger(CMSService.class);

    @Autowired
    private WebApplicationContext context;

    private final Map<String, ItemWorker> workers = Maps.newIdentityHashMap();

    @Transactional
    public ItemWorker getWorker(String tag) {
        ItemWorker worker = workers.get(tag);
        if (worker != null && worker.found) {
            return worker;
        }
        workers.put(tag, worker = new ItemWorker());

        worker.tag = tag;

        try {
            worker.service = context.getBean("cms" + StringUtils.capitalized(tag) + "Service");
        } catch (BeansException e) {
            logger.debug("not found cms service instance in Spring", e);
            return null;
        }

        worker.found = true;
        return worker;
    }

    @Getter(lazy = true)
    private final Properties orders = loadOrderProperties();

    private Properties loadOrderProperties() {
        val prop = new Properties();
        try {
            prop.load(context.getResource("classpath:config/order.properties").getInputStream());
        } catch (IOException e) {
            logger.error("cannot load order settings", e);
        }
        return prop;
    }

    private Comparator<Field> getFieldComparator(String tag) {
        return (o1, o2) -> {
            val orders = getOrders();
            val key1 = orders.getProperty(tag + '.' + o1.getName());
            val key2 = orders.getProperty(tag + '.' + o2.getName());
            return key1 == null || key2 == null ? 0 : key1.compareTo(key2);
        };
    }

    @Getter
    private class Unit {

        private String name;

        private String type;
    }

    public class ItemWorker {
        // all of worker found
        private boolean found = false;

        @Getter
        private String tag;

        private Object service;

        private Class<?> voType;

        private Class<?> idType;

        private Class<?> dtoType;

        @Getter(lazy = true)
        private final List<Unit> queryUnits = findQueryUnits();

        @Getter(lazy = true)
        private final List<Unit> itemUnits = findItemUnits();

        @Getter(lazy = true)
        private final Method getMethod = findGetMethod();

        @Getter(lazy = true)
        private final Method listMethod = findListMethod();

        private List<Unit> findQueryUnits() {
            if (dtoType == null) {
                logger.error("dtoType is null");
                return null;
            }
            return getUnits(dtoType,
                    i -> !Modifier.isStatic(i.getModifiers()) && i.isAnnotationPresent(QueryField.class));
        }

        private List<Unit> findItemUnits() {
            if (voType == null) {
                getGetMethod();
            }
            if (voType == null) {
                logger.error("voType is null");
                return null;
            }
            return getUnits(voType,
                    i -> !Modifier.isStatic(i.getModifiers()) && !i.isAnnotationPresent(HiddenField.class));
        }

        private List<Unit> getUnits(Class<?> clazz, Function<Field, Boolean> prediction) {
            return Reflections.getFields(clazz, prediction)
                    .stream()
                    .sorted(getFieldComparator(tag))
                    .map(field -> {
                        val unit = new Unit();
                        unit.name = field.getName();
                        unit.type = field.getType().getSimpleName();
                        return unit;
                    })
                    .collect(Collectors.toList());
        }

        private Method findGetMethod() {
            val name = "get" + StringUtils.capitalized(tag) + "ById";
            val value = Arrays.stream(service.getClass().getMethods())
                    .filter(m -> m.getParameterCount() == 1
                            && m.getReturnType() != void.class
                            && m.getName().equals(name))
                    .findFirst();
            if (!value.isPresent()) {
                logger.error("not found get method({}) of cms service({})", name, service);
                return null;
            }
            val method = value.get();
            idType = method.getParameterTypes()[0];
            voType = method.getReturnType();
            return method;
        }

        private Method findListMethod() {
            val name = "get" + StringUtils.capitalized(tag) + "Paged";
            val value = Arrays.stream(service.getClass().getMethods())
                    .filter(m -> m.getParameterCount() == 1
                            && Paged.class.isAssignableFrom(m.getReturnType())
                            && m.getName().equals(name))
                    .findFirst();
            if (!value.isPresent()) {
                logger.error("not found list method({}) of cms service: {}", name, service);
                return null;
            }
            val method = value.get();
            dtoType = method.getParameterTypes()[0];
            return method;
        }

        public Pair<Object, Object> getPaged(ServletRequest request) {
            if (service == null) {
                throw new IllegalStateException("CMS service is null");
            }
            val method = getListMethod();
            if (method == null) {
                return null;
            }
            val dto = WebUtils.getObjectParameter(dtoType, request);
            Object results = null;
            try {
                results = method.invoke(service, dto);
                if (results == null) {
                    logger.error("list method({}) of cms service({}) returned null", method.getName(), service);
                } else if (voType == null) {
                    val paged = ((Paged<?>) results);
                    if (paged.getTotal() > 0) {
                        voType = paged.get(0).getClass();
                    }
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                logger.error("cannot invoke list method({}) of cms service({})", method.getName(), service);
                logger.debug("traceback: ", e);
            }
            return new Pair<>(dto, results);
        }

        public Object getById(@NonNull String id) {
            if (service == null) {
                throw new IllegalStateException("CMS service is null");
            }
            val method = getGetMethod();
            if (method == null) {
                return null;
            }
            val _id = WebUtils.getValueParameter(idType, id);
            Object result = null;
            try {
                result = method.invoke(service, _id);
                if (result == null) {
                    logger.error("get method({}) of cms service({}) returned null", method.getName(), service);
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                logger.error("cannot invoke get method({}) of cms service({})", e, method.getName(), service);
                logger.debug("traceback: ", e);
            }
            return result;
        }
    }
}
