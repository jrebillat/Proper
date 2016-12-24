package net.alantea.proper.test.material.requiretest;

import net.alantea.proper.Manage;
import net.alantea.proper.Require;

@Require(key=Names.SIMPLEKEYNAME1, type = Double.class, action=Names.SIMPLEACTION1)
public class BasicElement
{
   private double keyName;


   public double getSimpleKey()
   {
      return keyName;
   }
   
   @Manage(Names.SIMPLEACTION1)
   private void changeKeyName()
   {
      keyName = 123.0;
   }
}
