package net.alantea.proper;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The annotation Manages, container for lists of @Manage annotations on a method.
 */
@Retention(RUNTIME)
@Target(METHOD)
@Inherited 
public @interface Manages
{
   
   /**
    * Get list of Manage annotations on the element.
    *
    * @return the list of Manage annotations on the element.
    */
   public Manage[] value();
}
