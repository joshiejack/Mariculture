package joshie.mariculture.modules;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/** Classes annotated with this automatically get registered
 *  to the minecraft forge event bus is the modules are active. */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EventContainer {
    String modules() default "core";
}
