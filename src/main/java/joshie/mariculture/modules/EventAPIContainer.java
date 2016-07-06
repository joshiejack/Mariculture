package joshie.mariculture.modules;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/** Classes annotated with this automatically get registered
 *  to the minecraft forge event bus if the modules are active.
 *  And they return true for events. It will also attempt to associate
 *  the instance with the api, if it implements one of the main classes */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EventAPIContainer {
    String modules() default "core";
    boolean events() default false;
}
