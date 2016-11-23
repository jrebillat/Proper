package net.alantea.proper;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The annotation ManageRegistry, usable only on methods. It is use to do any work needed during the instance 
 * association. The method shall have two parameters : a String containing the key code for association and
 * an Object representing the container.
 */
@Repeatable(ManageRegistries.class)
@Retention(RUNTIME)
@Target({METHOD})
@Inherited 
public @interface ManageRegistry
{
   
   /**
    * Managed key code.
    *
    * @return the managed key code 
    */
   public String value() default "";
}
