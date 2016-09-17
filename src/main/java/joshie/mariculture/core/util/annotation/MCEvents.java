package joshie.mariculture.core.util.annotation;

import net.minecraftforge.fml.relauncher.Side;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/** Classes annotated with this automatically get registered
 *  to the minecraft forge event bus if the modules are active.
 *  And they return true for events. */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MCEvents {
    Side value() default Side.SERVER;
    String modules() default "core";
}
