package net.alantea.proper.example1;

import net.alantea.proper.Associate;
import net.alantea.proper.Initialize;
import net.alantea.proper.Manage;
import net.alantea.proper.Require;

@Require(key=Container.PROP_ONE, type=Integer.class)
@Require(key=IntegerToLongConverter.PROP_TWO, type=Long.class)
public class IntegerToLongConverter
{
   public static final String PROP_TWO = "PropertyTwo";

   @Associate(Container.PROP_ONE)
   private int reference;
   
   @Initialize(Container.PROP_THIS)
   private Container container;
   
   @Manage(Container.ACT_GOTINTEGER)
   private void actionGotInteger()
   {
      System.out.println("got integer : " + reference);
      container.setPropertyValue(IntegerToLongConverter.PROP_TWO, (long)reference);
   }
}
