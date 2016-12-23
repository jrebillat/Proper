package net.alantea.proper.example.example5;

import net.alantea.proper.MappedPropertyContainer;

public class ManagedMain
{
   public static final String PROP_ONE = "PropertyOne";
   public static final String PROP_TWO = "PropertyTwo";
   public static final String ACT_GOTINTEGER = "GotInteger";
   public static final String ACT_GOTLONG = "GotLong";

   public static void main(String[] args)
   {
      Integer cont1 = -1;
      Double cont2 = -1.23;
      
      OutputPrinter printer = new OutputPrinter();
      MappedPropertyContainer.associateHook(cont1, "Container1", printer);
      MappedPropertyContainer.associateHook(cont2, "Container2", printer);
      MappedPropertyContainer.associateHook("HiddenContainer", "", printer);
      
      MappedPropertyContainer.setPropertyValue(cont1, PROP_ONE, 1);
      MappedPropertyContainer.setPropertyValue(cont2, PROP_ONE, 10);
      MappedPropertyContainer.setPropertyValue(cont2, PROP_TWO, 200000000);
      MappedPropertyContainer.setPropertyValue("HiddenContainer", OutputPrinter.PROP_THREE, 3.14);
      
      MappedPropertyContainer.setPropertyValue(cont1, PROP_ONE, 2);
      MappedPropertyContainer.setPropertyValue(cont2, PROP_TWO, 5000000000L);
      MappedPropertyContainer.setPropertyValue("", OutputPrinter.PROP_THREE, 123.456);
      MappedPropertyContainer.setPropertyValue("HiddenContainer", OutputPrinter.PROP_THREE, 666.0);
   }

}
