package net.alantea.proper.example.example5;

import net.alantea.proper.Manage;
import net.alantea.proper.ManageRegistry;
import net.alantea.proper.PropertyContainer;
import net.alantea.proper.Require;

@Require(key=ManagedMain.PROP_TWO, type=Long.class, action=ManagedMain.ACT_GOTLONG, code="Container2")
@Require(key=OutputPrinter.PROP_THREE, type=Long.class, action=OutputPrinter.ACT_GOTDOUBLE)
public class OutputPrinter
{
   public static final String PROP_THREE = "PropertyThree";
   public static final String ACT_GOTDOUBLE = "GotDouble";

   @Manage(value=ManagedMain.ACT_GOTINTEGER, code="Container1")
   private void actionGotInteger(int value)
   {
      System.out.println("I got a integer value : " + value);
   }
   @Manage(value=ManagedMain.ACT_GOTLONG, code="Container2")
   private void actionGotLong(long value)
   {
      System.out.println("I got a long value : " + value);
   }
   @Manage(value=ACT_GOTDOUBLE)
   private void actionGotDouble(double value)
   {
      System.out.println("I got a double value : " + value);
   }
   
   @ManageRegistry("Container1")
   private void manage1(String keyCode, PropertyContainer container)
   {
      System.out.println("test " + keyCode + " " + container.getClass());
      container.createProperty(ManagedMain.PROP_ONE, Integer.class, ManagedMain.ACT_GOTINTEGER, true);
   }
   
}
