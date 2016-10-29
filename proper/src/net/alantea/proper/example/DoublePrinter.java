package net.alantea.proper.example;

import net.alantea.proper.Manage;
import net.alantea.proper.Require;

@Require(key=LongToDoubleConverter.PROP_THREE, type=Double.class, action=DoublePrinter.ACT_GOTDOUBLE)
public class DoublePrinter
{
   public static final String ACT_GOTDOUBLE = "GotDouble";

   @Manage(ACT_GOTDOUBLE)
   private void actionGotDouble(double value)
   {
      System.out.println("I got a double value : " + value);
   }
}
