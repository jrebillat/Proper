package net.alantea.proper;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The annotation Bind, usable only on fields with a type deriving from Property. It is use to bind 
 * the annotated field to the property with the given name in the container. The property must have
 * been defined before. The property field content class type must be assignable from the container
 * property content type.
 */
@Retention(RUNTIME)
@Target(FIELD)
@Inherited 
public @interface Bind
{
   
   /**
    * Property name in the container.
    *
    * @return the property name in the container.
    */
   public String value();
   
   /**
    * Bidirectional flag. If true, the binding should be bidirectional. If false, the binding should be
    * done to bind the field property to the container property. The default value is 'false'.
    *
    * @return true, if the binding should be bidirectional.
    */
   public boolean biDirectional() default false;
}
