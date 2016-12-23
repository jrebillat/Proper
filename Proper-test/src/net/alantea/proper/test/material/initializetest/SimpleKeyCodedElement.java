package net.alantea.proper.test.material.initializetest;

import net.alantea.proper.Initialize;
import net.alantea.proper.Require;

@Require(key=Names.SIMPLEKEYNAME, type = String.class)
public class SimpleKeyCodedElement
{
   @Initialize(value=Names.SIMPLEKEYNAME, code=Names.SIMPLEKEYCODE1)
   private String keyName1;

   @Initialize(value=Names.SIMPLEKEYNAME, code=Names.SIMPLEKEYCODE2)
   private String keyName2;

   public String getSimpleKey1()
   {
      return keyName1;
   }
   
   public String getSimpleKey2()
   {
      return keyName2;
   }
}
