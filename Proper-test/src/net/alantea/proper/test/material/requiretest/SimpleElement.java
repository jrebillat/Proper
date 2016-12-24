package net.alantea.proper.test.material.requiretest;

import net.alantea.proper.Listen;
import net.alantea.proper.PropertyContainer;
import net.alantea.proper.Require;

@Require(key=Names.SIMPLEKEYNAME1, type = Double.class)
public class SimpleElement
{
   private double keyName;


   public double getSimpleKey()
   {
      return keyName;
   }
   
   @Listen(Names.SIMPLEKEYNAME1)
   private void changeKeyName(PropertyContainer container, Double value)
   {
      keyName = value;
   }
}
