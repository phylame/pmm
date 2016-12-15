package pmm.pbm.util.cms;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Fields of VO with this annotation will no be shown in CMS list.
 *
 * @author PW[<a href="mailto:phylame@163.com">phylame@163.com</a>]
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HiddenField {
}
