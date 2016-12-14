package pmm.pbm.web;

import static org.springframework.util.StringUtils.capitalize;
import static org.springframework.util.StringUtils.isEmpty;
import static org.springframework.util.StringUtils.uncapitalize;

import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRequest;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lombok.SneakyThrows;
import lombok.val;
import pmm.pbm.data.base.entity.Endpoint;
import pmm.pbm.data.base.support.Entity;
import pmm.pbm.data.base.support.Example;
import pmm.pbm.service.rest.EndpointService;
import pmm.pbm.service.support.CrudService;
import pmm.pbm.util.Reflections;
import pmm.pbm.web.controller.BaseController;

@RestController
@RequestMapping("/api/v1")
public class CrudController extends BaseController {
    @Autowired
    private EndpointService endpointService;

    @Autowired
    private WebApplicationContext context;

    @RequestMapping(value = "/{endpoint}", method = RequestMethod.GET)
    public Object getEntities(@PathVariable("endpoint") String endpoint) {
        val service = getService(endpoint);
        return Reflections.invokeMethod(service.service, "selectByExample", service.makeExample(), Example.class);
    }

    @RequestMapping(value = "/{endpoint}", method = RequestMethod.POST)
    public Object createEntity(@PathVariable("endpoint") String endpoint, @RequestBody String json) {
        val service = getService(endpoint);

        return Reflections.invokeMethod(service.service, "insert", getObjectParameter(service.entityClass, request),
                Entity.class);
    }

    @RequestMapping(value = "/{endpoint}/{id}", method = RequestMethod.GET)
    public Object getEntity(@PathVariable("endpoint") String endpoint, @PathVariable("id") String id) {
        val service = getService(endpoint);
        return Reflections.invokeMethod(service.service, "selectById", getValueParameter(service.idClass, id),
                Object.class);
    }

    @RequestMapping(value = "/{endpoint}/{id}", method = RequestMethod.PUT)
    public Object updateEntity(@PathVariable("endpoint") String endpoint, @PathVariable("id") String id) {
        val service = getService(endpoint);
        return "";
    }

    @RequestMapping(value = "/{endpoint}/{id}", method = RequestMethod.PATCH)
    public Object updateEntitySelective(@PathVariable("endpoint") String endpoint, @PathVariable("id") String id) {
        val service = getService(endpoint);
        return "";
    }

    @RequestMapping(value = "/{endpoint}/{id}", method = RequestMethod.DELETE)
    public Object deleteEntity(@PathVariable("endpoint") String endpoint, @PathVariable("id") String id) {
        val service = getService(endpoint);
        return Reflections.invokeMethod(service.service, "deleteById", getValueParameter(service.idClass, id),
                Object.class);
    }

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e) {
        e.printStackTrace();
        return e.getMessage();
    }

    @SneakyThrows({InstantiationException.class, IllegalAccessException.class})
    private Object getObjectParameter(Class<?> type, ServletRequest request) {
        val object = type.newInstance();
        val binder = new ServletRequestDataBinder(object);
        binder.bind(request);
        Reflections.invokeMethod(object, "setDeleted", false);
        return object;
    }

    private Object getValueParameter(Class<?> type, String value) {
        if (isEmpty(value)) {
            if (CharSequence.class.isAssignableFrom(type)) {
                return "";
            }
            return null;
        }
        if (CharSequence.class.isAssignableFrom(type)) {
            return value;
        } else if (type == boolean.class || type == Boolean.class) {
            return Boolean.valueOf(value);
        }
        try {
            if (type == int.class || type == Integer.class) {
                return Integer.valueOf(value);
            } else if (type == short.class || type == Short.class) {
                return Short.valueOf(value);
            } else if (type == byte.class || type == Byte.class) {
                return Byte.valueOf(value);
            } else if (type == long.class || type == Long.class) {
                return Long.valueOf(value);
            } else if (type == float.class || type == Float.class) {
                return Float.valueOf(value);
            } else if (type == double.class || type == Double.class) {
                return Double.valueOf(value);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Cannot convert '%s' to type %s", value, type));
        }
        throw new IllegalStateException("getParameter only used for value type, not: " + type);
    }

    private class ServiceDescriptor {

        private final Class<?> idClass;

        private final Class<? extends Entity> entityClass;

        private final Class<? extends Example> exampleClass;

        private boolean initialized = false;

        private final Set<Pair<String, Class<?>>> properties = Sets.newLinkedHashSet();

        private final CrudService<? extends Entity, ? extends Example, ?> service;

        @SuppressWarnings("unchecked")
        @SneakyThrows({ClassNotFoundException.class})
        ServiceDescriptor(Endpoint e) {
            idClass = Class.forName(e.getIdType());
            entityClass = (Class<? extends Entity>) Class.forName(e.getEntityType());
            exampleClass = (Class<? extends Example>) Class.forName(e.getExampleType());
            service = (CrudService<? extends Entity, ? extends Example, ?>) context.getBean(e.getMapping() + "Service");
        }

        private Set<Pair<String, Class<?>>> getPropertyNames() {
            if (!initialized) {
                try {
                    for (val m : Class.forName(exampleClass.getName() + "$Criteria").getMethods()) {
                        val name = m.getName();
                        if (name.startsWith("and") && name.endsWith("NotEqualTo")) {
                            properties.add(Pair.of(uncapitalize(name.substring(3, name.length() - 10)),
                                    m.getParameterTypes()[0]));
                        }
                    }
                } catch (ClassNotFoundException e) {
                    logger.error("not found criteria class: " + e.getMessage());
                }
                initialized = true;
            }
            return properties;
        }

        @SneakyThrows(Exception.class)
        private Example makeExample() {
            val example = exampleClass.newInstance();
            val criteria = Reflections.invokeMethod(example, "createCriteria");
            for (val item : getPropertyNames()) {
                val value = getValueParameter(item.getRight(), request.getParameter(item.getLeft()));
                if (value != null) {
                    if (CharSequence.class.isAssignableFrom(item.getRight())) {
                        val str = value.toString();
                        if (!str.isEmpty()) {
                            Reflections.invokeMethod(criteria, "and" + capitalize(item.getLeft()) + "Like",
                                    '%' + str + '%');
                        }
                    } else {
                        Reflections.invokeMethod(criteria, "and" + capitalize(item.getLeft()) + "EqualTo", value);
                    }
                }
            }
            Reflections.invokeMethod(criteria, "andDeletedEqualTo", false);
            String value = request.getParameter("offset");
            if (!isEmpty(value)) {
                example.setOffset(Integer.parseInt(value));
            }
            value = request.getParameter("limit");
            if (!isEmpty(value)) {
                example.setLimit(Integer.parseInt(value));
            }
            value = request.getParameter("sortby");
            if (!isEmpty(value)) {
                example.setOrderByClause(value);
            }
            value = request.getParameter("order");
            if (!isEmpty(value)) {
                if (!isEmpty(example.getOrderByClause())) {
                    example.setOrderByClause(example.getOrderByClause() + ' ' + value);
                } else {
                    logger.info("found 'order' but no 'sortby' specified");
                }
            }
            return example;
        }

    }

    private final Map<String, ServiceDescriptor> services = Maps.newConcurrentMap();

    private ServiceDescriptor getService(String name) {
        ServiceDescriptor descriptor = services.get(name);
        if (descriptor != null) {
            return descriptor;
        }
        descriptor = findService(name);
        if (descriptor != null) {
            services.put(name, descriptor);
        } else {
            throw new IllegalArgumentException("Not such endpoint: " + name);
        }
        return descriptor;
    }

    private ServiceDescriptor findService(String name) {
        val endpoint = endpointService.getByName(name);
        if (endpoint == null) {
            logger.debug("not found endpoint: " + name);
            return null;
        }
        return new ServiceDescriptor(endpoint);
    }
}
