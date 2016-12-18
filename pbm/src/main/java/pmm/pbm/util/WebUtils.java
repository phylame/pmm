package pmm.pbm.util;

import javax.servlet.ServletRequest;

import org.springframework.util.ClassUtils;
import org.springframework.web.bind.ServletRequestDataBinder;

import lombok.SneakyThrows;
import lombok.val;
import pw.phylame.ycl.format.Converters;

public final class WebUtils {
    @SuppressWarnings("unchecked")
    public static <T> T getParameter(Class<T> type, String str, ServletRequest request) {
        if (CharSequence.class.isAssignableFrom(type)) {
            return (T) str;
        } else if (ClassUtils.isPrimitiveOrWrapper(type)) {
            return getValueParameter(type, str);
        } else {
            return getObjectParameter(type, request);
        }
    }

    public static <T> T getValueParameter(Class<T> type, String str) {
        return Converters.parse(str, type);
    }

    @SneakyThrows({InstantiationException.class, IllegalAccessException.class})
    public static <T> T getObjectParameter(Class<T> type, ServletRequest request) {
        val object = type.newInstance();
        val binder = new ServletRequestDataBinder(object);
        binder.bind(request);
        return object;
    }
}
