package net.alantea.proper.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import net.alantea.proper.EventMessage;
import net.alantea.proper.test.material.manageerrortest.MultipleElement;
import net.alantea.proper.test.material.manageerrortest.Names;
import net.alantea.proper.test.material.manageerrortest.SimpleContainer;
import net.alantea.proper.test.material.manageerrortest.SimpleElement;

public class ManageErrorTest
{
   @Test
   public void simpleAsociateTest()
   {
      SimpleElement element = new SimpleElement();
      SimpleContainer container = new SimpleContainer();
      container.associate(element);
      
      EventMessage.sendErrorMessage(EventMessage.Level.INFORMATION, Names.MESSAGE1);
      String got = element.getSimpleKey();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, EventMessage.Level.INFORMATION.name());
      got = element.getMessage();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, Names.MESSAGE1);
      
      EventMessage.sendErrorMessage(EventMessage.Level.WARNING, Names.MESSAGE2);
      got = element.getSimpleKey();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, EventMessage.Level.WARNING.name());
      got = element.getMessage();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, Names.MESSAGE2);
      
      EventMessage.sendErrorMessage(EventMessage.Level.ERROR, Names.MESSAGE3);
      got = element.getSimpleKey();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, EventMessage.Level.ERROR.name());
      got = element.getMessage();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, Names.MESSAGE3);
      
      EventMessage.sendErrorMessage(EventMessage.Level.FATALERROR, Names.MESSAGE4);
      got = element.getSimpleKey();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, EventMessage.Level.FATALERROR.name());
      got = element.getMessage();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, Names.MESSAGE4);
   }
   
   @Test
   public void multipleAsociateTest()
   {
      MultipleElement element = new MultipleElement();
      SimpleContainer container = new SimpleContainer();
      container.associate(element);
      
      EventMessage.sendErrorMessage(EventMessage.Level.INFORMATION, Names.MESSAGE1);
      String got = element.getSimpleKey();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, EventMessage.Level.INFORMATION.name());
      got = element.getMessage();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, Names.MESSAGE1);
      
      EventMessage.sendErrorMessage(EventMessage.Level.WARNING, Names.MESSAGE2);
      got = element.getSimpleKey();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, EventMessage.Level.WARNING.name());
      got = element.getMessage();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, Names.MESSAGE2);
      
      EventMessage.sendErrorMessage(EventMessage.Level.ERROR, Names.MESSAGE3);
      got = element.getSimpleKey();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, EventMessage.Level.ERROR.name());
      got = element.getMessage();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, Names.MESSAGE3);
      
      EventMessage.sendErrorMessage(EventMessage.Level.FATALERROR, Names.MESSAGE4);
      got = element.getSimpleKey();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, EventMessage.Level.FATALERROR.name());
      got = element.getMessage();
      Assert.assertNotNull(got);
      Assert.assertEquals(got, Names.MESSAGE4);
   }
}
