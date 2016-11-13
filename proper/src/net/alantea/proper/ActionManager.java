package net.alantea.proper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
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
   
   private static final String ACTIONMAP_NAME = "__ActionManager__actionMap";
   
   private static Map<String, ActionManager> namedManagers = new HashMap<>();
   
   private static Map<Object, Map<String, Object>> grownFields = new HashMap<>();
   
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

      associateActionMethods(this, this, getClass(), "");
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
         associate(this, toAssociate);
      }
   }

   private static Map<String, List<ObjectMethod>> getActionMap(Object object)
   {
      if (object instanceof PropertyContainer)
      {
         return ((ActionManager)object).actionMap;
      }
      return getGrownField(object, ACTIONMAP_NAME);
   }

   @SuppressWarnings("unchecked")
   protected static <T> Map<String, T> getGrownField(Object object, String name)
   {
      Map<String, Object> map = grownFields.get(object);
      if (map == null)
      {
         map = new HashMap<>();
         grownFields.put(object,  map);
      }

      Map<String, T> ret = (Map<String, T>) map.get(name);
      if (ret == null)
      {
         ret = new LinkedHashMap<>();
         map.put(name, ret);
      }
      return  ret;
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
         associateActionMethods(manager, element, element.getClass(), keyCode);
      }
   }
   
   /**
    * Associate something with the action manager.
    */
   public static void associate(Object container, Object element)
   {
      associate(container, "", element);
   }
   
   /**
    * Associate something with the action manager.
    */
   public void associate(Object element)
   {
      associate(this, element);
   }

   /**
    * Associate something with the action manager.
    */
   public static void associate(Object container, String keyCode, Object element)
   {
      if (element != null)
      {
         associateActionMethods(container, element, element.getClass(), keyCode);
      }
   }
   
   /**
    * Associate something with the action manager.
    */
   public void associate(String keyCode, Object element)
   {
      associate(this, keyCode, element);
   }

   /**
    * Associate action methods.
    *
    * @param element the element
    * @param cl the class type
    */
   protected static void associateActionMethods(Object container, Object element, Class<?> cl, String keyCode)
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
                     List<ObjectMethod> actionList = getActionMap(container).get(action);
                     if (actionList == null)
                     {
                        actionList = new ArrayList<>();
                        getActionMap(container).put(action, actionList);
                     }
                     actionList.add(new ObjectMethod(element, method));
                  }
               }
            }
         }
      }
      cl = cl.getSuperclass();
      if (!Object.class.equals(cl))
      {
         associateActionMethods(container, element, cl, keyCode);
      }
   }
   
   /**
    * Execute.
    *
    * @param actionKey the action key
    * @param actionContent the action content
    * @param useParameter the use parameter flag
    */
   public final void execute(String actionKey, Object... parameters)
   {
      execute(this, actionKey, parameters);
   }
   
   /**
    * Execute.
    *
    * @param actionKey the action key
    * @param actionContent the action content
    * @param useParameter the use parameter flag
    */
   public static final void execute(Object container, String actionKey, Object... parameters)
   {
      List<ObjectMethod> actionList = getActionMap(container).get(actionKey);
      if (actionList != null)
      {
         List<ObjectMethod> list = Collections.unmodifiableList(actionList);
         for (ObjectMethod exe : list)
         {
            try
            {
               Method method = exe.getMethod();
               if ( parameters.length == method.getParameterCount())
               {
                  exe.getMethod().invoke(exe.getObject(), parameters);
               }
               else if (method.getParameterCount() == 0)
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
