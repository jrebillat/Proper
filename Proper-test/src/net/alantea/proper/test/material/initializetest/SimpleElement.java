package net.alantea.proper.test.material.initializetest;

import net.alantea.proper.Initialize;
import net.alantea.proper.Require;

@Require(key=Names.SIMPLEKEYNAME, type = String.class)
public class SimpleElement
{
   @Initialize(Names.SIMPLEKEYNAME)
   private String keyName;


   public String getSimpleKey()
   {
      return keyName;
   }
}
