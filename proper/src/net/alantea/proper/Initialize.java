package net.alantea.proper;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The annotation Initialize, usable only on fields. It is use to initialize (during the instance 
 * association) the annotated field to the value of the property with the given name in the container.
 * The property must have been defined before and a value set to it.
 * The field class type must be assignable from the container property content type.
 */
@Retention(RUNTIME)
@Target(FIELD)
@Inherited 
public @interface Initialize
{
   
   /**
    * Property name in the container.
    *
    * @return the property name in the container
    */
   public String value();
}
