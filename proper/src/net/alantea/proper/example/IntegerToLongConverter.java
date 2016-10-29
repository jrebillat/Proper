package net.alantea.proper.example;

import net.alantea.proper.Associate;
import net.alantea.proper.Manage;
import net.alantea.proper.Require;

@Require(key=Container.PROP_ONE, type=Integer.class, action=IntegerToLongConverter.ACT_GOTINTEGER)
@Require(key=IntegerToLongConverter.PROP_TWO, type=Long.class)
public class IntegerToLongConverter
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
      System.out.println("got integer : " + reference);
      container.setPropertyValue(IntegerToLongConverter.PROP_TWO, (long)reference);
   }
}
