package net.alantea.proper.example.example4;

import net.alantea.proper.MappedPropertyContainer;
import net.alantea.proper.Require;

@Require(key=Container.PROP_ONE, type=Integer.class, action=Container.ACT_GOTINTEGER)
public class Container extends MappedPropertyContainer
{
   public static final String PROP_ONE = "PropertyOne";
   public static final String ACT_GOTINTEGER = "GotInteger";

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
