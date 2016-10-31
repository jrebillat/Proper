package net.alantea.proper;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The annotation Manage, usable only on methods (even private) with zero or one parameter in an element.
 * It is use to link during element association an action to the annotated method. The method will be called 
 * each time the action is triggered.
 * It is possible to set multiple @Manage annotations on the same method.
 * This annotation is automatically inherited by derived classes.
 */
@Repeatable(Manages.class)
@Retention(RUNTIME)
@Target(METHOD)
@Inherited 
public @interface Manage
{
   
   /**
    * Action name in the container.
    *
    * @return the action name in the container
    */
   public String value();
}
