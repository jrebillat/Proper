package net.alantea.proper.test.material.registertest;

import net.alantea.proper.MappedPropertyContainer;

public class SimpleContainer extends MappedPropertyContainer
{
   public SimpleContainer()
   {
      super();
   }
   
   public Object getSimpleKeyCode1()
   {
      return getPropertyValue(Names.SIMPLENAME1);
   }
   
   public Object getSimpleKeyCode2()
   {
      return getPropertyValue(Names.SIMPLENAME2);
   }
}
