package net.alantea.proper.test.material.manageerrortest;

import net.alantea.proper.EventMessage;
import net.alantea.proper.ManageError;

public class SimpleElement
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
      private void manageInformation(EventMessage eventMessage)
      {
         keyName = EventMessage.Level.INFORMATION.name();
         message = eventMessage.getMessage();
      }
      
      @ManageError(EventMessage.Level.WARNING)
      private void manageWarning(EventMessage eventMessage)
      {
         keyName = EventMessage.Level.WARNING.name();
         message = eventMessage.getMessage();
      }
      
      @ManageError(EventMessage.Level.ERROR)
      private void manageError(EventMessage eventMessage)
      {
         keyName = EventMessage.Level.ERROR.name();
         message = eventMessage.getMessage();
      }
      
      @ManageError(EventMessage.Level.FATALERROR)
      private void manageFatalerror(EventMessage eventMessage)
      {
         keyName = EventMessage.Level.FATALERROR.name();
         message = eventMessage.getMessage();
      }
}
