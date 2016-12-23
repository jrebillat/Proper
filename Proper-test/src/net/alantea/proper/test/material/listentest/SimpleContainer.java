package net.alantea.proper.test.material.listentest;

import net.alantea.proper.MappedPropertyContainer;
import net.alantea.proper.Require;

@Require(key=Names.SIMPLEKEYNAME, type = String.class)
public class SimpleContainer extends MappedPropertyContainer
{
   public SimpleContainer()
   {
      super();
   }

   public String getSimpleKey()
   {
      return getPropertyValue(Names.SIMPLEKEYNAME);
   }

   public void setSimpleKey(String value)
   {
       setPropertyValue(Names.SIMPLEKEYNAME, value);
   }
}
