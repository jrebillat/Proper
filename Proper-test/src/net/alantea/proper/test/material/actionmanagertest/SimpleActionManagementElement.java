package net.alantea.proper.test.material.actionmanagertest;

import net.alantea.proper.Manage;

public class SimpleActionManagementElement
{
   private String reference = null;

   public SimpleActionManagementElement(String reference)
   {
      this.reference = reference;
   }

   public String getReference()
   {
      return reference;
   }

   @Manage(Names.SAMPLEACTIONNAME)
   private void manageSampleAction()
   {
      reference = Names.SAMPLEACTIONRESPONSE;
   }

   @Manage(Names.SAMPLEACTIONWITHPARAMETERNAME)
   private void manageSampleActionWithParameter(String parameter)
   {
      reference = null;
      if (Names.SAMPLEACTIONWITHPARAMETERPARAMETER.equals(parameter))
      {
         reference = Names.SAMPLEACTIONWITHPARAMETERRESPONSE;
      }
   }

   @Manage(Names.SAMPLEACTIONWITHPARAMETERSNAME)
   private void manageSampleActionWithParameters(String parameter1, String parameter2, String parameter3)
   {
      reference = null;
      if ((Names.SAMPLEACTIONWITHPARAMETERSPARAMETER1.equals(parameter1))
          && (Names.SAMPLEACTIONWITHPARAMETERSPARAMETER2.equals(parameter2))
          && (Names.SAMPLEACTIONWITHPARAMETERSPARAMETER3.equals(parameter3)))
      {
         reference = Names.SAMPLEACTIONWITHPARAMETERSRESPONSE;
      }
   }

   @Manage(value=Names.SAMPLEACTIONNAME, code=Names.KEYCODE1)
   private void manageSampleActionWithKeyCode1()
   {
      reference = Names.SAMPLEACTIONRESPONSEWITHKEYCODE1;
   }

   @Manage(value=Names.SAMPLEACTIONNAME, code=Names.KEYCODE2)
   private void manageSampleActionWithKeyCode2()
   {
      reference = Names.SAMPLEACTIONRESPONSEWITHKEYCODE2;
   }
}
