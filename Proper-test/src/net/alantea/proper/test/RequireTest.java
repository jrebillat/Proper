package net.alantea.proper.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import net.alantea.proper.test.material.requiretest.BasicElement;
import net.alantea.proper.test.material.requiretest.ImportFromElement;
import net.alantea.proper.test.material.requiretest.Names;
import net.alantea.proper.test.material.requiretest.SampleContainer;
import net.alantea.proper.test.material.requiretest.SampleElement;
import net.alantea.proper.test.material.requiretest.SimpleContainer;
import net.alantea.proper.test.material.requiretest.SimpleElement;
import net.alantea.proper.test.material.requiretest.SimpleKeyCodedElement;

public class RequireTest
{
   @Test
   public void sampleRequireTest()
   {
      SampleContainer container = new SampleContainer();
      
      boolean got = container.hasProperty(Names.SIMPLEKEYNAME1);
      Assert.assertFalse(got);
      
      got = container.hasProperty(Names.SIMPLEKEYNAME1, String.class);
      Assert.assertFalse(got);
      
      SampleElement element = new SampleElement();
      container.associate(element);
      
      got = container.hasProperty(Names.SIMPLEKEYNAME1);
      Assert.assertTrue(got);
      
      got = container.hasProperty(Names.SIMPLEKEYNAME1, String.class);
      Assert.assertTrue(got);
      
      got = container.hasProperty(Names.SIMPLEKEYNAME1, Double.class);
      Assert.assertFalse(got);
   }
   
   @Test
   public void basicElementRequireTest()
   {
      BasicElement element = new BasicElement();
      SimpleContainer container = new SimpleContainer();
      container.associate(element);
      
      double got = element.getSimpleKey();
      Assert.assertEquals(got, 0.0);
      
      container.setPropertyValue(Names.SIMPLEKEYNAME1, 1.0);
      got = element.getSimpleKey();
      Assert.assertEquals(got, 123.0);
   }
   
   @Test
   public void simpleContainerRequireTest()
   {
      SimpleContainer container = new SimpleContainer();
      
      String got = container.getSimpleKey();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, "");
      
      container.setSimpleKey(Names.SIMPLETESTVALUE);
      got = container.getSimpleKey();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, Names.SIMPLETESTVALUE);
      
      container.setPropertyValue(Names.SIMPLEKEYNAME, null);
      got = container.getSimpleKey();
      Assert.assertNull(got);
      
      container.setPropertyValue(Names.SIMPLEKEYNAME, container);
      got = container.getSimpleKey();
      Assert.assertNull(got);

      container.setSimpleKey(Names.SIMPLETESTVALUE);
      container.setPropertyValue(Names.SIMPLEKEYNAME, container);
      got = container.getSimpleKey();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, Names.SIMPLETESTVALUE);
   }
   
   @Test
   public void simpleElementRequireTest()
   {
      SimpleElement element = new SimpleElement();
      SimpleContainer container = new SimpleContainer();
      container.associate(element);
      
      double got = element.getSimpleKey();
      Assert.assertEquals(got, 0.0);
      
      container.setPropertyValue(Names.SIMPLEKEYNAME1, 1.0);
      got = element.getSimpleKey();
      Assert.assertEquals(got, 1.0);
   }

   @Test
   public void sampleKeyCodeRequireTest()
   {
      SampleContainer container1 = new SampleContainer();
      SampleContainer container2 = new SampleContainer();
      
      SimpleKeyCodedElement element = new SimpleKeyCodedElement();
      container1.associate(Names.SIMPLEKEYCODE1, element);
      container2.associate(Names.SIMPLEKEYCODE2, element);
      
      boolean got = container1.hasProperty(Names.SIMPLEKEYNAME1);
      Assert.assertTrue(got);
      got = container1.hasProperty(Names.SIMPLEKEYNAME1, String.class);
      Assert.assertTrue(got);
      got = container1.hasProperty(Names.SIMPLEKEYNAME1, Double.class);
      Assert.assertFalse(got);
      
      got = container2.hasProperty(Names.SIMPLEKEYNAME1);
      Assert.assertTrue(got);
      got = container2.hasProperty(Names.SIMPLEKEYNAME1, String.class);
      Assert.assertFalse(got);
      got = container2.hasProperty(Names.SIMPLEKEYNAME1, Double.class);
      Assert.assertTrue(got);
   }

   @Test
   public void importFromRequireTest()
   {
      SampleContainer container1 = new SampleContainer();
      SampleContainer container2 = new SampleContainer();
      
      container1.setPropertyValue(Names.SIMPLEKEYNAME, Names.SIMPLETESTVALUE);
      
      ImportFromElement element = new ImportFromElement();
      container2.associate(element);
      container1.associate(Names.SIMPLEKEYCODE1, element);
      
      String got = container2.getPropertyValue(Names.SIMPLEKEYNAME);
      Assert.assertNotNull(got);
      Assert.assertEquals(got, Names.SIMPLETESTVALUE);
   }
//   
//   @Test
//   public void doubleRegisterTest()
//   {
//      SimpleKeyCodedElement element = new SimpleKeyCodedElement();
//      SimpleContainer container1 = new SimpleContainer();
//      SimpleContainer container2 = new SimpleContainer();
//      
//      container1.associate(Names.SIMPLEKEYCODE1, element);
//      Object got = container1.getSimpleKeyCode1();
//      Assert.assertNotNull(got);
//      Assert.assertEquals(got, element);
//      got = container1.getSimpleKeyCode2();
//      Assert.assertNull(got);
//      
//      container2.associate(Names.SIMPLEKEYCODE2, element);
//      got = container2.getSimpleKeyCode1();
//      Assert.assertNull(got);
//      got = container2.getSimpleKeyCode2();
//      Assert.assertNotNull(got);
//      Assert.assertEquals(got, element);
//   }
//   
//   @Test
//   public void multipleRegisterTest()
//   {
//      SimpleElement element1 = new SimpleElement();
//      SecondElement element2 = new SecondElement();
//      SimpleContainer container = new SimpleContainer();
//      
//      container.associate(element1);
//      container.associate(element2);
//      
//      Object got = container.getSimpleKeyCode1();
//      Assert.assertNotNull(got);
//      Assert.assertEquals(got, element1);
//      
//      got = container.getSimpleKeyCode2();
//      Assert.assertNotNull(got);
//      Assert.assertEquals(got, element2);
//   }
}
