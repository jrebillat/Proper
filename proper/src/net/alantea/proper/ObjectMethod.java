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
   
   /** The needs parameters. */
   private boolean needsParameters = false;

   /**
    * Instantiates a new object method.
    *
    * @param object the object
    * @param method the method
    * @param needsParameters indicates if the method needs parameters
    */
   ObjectMethod(Object object, Method method, boolean needsParameters)
   {
      super();
      this.object = object;
      this.method = method;
      this.needsParameters = needsParameters;
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

   /**
    * Checks if is needs parameters.
    *
    * @return true, if is needs parameters
    */
   boolean isNeedsParameters()
   {
      return needsParameters;
   }
}
