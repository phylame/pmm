package pmm.pbm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import lombok.val;

public final class Reflections {
    private static final Logger logger = LoggerFactory.getLogger(Reflections.class);

    public static Object invokeMethod(Object target, String name) {
        val method = ReflectionUtils.findMethod(target.getClass(), name);
        if (method == null) {
            logger.debug("no such method: " + name + " of " + target.getClass());
            return null;
        }
        return ReflectionUtils.invokeMethod(method, target);
    }

    public static Object invokeMethod(Object target, String name, Object argument) {
        val method = ReflectionUtils.findMethod(target.getClass(), name, new Class<?>[]{argument.getClass()});
        if (method == null) {
            logger.debug("no such method: " + name + " of " + target.getClass());
            return null;
        }
        return ReflectionUtils.invokeMethod(method, target, argument);
    }

    public static Object invokeMethod(Object target, String name, Object argument, Class<?> parameterType) {
        val method = ReflectionUtils.findMethod(target.getClass(), name, new Class<?>[]{parameterType});
        if (method == null) {
            logger.debug("no such method: " + name + " of " + target.getClass());
            return null;
        }
        return ReflectionUtils.invokeMethod(method, target, argument);
    }
}
