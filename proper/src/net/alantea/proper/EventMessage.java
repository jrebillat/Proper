package net.alantea.proper;

/**
 * The Class EventMessage.
 */
@SuppressWarnings("serial")
public class EventMessage extends Exception
{
   public static final String EVENTMESSAGEACTION = "EventMessageAction";

   /**
    * The error Level.
    */
   public static enum Level
   { 
      /** The information level. */
      INFORMATION, 
      /** The warning level. */
      WARNING, 
      /** The error level. */
      ERROR, 
      /** The fatal level. */
      FATAL
   };

   /** The level. */
   private Level errorLevel = Level.WARNING;
   
   /** The source. */
   private Object source;
   
   /**
    * Instantiates a new event message.
    *
    * @param errorLevel the error level
    * @param message the message
    */
   public EventMessage(Object source, Level errorLevel, String message)
   {
      super(message);
      this.errorLevel = errorLevel;
      this.source = source;
   }

   /**
    * Instantiates a new event message.
    *
    * @param errorLevel the error level
    * @param cause the cause
    */
   public EventMessage(Object source, Level errorLevel, Throwable cause)
   {
      super(cause);
      this.errorLevel = errorLevel;
      this.source = source;
   }

   /**
    * Instantiates a new event message.
    *
    * @param errorLevel the error level
    * @param message the message
    * @param cause the cause
    */
   public EventMessage(Object source, Level errorLevel, String message, Throwable cause)
   {
      super(message, cause);
      this.errorLevel = errorLevel;
      this.source = source;
   }
   
   /**
    * Instantiates a new event message.
    *
    * @param message the message
    */
   public EventMessage(Object source, String message)
   {
      super(message);
      this.source = source;
   }

   /**
    * Instantiates a new event message.
    *
    * @param cause the cause
    */
   public EventMessage(Object source, Throwable cause)
   {
      super(cause);
      this.source = source;
   }

   /**
    * Instantiates a new event message.
    *
    * @param message the message
    * @param cause the cause
    */
   public EventMessage(Object source, String message, Throwable cause)
   {
      super(message, cause);
      this.source = source;
   }

   /**
    * Gets the error level.
    *
    * @return the error level
    */
   public Level getErrorLevel()
   {
      return errorLevel;
   }
   
   
   /**
    * Gets the error source.
    *
    * @return the error source
    */
   public Object getSource()
   {
      return source;
   }

   /**
    * Send error message.
    *
    * @param errorMessage the error message
    */
   public static void sendErrorMessage(EventMessage errorMessage)
   {
      ActionManager.execute((Object)null, EVENTMESSAGEACTION, errorMessage);
   }

   /**
    * Send error message.
    *
    * @param source the source
    * @param level the level
    * @param message the message
    */
   public static void sendErrorMessage(Object source, Level level, String message)
   {
      ActionManager.execute((Object)null, EVENTMESSAGEACTION, new EventMessage(source, level, message));
   }
}
