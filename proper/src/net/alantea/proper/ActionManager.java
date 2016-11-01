package net.alantea.proper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class ActionManager manages named action by calling methods in associated elements that 
 * manage the named action.
 * 
 * Methods with  zero or one parameter in action manager or element class may be extended with one or more @Manage annotations :
 * @Manage("action key string").
 * 
 */
public class ActionManager
{
   /** The Constant ALL_KEYCODES. Use to specify that this may be applied to all managed containers for this method. */
   public static final String ALL_KEYCODES = "__ALL_KEYCODES__";

   /** The Constant DEFAULT_KEYCODE. Use to specify that this may be applied only to default container. */
   public static final String DEFAULT_KEYCODE = "";
   
   private static Map<String, ActionManager> namedManagers = new HashMap<>();
   
   /** The action map. */
   private Map<String, List<ObjectMethod>> actionMap;
   
   /**
    * Instantiates a new action manager.
    */
   public ActionManager()
   {
      this((String)null);
   }
   
   /**
    * Instantiates a new named action manager.
    */
   public ActionManager(String name)
   {
      super();
      if (name != null)
      {
         namedManagers.put(name, this);
      }
      actionMap = new LinkedHashMap<>();

      associateActionMethods(this, getClass(), "");
   }
   
   /**
    * Instantiates a new property container and associate a list of elements to it.
    *
    * @param toBeAssociated the elements to be associated. They should use the annotations to be fully associated.
    */
   public ActionManager(Object... toBeAssociated)
   {
      this();
      for (Object toAssociate : toBeAssociated)
      {
         associate(toAssociate);
      }
   }
   
   /**
    * Gets the named manager.
    *
    * @param name the name
    * @return the manager
    */
   public static ActionManager getManager(String name)
   {
      return (name == null) ? null : namedManagers.get(name);
   }
   
   /**
    * Associate something with the named action manager.
    */
   public static void associateNamedManager(String managerName, Object element)
   {
      associateNamedManager(managerName, "", element);
   }
   
   /**
    * Associate something with the named action manager.
    */
   public static void associateNamedManager(String managerName, String keyCode, Object element)
   {
      if ((managerName == null) || (element == null))
      {
         return;
      }
      ActionManager manager = namedManagers.get(managerName);
      if (manager != null)
      {
         manager.associateActionMethods(element, element.getClass(), keyCode);
      }
   }
   
   /**
    * Associate something with the action manager.
    */
   public void associate(Object element)
   {
      associate("", element);
   }

   /**
    * Associate something with the action manager.
    */
   public void associate(String keyCode, Object element)
   {
      if (element != null)
      {
         associateActionMethods(element, element.getClass(), keyCode);
      }
   }

   /**
    * Associate action methods.
    *
    * @param element the element
    * @param cl the class type
    */
   protected void associateActionMethods(Object element, Class<?> cl, String keyCode)
   {
      if ((element == null) || (cl == null) || (!cl.isAssignableFrom(element.getClass())))
      {
         return;
      }
      
      for (Method method : cl.getDeclaredMethods())
      {
         if ((method.isAnnotationPresent(Manages.class)) || (method.isAnnotationPresent(Manage.class)))
         {
            method.setAccessible(true);
            Manage[] manages = method.getAnnotationsByType(Manage.class);
            for (Manage manage : manages)
            {
               if ((ALL_KEYCODES.equals(manage.code())) || (manage.code().equals(keyCode)))
               {
                  String action = manage.value();
                  if ((action != null) && (action.length() > 0))
                  {
                     List<ObjectMethod> actionList = actionMap.get(action);
                     if (actionList == null)
                     {
                        actionList = new ArrayList<>();
                        actionMap.put(action, actionList);
                     }
                     if (method.getParameterCount() < 2)
                     {
                        actionList.add(new ObjectMethod(element, method, method.getParameterCount() == 1));
                     }
                  }
               }
            }
         }
      }
      cl = cl.getSuperclass();
      if (!Object.class.equals(cl))
      {
         associateActionMethods(element, cl, keyCode);
      }
   }
   
   /**
    * Execute, using only methods with no parameters.
    *
    * @param actionKey the action key
    */
   public final void execute(String actionKey)
   {
      if ((actionKey != null) && (actionKey.length() > 0))
      {
         execute(actionKey, null, false);
      }
   }
   
   /**
    * Execute, using methods with zero or one parameter.
    *
    * @param actionKey the action key
    * @param actionContent the action content
    */
   public final void execute(String actionKey, Object actionContent)
   {
      if ((actionKey != null) && (actionKey.length() > 0))
      {
         execute(actionKey, actionContent, true);
      }
   }
   
   /**
    * Execute.
    *
    * @param actionKey the action key
    * @param actionContent the action content
    * @param useParameter the use parameter flag
    */
   private final void execute(String actionKey, Object actionContent, boolean useParameter)
   {
      List<ObjectMethod> actionList = actionMap.get(actionKey);
      if (actionList != null)
      {
         for (ObjectMethod exe : actionList)
         {
            try
            {
               if (exe.isNeedsParameters())
               {
                  if (useParameter)
                  {
                     exe.getMethod().invoke(exe.getObject(), actionContent);
                  }
               }
               else
               {
                  exe.getMethod().invoke(exe.getObject());
               }
            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
            {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
         }
      }
   }
}
