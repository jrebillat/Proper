package net.alantea.proper.test.material.listentest;

import net.alantea.proper.Listen;
import net.alantea.proper.PropertyContainer;

public class MultipleElement
{
   private String keyName1;
   private String keyName2;

   public String getSimpleKey1()
   {
      return keyName1;
   }
   
   public String getSimpleKey2()
   {
      return keyName2;
   }
   
   @Listen(value=Names.SIMPLEKEYNAME, code=Names.SIMPLEKEYCODE1)
   @Listen(value=Names.SIMPLEKEYNAME, code=Names.SIMPLEKEYCODE2)
   private void changeKeyName1(PropertyContainer container, String text)
   {
      keyName1 = text;
   }
   
   @Listen(value=Names.SIMPLEKEYNAME)
   @Listen(value=Names.SIMPLEKEYNAME1)
   private void changeKeyName2(PropertyContainer container, String text)
   {
      keyName2 = text;
   }
}
