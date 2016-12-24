package net.alantea.proper.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import net.alantea.proper.test.material.registertest.Names;
import net.alantea.proper.test.material.registertest.SecondElement;
import net.alantea.proper.test.material.registertest.SimpleContainer;
import net.alantea.proper.test.material.registertest.SimpleElement;
import net.alantea.proper.test.material.registertest.SimpleKeyCodedElement;

public class RegisterTest
{
   @Test
   public void simpleRegisterTest()
   {
      SimpleElement element = new SimpleElement();
      SimpleContainer container1 = new SimpleContainer();
      SimpleContainer container2 = new SimpleContainer();
      
      Object got = container1.getSimpleKeyCode1();
      Assert.assertNull(got);
      
      container1.associate(element);
      got = container1.getSimpleKeyCode1();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, element);
      
      container2.associate(element);
      got = container2.getSimpleKeyCode1();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, element);
   }
   
   @Test
   public void doubleRegisterTest()
   {
      SimpleKeyCodedElement element = new SimpleKeyCodedElement();
      SimpleContainer container1 = new SimpleContainer();
      SimpleContainer container2 = new SimpleContainer();
      
      container1.associate(Names.SIMPLEKEYCODE1, element);
      Object got = container1.getSimpleKeyCode1();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, element);
      got = container1.getSimpleKeyCode2();
      Assert.assertNull(got);
      
      container2.associate(Names.SIMPLEKEYCODE2, element);
      got = container2.getSimpleKeyCode1();
      Assert.assertNull(got);
      got = container2.getSimpleKeyCode2();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, element);
   }
   
   @Test
   public void multipleRegisterTest()
   {
      SimpleElement element1 = new SimpleElement();
      SecondElement element2 = new SecondElement();
      SimpleContainer container = new SimpleContainer();
      
      container.associate(element1);
      container.associate(element2);
      
      Object got = container.getSimpleKeyCode1();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, element1);
      
      got = container.getSimpleKeyCode2();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, element2);
   }
}
