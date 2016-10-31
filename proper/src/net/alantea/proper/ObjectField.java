package net.alantea.proper;

import java.lang.reflect.Field;

/**
 * The Class ObjectField is internally used to help management of instance fields storage.
 */
class ObjectField
{
   
   /** The object instance. */
   private Object object;
   
   /** The field. */
   private Field field;

   /**
    * Instantiates a new object field.
    *
    * @param object the object instance
    * @param field the field
    */
   ObjectField(Object object, Field field)
   {
      super();
      this.object = object;
      this.field = field;
   }

   /**
    * Gets the object.
    *
    * @return the object
    */
   Object getObject()
   {
      return object;
   }

   /**
    * Gets the field.
    *
    * @return the field
    */
   Field getField()
   {
      return field;
   }
}
