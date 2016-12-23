package net.alantea.proper;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The annotation Listens, container for lists of @Listen annotations on a method.
 */
@Retention(RUNTIME)
@Target(METHOD)
@Inherited 
public @interface Listens
{
   
   /**
    * Get list of listeners to property change on the element.
    *
    * @return the list of Listen annotations on the element.
    */
   public Listen[] value();
}
