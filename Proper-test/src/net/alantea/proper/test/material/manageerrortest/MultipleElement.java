package net.alantea.proper.test.material.manageerrortest;

import net.alantea.proper.EventMessage;
import net.alantea.proper.ManageError;

public class MultipleElement
{
      private String keyName;

    private String message;
    
    public String getSimpleKey()
    {
       return keyName;
    }
    
      public String getMessage()
      {
         return message;
      }
      
      @ManageError(EventMessage.Level.INFORMATION)
      @ManageError(EventMessage.Level.WARNING)
      private void manageInformation(EventMessage eventMessage)
      {
         keyName = eventMessage.getlevel().name();
         message = eventMessage.getMessage();
      }
      
      @ManageError(EventMessage.Level.ERROR)
      @ManageError(EventMessage.Level.FATALERROR)
      private void manageFatalerror(EventMessage eventMessage)
      {
         keyName = eventMessage.getlevel().name();
         message = eventMessage.getMessage();
      }
}
