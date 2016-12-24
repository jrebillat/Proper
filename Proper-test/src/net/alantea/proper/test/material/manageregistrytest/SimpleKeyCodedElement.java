package net.alantea.proper.test.material.manageregistrytest;

import net.alantea.proper.ManageRegistry;
import net.alantea.proper.PropertyContainer;

public class SimpleKeyCodedElement
{
   private boolean initialized1 = false;
   private boolean initialized2 = false;

   public boolean isInitialized1()
   {
      return initialized1;
   }
   
   public boolean isInitialized2()
   {
      return initialized2;
   }
   
   @ManageRegistry(Names.SIMPLEKEYCODE1)
   private void changeKeyName1(String keyCode, PropertyContainer container)
   {
      initialized1 = true;
   }
   
   @ManageRegistry(Names.SIMPLEKEYCODE2)
   private void changeKeyName2(String keyCode, PropertyContainer container)
   {
      initialized2 = true;
   }
}
