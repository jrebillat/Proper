package net.alantea.proper;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The annotation ManageErrors, container for lists of @ManageError annotations on a method.
 */
@Retention(RUNTIME)
@Target(METHOD)
@Inherited 
public @interface ManageErrors
{
   
   /**
    * Get list of error managers.
    *
    * @return the list of error managers.
    */
   public ManageError[] value();
}
