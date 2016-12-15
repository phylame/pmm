package pmm.pbm.util.cms;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for method of service for query item by id.
 *
 * @author PW[<a href="mailto:phylame@163.com">phylame@163.com</a>]
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GetMethod {

}
