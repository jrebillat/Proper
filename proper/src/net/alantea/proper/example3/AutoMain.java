package net.alantea.proper.example3;

import net.alantea.proper.PropertyContainer;
import net.alantea.proper.example1.Container;

public class AutoMain
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
      PropertyContainer.associate(cont1, "Container1", printer);
      
      PropertyContainer.associate(cont2, "Container2", printer);
      Container.associateNamedContainer("HiddenContainer", printer);
      
      Container.setPropertyValue(cont1, PROP_ONE, 1);
      Container.setPropertyValue(cont2, PROP_ONE, 10);
      Container.setPropertyValue(cont2, PROP_TWO, 200000000);
      Container.setPropertyValue("HiddenContainer", OutputPrinter.PROP_THREE, 3.14);
      
      Container.setPropertyValue(cont1, PROP_ONE, 2);
      Container.setPropertyValue(cont2, PROP_TWO, 5000000000L);
      Container.setPropertyValue("", OutputPrinter.PROP_THREE, 123.456);
      Container.setPropertyValue("HiddenContainer", OutputPrinter.PROP_THREE, 666.0);
   }

}
