package net.alantea.proper.example;

import net.alantea.proper.PropertyContainer;
import net.alantea.proper.Require;


@Require(key=Container.PROP_ONE, type=Integer.class)
public class Container extends PropertyContainer
{
   public static final String PROP_ONE = "PropertyOne";

   public static void main(String[] args)
   {
      Container cont = new Container();
      cont.associate(new IntegerToLongConverter());
      cont.associate(new LongToDoubleConverter());
      cont.associate(new DoublePrinter());
      
      cont.setPropertyValue(PROP_ONE, 1);
      cont.setPropertyValue(PROP_ONE, 2);
      cont.setPropertyValue(PROP_ONE, 3);
   }

}
