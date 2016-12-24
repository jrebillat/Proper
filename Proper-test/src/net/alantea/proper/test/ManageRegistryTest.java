package net.alantea.proper.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import net.alantea.proper.test.material.manageregistrytest.MultipleElement;
import net.alantea.proper.test.material.manageregistrytest.Names;
import net.alantea.proper.test.material.manageregistrytest.SimpleContainer;
import net.alantea.proper.test.material.manageregistrytest.SimpleElement;
import net.alantea.proper.test.material.manageregistrytest.SimpleKeyCodedElement;

public class ManageRegistryTest
{
   @Test
   public void simpleManageRegistryTest()
   {
      SimpleElement element1 = new SimpleElement();
      SimpleContainer container = new SimpleContainer();
      
      boolean got = element1.isInitialized();
      Assert.assertFalse(got);
      
      container.associate(element1);
      
      got = element1.isInitialized();
      Assert.assertTrue(got);

   }
   
   @Test
   public void doubleManageRegistryTest()
   {
      SimpleKeyCodedElement element = new SimpleKeyCodedElement();
      SimpleContainer container1 = new SimpleContainer();
      SimpleContainer container2 = new SimpleContainer();
      
      boolean got = element.isInitialized1();
      Assert.assertFalse(got);
      
      container1.associate(Names.SIMPLEKEYCODE1, element);
      got = element.isInitialized1();
      Assert.assertTrue(got);
      got = element.isInitialized2();
      Assert.assertFalse(got);
      
      container2.associate(Names.SIMPLEKEYCODE2, element);
      got = element.isInitialized1();
      Assert.assertTrue(got);
      got = element.isInitialized2();
      Assert.assertTrue(got);
   }
   
   @Test
   public void multipleManageRegistryTest()
   {
      MultipleElement element = new MultipleElement();
      SimpleContainer container1 = new SimpleContainer();
      SimpleContainer container2 = new SimpleContainer();
      
      int got = element.getStored();
      Assert.assertEquals(got, 0);
      
      container1.associate(Names.SIMPLEKEYCODE1, element);
      got = element.getStored();
      Assert.assertEquals(got, 1);
      
      container2.associate(Names.SIMPLEKEYCODE2, element);
      got = element.getStored();
      Assert.assertEquals(got, 2);
   }
}
