package ch.trick17.betterchecks.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The presence of this annotation on a type indicates that the type may be used
 * with the <a href="http://www.gwtproject.org/">Google Web Toolkit</a> (GWT).
 * 
 * @author Michael Faes
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
@Documented
@GwtCompatible
public @interface GwtCompatible {
    
}
