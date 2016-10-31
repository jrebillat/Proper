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
   private static Map<String, ActionManager> namedManagers = new HashMap<>();;
   /** The action map. */
   private Map<String, List<ObjectMethod>> actionMap;
   
   /**
    * Instantiates a new action manager.
    */
   public ActionManager()
   {
      super();
      actionMap = new LinkedHashMap<>();

      associateActionMethods(this, getClass());
   }
   
   /**
    * Instantiates a new named action manager.
    */
   public ActionManager(String name)
   {
      super();
      namedManagers.put(name, this);
      actionMap = new LinkedHashMap<>();

      associateActionMethods(this, getClass());
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
      return namedManagers.get(name);
   }
   
   /**
    * Associate something with the named action manager.
    */
   public static void associate(String managerName, Object element)
   {
      ActionManager manager = namedManagers.get(managerName);
      if ((element != null) && (manager != null))
      {
         manager.associateActionMethods(element, element.getClass());
      }
   }
   
   /**
    * Associate something with the action manager.
    */
   public void associate(Object element)
   {
      if (element != null)
      {
         associateActionMethods(element, element.getClass());
      }
   }

   /**
    * Associate action methods.
    *
    * @param element the element
    * @param cl the cl
    */
   protected void associateActionMethods(Object element, Class<?> cl)
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
               List<ObjectMethod> actionList = actionMap.get(manage.value());
               if (actionList == null)
               {
                  actionList = new ArrayList<>();
                  actionMap.put(manage.value(), actionList);
               }
               if (method.getParameterCount() < 2)
               {
                  actionList.add(new ObjectMethod(element, method, method.getParameterCount() == 1));
               } 
            }
         }
      }
      cl = cl.getSuperclass();
      if (!Object.class.equals(cl))
      {
         associateActionMethods(element, cl);
      }
   }
   
   /**
    * Execute, using only methods with no parameters.
    *
    * @param actionKey the action key
    */
   public final void execute(String actionKey)
   {
      execute(actionKey, null, false);
   }
   
   /**
    * Execute, using methods with zero or one parameter.
    *
    * @param actionKey the action key
    * @param actionContent the action content
    */
   public final void execute(String actionKey, Object actionContent)
   {
      execute(actionKey, actionContent, true);
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
