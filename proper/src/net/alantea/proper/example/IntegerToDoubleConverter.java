package net.alantea.proper.example;

import net.alantea.proper.Associate;
import net.alantea.proper.Manage;
import net.alantea.proper.Require;

@Require(key=Container.PROP_ONE, type=Integer.class, action=IntegerToDoubleConverter.ACT_GOTINTEGER)
@Require(key=IntegerToDoubleConverter.PROP_TWO, type=Double.class)
public class IntegerToDoubleConverter
{
   public static final String PROP_TWO = "PropertyTwo";
   public static final String ACT_GOTINTEGER = "GotInteger";

   @Associate(Container.PROP_ONE)
   private int reference;
   
   @Associate(Container.PROP_THIS)
   private Container container;
   
   @Manage(ACT_GOTINTEGER)
   private void actionGotInteger()
   {
      container.setPropertyValue(IntegerToDoubleConverter.PROP_TWO, (double)reference);
   }
}
