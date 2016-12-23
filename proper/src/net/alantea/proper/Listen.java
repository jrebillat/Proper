package net.alantea.proper;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The annotation Listen, usable only on methods (even private) with zero or one parameter in an element.
 * It is use to add listeners on properties during element association to the annotated method. The method will be called 
 * each time the property is changed.
 * It is possible to set multiple @Listen annotations on the same method.
 * This annotation is automatically inherited by derived classes.
 */
@Repeatable(Listens.class)
@Retention(RUNTIME)
@Target(METHOD)
@Inherited 
public @interface Listen
{
   
   /**
    * Property name in the container.
    *
    * @return the action name in the container
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
