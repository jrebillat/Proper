package net.alantea.proper.test.material.associatetest;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import net.alantea.proper.Associate;
import net.alantea.proper.Require;

@Require(key=Names.SIMPLEKEYNAME, type = String.class)
public class SimpleKeyCodedElement
{
   @Associate(value=Names.SIMPLEKEYNAME, code=Names.SIMPLEKEYCODE1)
   private String keyName1;

   @Associate(value=Names.SIMPLEKEYNAME, code=Names.SIMPLEKEYCODE2)
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
