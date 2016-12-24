package net.alantea.proper.test.material.manageregistrytest;

import net.alantea.proper.ManageRegistry;
import net.alantea.proper.PropertyContainer;

public class SimpleElement
{
   private boolean initialized = false;;


   public boolean isInitialized()
   {
      return initialized;
   }
   
   @ManageRegistry()
   private void changeKeyName(String keyCode, PropertyContainer container)
   {
      initialized = true;
   }
}
