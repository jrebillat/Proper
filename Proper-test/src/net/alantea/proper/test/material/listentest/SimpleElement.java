package net.alantea.proper.test.material.listentest;

import net.alantea.proper.Listen;
import net.alantea.proper.PropertyContainer;

public class SimpleElement
{
   private String keyName;


   public String getSimpleKey()
   {
      return keyName;
   }
   
   @Listen(Names.SIMPLEKEYNAME)
   private void changeKeyName(PropertyContainer container, String text)
   {
      keyName = text;
   }
}
