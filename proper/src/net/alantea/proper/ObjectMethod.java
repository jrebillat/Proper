package net.alantea.proper;

import java.lang.reflect.Method;

class ObjectMethod
{
   private Object object;
   private Method method;
   private boolean needsParameters = false;

   ObjectMethod(Object object, Method method, boolean needsParameters)
   {
      super();
      this.object = object;
      this.method = method;
      this.needsParameters = needsParameters;
   }

   Object getObject()
   {
      return object;
   }

   Method getMethod()
   {
      return method;
   }

   boolean isNeedsParameters()
   {
      return needsParameters;
   }
}
