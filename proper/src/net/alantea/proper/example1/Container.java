package net.alantea.proper.example1;

import net.alantea.proper.PropertyContainer;
import net.alantea.proper.Require;

@Require(key=Container.PROP_ONE, type=Integer.class, action=Container.ACT_GOTINTEGER)
public class Container extends PropertyContainer
{
   public static final String PROP_ONE = "PropertyOne";
   public static final String ACT_GOTINTEGER = "GotInteger";

   public static void main(String[] args)
   {
      Container cont = new Container();
      PropertyContainer.associate(cont, new IntegerToLongConverter());
      PropertyContainer.associate(cont, new LongToDoubleConverter());
      PropertyContainer.associate(cont, new DoublePrinter());
      
      Container.setPropertyValue(cont, PROP_ONE, 1);
      Container.setPropertyValue(cont, PROP_ONE, 2);
      Container.setPropertyValue(cont, PROP_ONE, 3);
   }
}
