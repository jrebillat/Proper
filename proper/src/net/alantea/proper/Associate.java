package net.alantea.proper;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The annotation Associate, usable only on fields. It is use to associate the annotated field
 * to the property with the given name in the container. The property must have been defined before.
 * The field class type must be assignable from the container property content type.
 */
@Retention(RUNTIME)
@Target(FIELD)
@Inherited 
public @interface Associate
{
   
   /**
    * Property name in the container.
    *
    * @return the property name in the container
    */
   public String value();
   
   /**
    * If not empty, specify this annotation to be used only when associating with this reference code.
    * Default to empty string, that means to be used when no keycode is given.
    *
    * @return the action key
    */
   public String code() default "";
}
