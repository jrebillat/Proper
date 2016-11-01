package net.alantea.proper.example1;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import net.alantea.proper.Associate;
import net.alantea.proper.Bind;
import net.alantea.proper.Manage;
import net.alantea.proper.Require;

@Require(key=IntegerToLongConverter.PROP_TWO, type=Long.class, action=LongToDoubleConverter.ACT_GOTLONG)
@Require(key=LongToDoubleConverter.PROP_THREE, type=Double.class)
public class LongToDoubleConverter
{
   public static final String PROP_THREE = "PropertyThree";
   public static final String ACT_GOTLONG = "GotLong";

   @Bind(IntegerToLongConverter.PROP_TWO)
   private LongProperty reference = new SimpleLongProperty();
   
   @Associate(Container.PROP_THIS)
   private Container container;
   
   @Manage(ACT_GOTLONG)
   private void actionGotLong()
   {
      System.out.println("got long : " + reference.longValue());
      container.setPropertyValue(LongToDoubleConverter.PROP_THREE, (double)reference.get());
   }
}
