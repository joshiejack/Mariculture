package joshie.mariculture.modules;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Module {
    /** Name of this module **/
    String name();

    /** If this is set to true,
     * this module will be disabled by default **/
    boolean disableByDefault() default false;

    /** Comma separated list of modules required,
     *  By default, all modules require core to be enabled **/
    String modules() default "";

    /** Comma separated list of mods required to enabled,
     *  By default, requires no mods **/
    String mods() default "";
}
