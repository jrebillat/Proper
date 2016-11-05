package net.alantea.proper;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The annotation Registers, container for lists of @Register annotations on a method.
 */
@Retention(RUNTIME)
@Target(TYPE)
@Inherited 
public @interface Registers
{
   
   /**
    * Get list of Require annotations on the element.
    *
    * @return the list of Require annotations on the element.
    */
   public Register[] value();
}
