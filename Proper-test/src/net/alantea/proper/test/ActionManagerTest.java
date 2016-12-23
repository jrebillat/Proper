package net.alantea.proper.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import net.alantea.proper.ActionManager;
import net.alantea.proper.test.material.actionmanagertest.Names;
import net.alantea.proper.test.material.actionmanagertest.SimpleActionManagementElement;

/**
 * The Class ActionManagerTest.
 */
public class ActionManagerTest
{
   
   /**
    * Simple creation test.
    */
   @Test
   public void simpleCreationTest()
   {
      ActionManager manager = new ActionManager();
      Assert.assertNotNull(manager);
   }
   
   /**
    * Simple creation test with associates.
    */
   @Test(dependsOnMethods = { "simpleCreationTest" })
   public void simpleCreationTestWithAssociates()
   {
      ActionManager manager = new ActionManager(new Integer(0), new Double(0.0));
      Assert.assertNotNull(manager);
   }
   
   /**
    * Simple creation test with name.
    */
   @Test(dependsOnMethods = { "simpleCreationTest" })
   public void simpleCreationTestWithName()
   {
      ActionManager manager = new ActionManager(Names.SIMPLECREATIONWITHNAMETESTNAME);
      Assert.assertNotNull(manager);
   }
   
   /**
    * Gets the named manager test.
    *
    */
   @Test(dependsOnMethods = { "simpleCreationTestWithName"})
   public void getNamedManagerTest()
   {
      ActionManager manager = new ActionManager(Names.CREATIONFORGETWITHNAMETESTNAME);
      Assert.assertNotNull(manager);
      
      ActionManager manager1 = ActionManager.getManager(Names.CREATIONFORGETWITHNAMETESTNAME);
      Assert.assertNotNull(manager1);
      Assert.assertEquals(manager, manager1);
   }
   
   /**
    * Simple action test.
    */
   @Test(dependsOnMethods = { "simpleCreationTest" })
   public void simpleActionTest()
   {
      ActionManager manager = new ActionManager();
      Assert.assertNotNull(manager);
      
      SimpleActionManagementElement element = new SimpleActionManagementElement(null);
      manager.associateActions(element);
      
      String s = element.getReference();
      Assert.assertNull(s);
      
      manager.execute(Names.SAMPLEACTIONNAME);
      
      s = element.getReference();
      Assert.assertNotNull(s);
      Assert.assertEquals(s, Names.SAMPLEACTIONRESPONSE);
   }
   
   /**
    * Simple action with parameter test.
    */
   @Test(dependsOnMethods = { "simpleCreationTest", "simpleActionTest" })
   public void simpleActionWithParameterTest()
   {
      ActionManager manager = new ActionManager();
      Assert.assertNotNull(manager);
      
      SimpleActionManagementElement element = new SimpleActionManagementElement(null);
      manager.associateActions(element);
      
      String s = element.getReference();
      Assert.assertNull(s);
      
      manager.execute(Names.SAMPLEACTIONWITHPARAMETERNAME, (Object)Names.SAMPLEACTIONWITHPARAMETERPARAMETER);
      
      s = element.getReference();
      Assert.assertNotNull(s);
      Assert.assertEquals(s, Names.SAMPLEACTIONWITHPARAMETERRESPONSE);
      
      manager.execute(Names.SAMPLEACTIONWITHPARAMETERNAME, (Object)Names.BADSAMPLEACTIONWITHPARAMETERPARAMETER);
      
      s = element.getReference();
      Assert.assertNull(s);
   }
   
   /**
    * Simple action with parameters test.
    */
   @Test(dependsOnMethods = { "simpleCreationTest", "simpleActionTest" })
   public void simpleActionWithParametersTest()
   {
      ActionManager manager = new ActionManager();
      Assert.assertNotNull(manager);
      
      SimpleActionManagementElement element = new SimpleActionManagementElement(null);
      manager.associateActions(element);
      
      String s = element.getReference();
      Assert.assertNull(s);
      
      manager.execute(Names.SAMPLEACTIONWITHPARAMETERSNAME,
            (Object)Names.SAMPLEACTIONWITHPARAMETERSPARAMETER1,
            (Object)Names.SAMPLEACTIONWITHPARAMETERSPARAMETER2,
            (Object)Names.SAMPLEACTIONWITHPARAMETERSPARAMETER3);
      
      s = element.getReference();
      Assert.assertNotNull(s);
      Assert.assertEquals(s, Names.SAMPLEACTIONWITHPARAMETERSRESPONSE);
      
      manager.execute(Names.SAMPLEACTIONWITHPARAMETERSNAME, 
            (Object)Names.SAMPLEACTIONWITHPARAMETERSPARAMETER1,
            (Object)Names.SAMPLEACTIONWITHPARAMETERSPARAMETER2,
            (Object)Names.BADSAMPLEACTIONWITHPARAMETERPARAMETER);
      
      s = element.getReference();
      Assert.assertNull(s);
   }
   
   /**
    * Simple action test.
    */
   @Test(dependsOnMethods = { "getNamedManagerTest" })
   public void simpleActionNamedTest()
   {
      ActionManager manager = new ActionManager(Names.SIMPLEACTIONNAMETESTNAME);
      Assert.assertNotNull(manager);
      
      SimpleActionManagementElement element = new SimpleActionManagementElement(null);
      manager.associateActions(element);
      
      String s = element.getReference();
      Assert.assertNull(s);
      
      manager.execute(Names.SAMPLEACTIONNAME);
      
      s = element.getReference();
      Assert.assertNotNull(s);
      Assert.assertEquals(s, Names.SAMPLEACTIONRESPONSE);
   }
   
   /**
    * Simple action with parameter test.
    */
   @Test(dependsOnMethods = { "getNamedManagerTest", "simpleActionNamedTest" })
   public void simpleActionWithParameterNamedTest()
   {
      ActionManager manager = new ActionManager(Names.SIMPLEACTIONWITHPARAMETERNAMETESTNAME);
      Assert.assertNotNull(manager);
      
      SimpleActionManagementElement element = new SimpleActionManagementElement(null);
      manager.associateActions(element);
      
      String s = element.getReference();
      Assert.assertNull(s);
      
      manager.execute(Names.SAMPLEACTIONWITHPARAMETERNAME, (Object)Names.SAMPLEACTIONWITHPARAMETERPARAMETER);
      
      s = element.getReference();
      Assert.assertNotNull(s);
      Assert.assertEquals(s, Names.SAMPLEACTIONWITHPARAMETERRESPONSE);
      
      manager.execute(Names.SAMPLEACTIONWITHPARAMETERNAME, (Object)Names.BADSAMPLEACTIONWITHPARAMETERPARAMETER);
      
      s = element.getReference();
      Assert.assertNull(s);
   }
   
   /**
    * Simple action with parameters test.
    */
   @Test(dependsOnMethods = { "getNamedManagerTest", "simpleActionNamedTest" })
   public void simpleActionWithParametersNamedTest()
   {
      ActionManager manager = new ActionManager(Names.SIMPLEACTIONWITHPARAMETERSNAMETESTNAME);
      Assert.assertNotNull(manager);
      
      SimpleActionManagementElement element = new SimpleActionManagementElement(null);
      manager.associateActions(element);
      
      String s = element.getReference();
      Assert.assertNull(s);
      
      manager.execute(Names.SAMPLEACTIONWITHPARAMETERSNAME,
            (Object)Names.SAMPLEACTIONWITHPARAMETERSPARAMETER1,
            (Object)Names.SAMPLEACTIONWITHPARAMETERSPARAMETER2,
            (Object)Names.SAMPLEACTIONWITHPARAMETERSPARAMETER3);
      
      s = element.getReference();
      Assert.assertNotNull(s);
      Assert.assertEquals(s, Names.SAMPLEACTIONWITHPARAMETERSRESPONSE);
      
      manager.execute(Names.SAMPLEACTIONWITHPARAMETERSNAME, 
            (Object)Names.SAMPLEACTIONWITHPARAMETERSPARAMETER1,
            (Object)Names.SAMPLEACTIONWITHPARAMETERSPARAMETER2,
            (Object)Names.BADSAMPLEACTIONWITHPARAMETERPARAMETER);
      
      s = element.getReference();
      Assert.assertNull(s);
   }
   
   /**
    * Simple action test with key codes (first part).
    */
   @Test
   public void simpleActionWithKeyCodesTest1()
   {
      ActionManager manager = new ActionManager();
      Assert.assertNotNull(manager);
      
      SimpleActionManagementElement element = new SimpleActionManagementElement(null);
      manager.associateActions(Names.KEYCODE1, element);
      
      String s = element.getReference();
      Assert.assertNull(s);
      
      manager.execute(Names.SAMPLEACTIONNAME);
      s = element.getReference();
      Assert.assertNotNull(s);
      Assert.assertEquals(s, Names.SAMPLEACTIONRESPONSEWITHKEYCODE1);
   }
   
   /**
    * Simple action test with key codes (second part).
    */
   @Test
   public void simpleActionWithKeyCodesTest2()
   {
      ActionManager manager = new ActionManager();
      Assert.assertNotNull(manager);
      
      SimpleActionManagementElement element = new SimpleActionManagementElement(null);
      manager.associateActions(Names.KEYCODE2, element);
      
      String s = element.getReference();
      Assert.assertNull(s);
      
      manager.execute(Names.SAMPLEACTIONNAME);
      s = element.getReference();
      Assert.assertNotNull(s);
      Assert.assertEquals(s, Names.SAMPLEACTIONRESPONSEWITHKEYCODE2);
   }
}
