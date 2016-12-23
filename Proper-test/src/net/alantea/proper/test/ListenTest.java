package net.alantea.proper.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import net.alantea.proper.test.material.listentest.Names;
import net.alantea.proper.test.material.listentest.SimpleContainer;
import net.alantea.proper.test.material.listentest.SimpleElement;
import net.alantea.proper.test.material.listentest.SimpleKeyCodedElement;

public class ListenTest
{
   @Test
   public void simpleAsociateTest()
   {
      SimpleElement element1 = new SimpleElement();
      SimpleContainer container = new SimpleContainer();
      container.associate(element1);
      
      container.setSimpleKey(Names.SIMPLELISTENTESTVALUE);
      String got = element1.getSimpleKey();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, Names.SIMPLELISTENTESTVALUE);

   }
   
   @Test
   public void doubleAsociateTest()
   {
      SimpleElement element1 = new SimpleElement();
      SimpleContainer container = new SimpleContainer();
      container.associate(element1);
      
      container.setSimpleKey(Names.SIMPLELISTENTESTVALUE);
      String got = element1.getSimpleKey();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, Names.SIMPLELISTENTESTVALUE);

      SimpleElement element2 = new SimpleElement();
      container.associate(element2);
      got = element2.getSimpleKey();
      Assert.assertNull(got); // Property not changed = element not initialized

      container.setSimpleKey(Names.SIMPLEKEYCODEDLISTENTESTVALUE1);
      got = element2.getSimpleKey();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, Names.SIMPLEKEYCODEDLISTENTESTVALUE1);
      
      container.setSimpleKey(Names.SIMPLEKEYCODEDLISTENTESTVALUE2);
      got = element2.getSimpleKey();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, Names.SIMPLEKEYCODEDLISTENTESTVALUE2);
   }
   
   @Test
   public void simpleKeycodedBindTest()
   {
      SimpleKeyCodedElement element = new SimpleKeyCodedElement();
      SimpleContainer container1 = new SimpleContainer();
      SimpleContainer container2 = new SimpleContainer();
      
      container1.associate(Names.SIMPLEKEYCODE1, element);
      container2.associate(Names.SIMPLEKEYCODE2, element);
      
      container1.setSimpleKey(Names.SIMPLEKEYCODEDLISTENTESTVALUE1);
      container2.setSimpleKey(Names.SIMPLEKEYCODEDLISTENTESTVALUE2);
      
      String got = element.getSimpleKey1();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, Names.SIMPLEKEYCODEDLISTENTESTVALUE1);
      
      got = element.getSimpleKey2();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, Names.SIMPLEKEYCODEDLISTENTESTVALUE2);
      
   }
}
