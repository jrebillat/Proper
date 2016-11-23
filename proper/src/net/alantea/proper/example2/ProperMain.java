package net.alantea.proper.example2;

public class ProperMain
{

   public static void main(String[] args)
   {
      Container1 cont1 = new Container1();
      Container2 cont2 = new Container2();
      
      OutputPrinter printer = new OutputPrinter();
      cont1.associate("Container1", printer);
      cont2.associate("Container2", printer);
//      Container.associateNamedContainer("HiddenContainer", printer);
      
      cont1.setPropertyValue(Container1.PROP_ONE, 1);
      cont2.setPropertyValue(Container1.PROP_ONE, 10);
      cont2.setPropertyValue(Container2.PROP_TWO, 200000000);
//      Container.setPropertyValue("HiddenContainer", OutputPrinter.PROP_THREE, 3.14);
      
      cont1.setPropertyValue(Container1.PROP_ONE, 2);
      cont2.setPropertyValue(Container2.PROP_TWO, 5000000000L);
//      Container.setPropertyValue("", OutputPrinter.PROP_THREE, 123.456);
//      Container.setPropertyValue("HiddenContainer", OutputPrinter.PROP_THREE, 666.0);
   }

}
