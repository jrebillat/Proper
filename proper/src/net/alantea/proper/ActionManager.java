package net.alantea.proper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
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
 *  Manage("action key string").
 * 
 */
public class ActionManager
{
   /** The Constant ALL_KEYCODES. Use to specify that this may be applied to all managed containers for this method. */
   public static final String ALL_KEYCODES = "__ALL_KEYCODES__";

   /** The Constant DEFAULT_KEYCODE. Use to specify that this may be applied only to default container. */
   public static final String DEFAULT_KEYCODE = "";
   
   /** The Constant ACTIONMAP_NAME. */
   private static final String ACTIONMAP_NAME = "__ActionManager__actionMap";
   
   /** The named managers. */
   private static Map<String, ActionManager> namedManagers = new HashMap<>();
   
   /** The grown fields. */
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
    *
    * @param name the name
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
         associateActions(this, toAssociate);
      }
   }

   /**
    * Gets the action map.
    *
    * @param object the object
    * @return the action map
    */
   private static Map<String, List<ObjectMethod>> getActionMap(Object object)
   {
      if (object instanceof PropertyContainer)
      {
         return ((ActionManager)object).actionMap;
      }
      return getGrownField(object, ACTIONMAP_NAME);
   }

   /**
    * Gets the grown field.
    *
    * @param <T> the generic type
    * @param object the object
    * @param name the name
    * @return the grown field
    */
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
    *
    * @param managerName the manager name
    * @param element the element
    */
   public static void associateNamedManager(String managerName, Object element)
   {
      associateNamedManager(managerName, "", element);
   }
   
   /**
    * Associate something with the named action manager.
    *
    * @param managerName the manager name
    * @param keyCode the key code
    * @param element the element
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
    *
    * @param container the container
    * @param element the element
    */
   public static void associateActions(Object container, Object element)
   {
      associateActions(container, "", element);
   }
   
   /**
    * Associate something with the action manager.
    *
    * @param element the element
    */
   public void associateActions(Object element)
   {
      associateActions(this, element);
   }

   /**
    * Associate something with the action manager.
    *
    * @param container the container
    * @param keyCode the key code
    * @param element the element
    */
   public static void associateActions(Object container, String keyCode, Object element)
   {
      if (element != null)
      {
         associateActionMethods(container, element, element.getClass(), keyCode);
      }
   }
   
   /**
    * Associate something with the action manager.
    *
    * @param keyCode the key code
    * @param element the element
    */
   public void associateActions(String keyCode, Object element)
   {
      associateActions(this, keyCode, element);
   }
   
   /**
    * Forget.
    *
    * @param object the object
    */
   public void forget(Object object)
   {
      forget(this, object);
   }
   
   /**
    * Forget.
    *
    * @param container the container
    * @param object the object
    */
   public static void forget(Object container, Object object)
   {
      if (object != null)
      {
         grownFields.remove(object);
         Collection<String> collection = Collections.unmodifiableCollection(namedManagers.keySet());
         for (String key : collection)
         {
            if (namedManagers.get(key).equals(object))
            {
               namedManagers.remove(key);
            }
         }
      }
   }

   /**
    * Associate action methods.
    *
    * @param container the container
    * @param element the element
    * @param cl the class type
    * @param keyCode the key code
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
                     method.setAccessible(true);
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
    * @param parameters the parameters
    */
   public final void execute(String actionKey, Object... parameters)
   {
      execute(this, actionKey, parameters);
   }
   
   /**
    * Execute.
    *
    * @param container the container
    * @param actionKey the action key
    * @param parameters the parameters
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
               if (  method.getParameterCount() == parameters.length)
               {
                  exe.getMethod().invoke(exe.getObject(), parameters);
               }
               else if (( method.getParameterCount() == parameters.length + 1)
                     && (method.getParameterTypes()[0].equals(Object.class)))
               {
                  Object[] params = new Object[parameters.length + 1];
                  params[0] = container;
                  for (int i = 0; i < parameters.length; i++)
                  {
                     params[i+1] = parameters[i];
                  }
                  exe.getMethod().invoke(exe.getObject(), params);
               }
               else if (( method.getParameterCount() == parameters.length + 1)
                     && (method.getParameterTypes()[0].equals(String.class)))
               {
                  Object[] params = new Object[parameters.length + 1];
                  params[0] = actionKey;
                  for (int i = 0; i < parameters.length; i++)
                  {
                     params[i+1] = parameters[i];
                  }
                  exe.getMethod().invoke(exe.getObject(), params);
               }
               else if (( method.getParameterCount() == parameters.length + 2)
                     && (method.getParameterTypes()[0].equals(Object.class))
                     && (method.getParameterTypes()[1].equals(String.class)))
               {
                  Object[] params = new Object[parameters.length + 2];
                  params[0] = container;
                  params[1] = actionKey;
                  for (int i = 0; i < parameters.length; i++)
                  {
                     params[i+2] = parameters[i];
                  }
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
