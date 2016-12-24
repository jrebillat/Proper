package net.alantea.proper.test.material.requiretest;

import net.alantea.proper.Require;

@Require(key=Names.SIMPLEKEYNAME1, type = String.class, code=Names.SIMPLEKEYCODE1)
@Require(key=Names.SIMPLEKEYNAME1, type = Double.class, code=Names.SIMPLEKEYCODE2)
public class SimpleKeyCodedElement
{
}
