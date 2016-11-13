package net.alantea.proper.example3;

import net.alantea.proper.Manage;
import net.alantea.proper.Require;

@Require(key=AutoMain.PROP_ONE, type=Integer.class, action=AutoMain.ACT_GOTINTEGER, code="Container1")
@Require(key=AutoMain.PROP_TWO, type=Long.class, action=AutoMain.ACT_GOTLONG, code="Container2")
@Require(key=OutputPrinter.PROP_THREE, type=Long.class, action=OutputPrinter.ACT_GOTDOUBLE)
public class OutputPrinter
{
   public static final String PROP_THREE = "PropertyThree";
   public static final String ACT_GOTDOUBLE = "GotDouble";

   @Manage(value=AutoMain.ACT_GOTINTEGER, code="Container1")
   private void actionGotInteger(int value)
   {
      System.out.println("I got a integer value : " + value);
   }
   @Manage(value=AutoMain.ACT_GOTLONG, code="Container2")
   private void actionGotLong(long value)
   {
      System.out.println("I got a long value : " + value);
   }
   @Manage(value=ACT_GOTDOUBLE)
   private void actionGotDouble(double value)
   {
      System.out.println("I got a double value : " + value);
   }
}
