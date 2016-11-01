package net.alantea.proper.example2;

import net.alantea.proper.PropertyContainer;
import net.alantea.proper.Require;

@Require(key=Container2.PROP_TWO, type=Long.class, action=Container2.ACT_GOTLONG)
public class Container2 extends PropertyContainer
{
   public static final String PROP_TWO = "PropertyTwo";
   public static final String ACT_GOTLONG = "GotLong";

}
