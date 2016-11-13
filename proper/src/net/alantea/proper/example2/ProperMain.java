package net.alantea.proper.example2;

import net.alantea.proper.PropertyContainer;
import net.alantea.proper.example1.Container;

public class ProperMain
{

   public static void main(String[] args)
   {
      Container1 cont1 = new Container1();
      Container2 cont2 = new Container2();
      
      OutputPrinter printer = new OutputPrinter();
      PropertyContainer.associate(cont1, "Container1", printer);
      PropertyContainer.associate(cont2, "Container2", printer);
      Container.associateNamedContainer("HiddenContainer", printer);
      
      Container.setPropertyValue(cont1, Container1.PROP_ONE, 1);
      Container.setPropertyValue(cont2, Container1.PROP_ONE, 10);
      Container.setPropertyValue(cont2, Container2.PROP_TWO, 200000000);
      Container.setPropertyValue("HiddenContainer", OutputPrinter.PROP_THREE, 3.14);
      
      Container.setPropertyValue(cont1, Container1.PROP_ONE, 2);
      Container.setPropertyValue(cont2, Container2.PROP_TWO, 5000000000L);
      Container.setPropertyValue("", OutputPrinter.PROP_THREE, 123.456);
      Container.setPropertyValue("HiddenContainer", OutputPrinter.PROP_THREE, 666.0);
   }

}
