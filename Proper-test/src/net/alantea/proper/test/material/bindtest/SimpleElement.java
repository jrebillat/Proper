package net.alantea.proper.test.material.bindtest;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import net.alantea.proper.Bind;
import net.alantea.proper.Require;

@Require(key=Names.SIMPLEKEYNAME, type = String.class)
public class SimpleElement
{
   @Bind(Names.SIMPLEKEYNAME)
   private ObjectProperty<String> keyName = new SimpleObjectProperty<>();


   public String getSimpleKey()
   {
      return keyName.get();
   }

   // Note : this method should fail
   public void setSimpleKey(String value)
   {
      keyName.set(value);
   }
}
