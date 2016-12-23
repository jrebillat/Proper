package net.alantea.proper.example.example2;

import net.alantea.proper.MappedPropertyContainer;
import net.alantea.proper.Require;

@Require(key=Container1.PROP_ONE, type=Integer.class, action=Container1.ACT_GOTINTEGER)
public class Container1 extends MappedPropertyContainer
{
   public static final String PROP_ONE = "PropertyOne";
   public static final String ACT_GOTINTEGER = "GotInteger";

}
