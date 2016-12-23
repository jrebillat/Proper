package net.alantea.proper.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import net.alantea.proper.test.material.bindtest.Names;
import net.alantea.proper.test.material.bindtest.SimpleBiDirectionalElement;
import net.alantea.proper.test.material.bindtest.SimpleContainer;
import net.alantea.proper.test.material.bindtest.SimpleElement;
import net.alantea.proper.test.material.bindtest.SimpleKeyCodedElement;

public class BindTest
{
   @Test
   public void simpleBindTest()
   {
      SimpleElement element = new SimpleElement();
      SimpleContainer container = new SimpleContainer();
      container.associate(element);
      
      container.setSimpleKey(Names.SIMPLEBINDTESTVALUE);
      
      String got = element.getSimpleKey();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, Names.SIMPLEBINDTESTVALUE);
      
      try
      {
         element.setSimpleKey(Names.SIMPLEBINDTESTVALUE);
         Assert.fail("expect RuntimeException");
      }
      catch (RuntimeException e)
      {
         // OK
      }
   }
   
   @Test
   public void simpleBindBiDirectionalTest()
   {
      SimpleBiDirectionalElement element = new SimpleBiDirectionalElement();
      SimpleContainer container = new SimpleContainer();
      container.associate(element);
      
      try
      {
         element.setSimpleKey(Names.SIMPLEBINDTESTVALUE);
      }
      catch (RuntimeException e)
      {
         Assert.fail("got a RuntimeException");
      }
      
      String got = container.getSimpleKey();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, Names.SIMPLEBINDTESTVALUE);
   }
   
   @Test
   public void simpleKeycodedBindTest()
   {
      SimpleKeyCodedElement element = new SimpleKeyCodedElement();
      SimpleContainer container1 = new SimpleContainer();
      container1.associate(Names.SIMPLEKEYCODE1, element);

      SimpleContainer container2 = new SimpleContainer();
      container2.associate(Names.SIMPLEKEYCODE2, element);
      
      container1.setSimpleKey(Names.SIMPLEKEYCODEDBINDTESTVALUE1);
      container2.setSimpleKey(Names.SIMPLEKEYCODEDBINDTESTVALUE2);
      
      String got = element.getSimpleKey1();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, Names.SIMPLEKEYCODEDBINDTESTVALUE1);
      
      got = element.getSimpleKey2();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, Names.SIMPLEKEYCODEDBINDTESTVALUE2);
      
   }
}
