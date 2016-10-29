package net.alantea.proper;

import java.lang.reflect.Field;

class ObjectField
{
   private Object object;
   private Field field;

   ObjectField(Object object, Field field)
   {
      super();
      this.object = object;
      this.field = field;
   }

   Object getObject()
   {
      return object;
   }

   Field getField()
   {
      return field;
   }
}
