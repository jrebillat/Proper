package net.alantea.proper;

/**
 * The Class EventMessage.
 */
@SuppressWarnings("serial")
public class EventMessage extends Exception
{
   public static enum Level { INFORMATION, WARNING, ERROR, FATALERROR };
   
   /** The Constant ERROR_OBJECT. */
   static final String ERROR_OBJECT = "__Error_Object_Hook__";
   
   /** The source. */
   private Object source;
   
   /** The level. */
   private Level level;
   
   /**
    * Instantiates a new event message.
    *
    * @param source the source
    * @param level the level
    * @param message the message
    */
   public EventMessage(Object source, Level level, String message)
   {
      super(message);
      this.source = source;
      this.level = level;
   }

   /**
    * Instantiates a new event message.
    *
    * @param source the source
    * @param level the level
    * @param cause the cause
    */
   public EventMessage(Object source, Level level, Throwable cause)
   {
      super(cause);
      this.source = source;
      this.level = level;
   }

   /**
    * Instantiates a new event message.
    *
    * @param source the source
    * @param level the level
    * @param message the message
    * @param cause the cause
    */
   public EventMessage(Object source, Level level, String message, Throwable cause)
   {
      super(message, cause);
      this.source = source;
      this.level = level;
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
    * Gets the error level.
    *
    * @return the error level
    */
   public Level getlevel()
   {
      return level;
   }

   /**
    * Send error message.
    *
    * @param level the level
    * @param errorMessage the error message
    */
   public static void sendErrorMessage(String level, EventMessage errorMessage)
   {
      ActionManager.execute((Object)ERROR_OBJECT, level, errorMessage);
   }

   /**
    * Send error message.
    *
    * @param level the level
    * @param errorMessage the error message
    */
   public static void sendErrorMessage(Level level, String message)
   {
      ActionManager.execute((Object)ERROR_OBJECT, level.name(), new EventMessage(null, level, message));
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
      ActionManager.execute((Object)ERROR_OBJECT, level.name(), new EventMessage(source, level, message));
   }
}
