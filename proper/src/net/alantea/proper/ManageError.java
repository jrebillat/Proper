package net.alantea.proper;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The annotation ManageError, usable only on methods (even private) with one parameter (EventMessage) in an element.
 * It is possible to set multiple @ManageError annotations on the same method.
 * This annotation is automatically inherited by derived classes.
 */
@Repeatable(ManageErrors.class)
@Retention(RUNTIME)
@Target(METHOD)
@Inherited 
public @interface ManageError
{
   
   /**
    * Error level.
    *
    * @return the error level
    */
   public EventMessage.Level value();
}
