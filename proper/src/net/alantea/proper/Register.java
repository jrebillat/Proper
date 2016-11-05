package net.alantea.proper;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The annotation Register, usable only on class. It is use to initialize (during the instance 
 * association) the property with the given name in the container with the instance object itself.
 * The property content class type, if already existing, must be assignable from the element type.
 */
@Repeatable(Registers.class)
@Retention(RUNTIME)
@Target({TYPE, FIELD})
@Inherited 
public @interface Register
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
