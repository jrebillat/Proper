package net.alantea.proper.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import net.alantea.proper.test.material.initializetest.Names;
import net.alantea.proper.test.material.initializetest.SimpleContainer;
import net.alantea.proper.test.material.initializetest.SimpleElement;
import net.alantea.proper.test.material.initializetest.SimpleKeyCodedElement;

public class InitializeTest
{
   @Test
   public void simpleAsociateTest()
   {
      SimpleElement element1 = new SimpleElement();
      SimpleContainer container = new SimpleContainer();
      container.associate(element1);
      
      container.setSimpleKey(Names.SIMPLEINITIALIZETESTVALUE);
      String got = element1.getSimpleKey();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, "");

      SimpleElement element2 = new SimpleElement();
      container.associate(element2);
      got = element2.getSimpleKey();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, Names.SIMPLEINITIALIZETESTVALUE);

      container.setSimpleKey(Names.SIMPLEKEYCODEDINITIALIZETESTVALUE1);
      got = element2.getSimpleKey();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, Names.SIMPLEINITIALIZETESTVALUE);
      
      container.setSimpleKey(Names.SIMPLEKEYCODEDINITIALIZETESTVALUE2);
      got = element2.getSimpleKey();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, Names.SIMPLEINITIALIZETESTVALUE);
   }
   
   @Test
   public void simpleKeycodedBindTest()
   {
      SimpleKeyCodedElement element = new SimpleKeyCodedElement();
      SimpleContainer container1 = new SimpleContainer();
      SimpleContainer container2 = new SimpleContainer();
      
      container1.setSimpleKey(Names.SIMPLEKEYCODEDINITIALIZETESTVALUE1);
      container2.setSimpleKey(Names.SIMPLEKEYCODEDINITIALIZETESTVALUE2);
      
      container1.associate(Names.SIMPLEKEYCODE1, element);
      container2.associate(Names.SIMPLEKEYCODE2, element);
      
      String got = element.getSimpleKey1();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, Names.SIMPLEKEYCODEDINITIALIZETESTVALUE1);
      
      got = element.getSimpleKey2();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, Names.SIMPLEKEYCODEDINITIALIZETESTVALUE2);
      
   }
}
