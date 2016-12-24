package net.alantea.proper.test.material.manageregistrytest;

import net.alantea.proper.ManageRegistry;
import net.alantea.proper.PropertyContainer;

public class MultipleElement
{
   private int stored = 0;

   public int getStored()
   {
      return stored;
   }
   
   @ManageRegistry(Names.SIMPLEKEYCODE1)
   @ManageRegistry(Names.SIMPLEKEYCODE2)
   private void changeKeyName(String keyCode, PropertyContainer container)
   {
      stored++;
   }
}
