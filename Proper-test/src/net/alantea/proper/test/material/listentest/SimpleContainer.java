package net.alantea.proper.test.material.listentest;

import net.alantea.proper.MappedPropertyContainer;
import net.alantea.proper.Require;

@Require(key=Names.SIMPLEKEYNAME, type = String.class)
@Require(key=Names.SIMPLEKEYNAME1, type = String.class)
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

   public void setSimpleKey1(String value)
   {
       setPropertyValue(Names.SIMPLEKEYNAME1, value);
   }
}
