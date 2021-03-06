package net.alantea.proper;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The annotation Require, usable only on class definition. It is use to create or update if needed
 * a property in the container during instance association with a container, with a content class type
 * and optionnaly an action to trigger on each property value modification.
 * It is possible to set multiple @Require annotations on the same class.
 * This annotation is automatically inherited by derived classes.
 */
@Repeatable(Requires.class)
@Retention(RUNTIME)
@Target(TYPE)
@Inherited 
public @interface Require
{
   
   /**
    * Property key.
    *
    * @return the property key
    */
   public String key();
   
   /**
    * Property content type.
    *
    * @return the property content type
    */
   public Class<?> type();
   
   /**
    * Action triggered on content value modification. Default to empty action.
    *
    * @return the action key
    */
   public String action() default "";
   
   /**
    * If not empty, specify this annotation to be used only when associating with this reference code.
    * Default to empty string, that means to be used when no keycode is given.
    *
    * @return the action key
    */
   public String code() default "";
   
   /**
    * Import from key code container into default container.
    *
    * @return the string
    */
   public String importFrom() default "";
   
   /**
    * Associate when creating.
    *
    * @return true, if successful
    */
   public boolean associate() default false;
   
   /**
    * Bind with key code container's property when creating. Only concerned if 'associate' is true.
    *
    * @return true, if successful
    */
   public boolean bound() default false;
}
