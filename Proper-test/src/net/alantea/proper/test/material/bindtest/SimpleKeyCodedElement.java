package net.alantea.proper.test.material.bindtest;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import net.alantea.proper.Bind;
import net.alantea.proper.Require;

@Require(key=Names.SIMPLEKEYNAME, type = String.class)
public class SimpleKeyCodedElement
{
   @Bind(value=Names.SIMPLEKEYNAME, code=Names.SIMPLEKEYCODE1)
   private ObjectProperty<String> keyName1 = new SimpleObjectProperty<>();

   @Bind(value=Names.SIMPLEKEYNAME, code=Names.SIMPLEKEYCODE2)
   private ObjectProperty<String> keyName2 = new SimpleObjectProperty<>();

   public String getSimpleKey1()
   {
      return keyName1.get();
   }
   
   public String getSimpleKey2()
   {
      return keyName2.get();
   }
}
