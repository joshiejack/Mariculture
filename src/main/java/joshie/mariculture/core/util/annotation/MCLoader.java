package joshie.mariculture.core.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MCLoader {
    /** Comma separated list of modules required to be enabled,
     *  By default, requires no modules **/
    String modules() default "";

    /** Comma separated list of mods required to enabled,
     *  By default, requires no mods **/
    String mods() default "";
}
