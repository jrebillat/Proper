package net.alantea.proper.test.material.associatetest;

import net.alantea.proper.Associate;
import net.alantea.proper.Require;

@Require(key=Names.SIMPLEKEYNAME, type = String.class)
public class SimpleElement
{
   @Associate(Names.SIMPLEKEYNAME)
   private String keyName;


   public String getSimpleKey()
   {
      return keyName;
   }
}
