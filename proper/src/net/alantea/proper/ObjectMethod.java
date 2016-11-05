package net.alantea.proper;

import java.lang.reflect.Method;

// TODO: Auto-generated Javadoc
/**
 * The Class ObjectMethod is internally used to help management of instance method storage..
 */
class ObjectMethod
{
   
   /** The object instance. */
   private Object object;
   
   /** The method. */
   private Method method;

   /**
    * Instantiates a new object method.
    *
    * @param object the object
    * @param method the method
    */
   ObjectMethod(Object object, Method method)
   {
      super();
      this.object = object;
      this.method = method;
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
    * Gets the method.
    *
    * @return the method
    */
   Method getMethod()
   {
      return method;
   }
}
