package net.alantea.proper.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import net.alantea.proper.test.material.associatetest.Names;
import net.alantea.proper.test.material.associatetest.SimpleContainer;
import net.alantea.proper.test.material.associatetest.SimpleElement;
import net.alantea.proper.test.material.associatetest.SimpleKeyCodedElement;


public class AssociateTest
{
   @Test
   public void simpleAsociateTest()
   {
      SimpleElement element = new SimpleElement();
      SimpleContainer container = new SimpleContainer();
      container.associate(element);
      
      container.setSimpleKey(Names.SIMPLEASSOCIATETESTVALUE);
      
      String got = element.getSimpleKey();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, Names.SIMPLEASSOCIATETESTVALUE);
      
      container.setSimpleKey(Names.SIMPLEKEYCODEDASSOCIATETESTVALUE1);
      
      got = element.getSimpleKey();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, Names.SIMPLEKEYCODEDASSOCIATETESTVALUE1);
      
      container.setSimpleKey(Names.SIMPLEKEYCODEDASSOCIATETESTVALUE2);
      
      got = element.getSimpleKey();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, Names.SIMPLEKEYCODEDASSOCIATETESTVALUE2);
   }
   
   @Test
   public void simpleKeycodedBindTest()
   {
      SimpleKeyCodedElement element = new SimpleKeyCodedElement();
      SimpleContainer container1 = new SimpleContainer();
      container1.associate(Names.SIMPLEKEYCODE1, element);

      SimpleContainer container2 = new SimpleContainer();
      container2.associate(Names.SIMPLEKEYCODE2, element);
      
      container1.setSimpleKey(Names.SIMPLEKEYCODEDASSOCIATETESTVALUE1);
      container2.setSimpleKey(Names.SIMPLEKEYCODEDASSOCIATETESTVALUE2);
      
      String got = element.getSimpleKey1();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, Names.SIMPLEKEYCODEDASSOCIATETESTVALUE1);
      
      got = element.getSimpleKey2();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, Names.SIMPLEKEYCODEDASSOCIATETESTVALUE2);
      
   }
}
