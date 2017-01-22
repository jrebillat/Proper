package net.alantea.proper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;

/**
 * The Class PropertyContainer.
 * A property container instance is the root part of some elements associated with it and working with a common set of properties.
 * By construction, every property container have at start one property, keyed 'PROP_THIS', with the property container as value.
 * This way, each element may refer directly to the container (see below for the annotations).
 * 
 *  Both the property container and the elements must have annotations to ensure a correct association.
 * 
 * The property container or element class should be extended with @Require annotations :
 * Require(key="key string to define", type= primitive class or any class type provided it has a constructor without arguments,
 * action="action key"). The action parameter is an option.
 * Entries for required properties will be created and added to the container at instance creation and initialized to default values
 *  : 0 value for primitive types, false for booleans, "" for String and null for other object types.
 * 
 * Methods with  zero or one parameter in property container or element class may be extended with one or more @Manage annotations :
 * Manage("action key string").
 * The action key may correspond to an 'action' parameter value in one or more Require definition, in the property container,
 * another associated element or even the same class. Each time the value of the property change, the annotated methods with the
 * action key in any element will be called. If a parameter is needed, the value passed to the method is the new property value.
 * But caution with it : there is no class compatibility verification here.
 * Methods with @Manage annotations in elements may also be called directly using the property container 'execute('action key', value).
 * 
 * Fields in elements may also have a single @Associate annotation : Associate("property key"). If the property already exists in
 * the container, then the value of the field will be set to the field value. If not, then a new property with the field value is 
 * created. The field value will be set each time the property value change to reflect the new value. Changing the value of the field
 * will not change the property value.
 * 
 * Fields in elements may also have a single @Initialize annotation : Initialize("property key"). If the property already exists in
 * the container, then the value of the field will be set at association to the current property value at that time. If not, then a new
 * property with the field value is created. Changes will not be reflected afterwards.
 */
public abstract class PropertyContainer extends ActionManager
{
   
   /** The Constant PROP_THIS. Use to represent the container itself in the property list. */
   public static final String PROP_THIS = "PropertyContainerThis";

   /** The Constant MESS_ASSOCIATE. */
   public static final String MESS_ASSOCIATE = "PropertyContainerAssociate";
   
   /** The Constant MESS_SETVALUE. */
   public static final String MESS_SETVALUE = "PropertyContainerSetValue";
      
   /** The Constant FIELDSMAP_NAME. */
   private static final String FIELDSMAP_NAME = "__PropertyContainer__fieldsMap";
   
   /** The Constant ACTIONSMAP_NAME. */
   private static final String ACTIONSMAP_NAME = "__PropertyContainer__actionsMap";
   
   /** The standard component map. */
   private static Map<Object, PropertyContainer> reference = new HashMap<>();
   
   /** The associated fields map. */
   private Map<String, List<ObjectField>> fieldsMap;
   
   /** The associated actions map. */
   private Map<String, List<String>> propertyActionsMap;
   
   /**
    * Instantiates a new container.
    */
   public PropertyContainer()
   {
      this((String)null);
   }
   
   /**
    * Instantiates a new container.
    *
    * @param name the name for container
    */
   public PropertyContainer(String name)
   {
      super(name);
      setPropertyValue(PROP_THIS, this);
      reference.put(this, this);
      fieldsMap = new LinkedHashMap<>();
      propertyActionsMap = new LinkedHashMap<>();

    //  createProperties(this);
      createRequiredProperties(this, "");
      associateActionMethods(this, this, this.getClass(), "");
      associateFields(this, this.getClass(), "");
      initializeFields(this, this.getClass(), "");
      registerItself(this, "");
      manageRegistry(this, "");
      manageListeners(this, "");
      execute(this, MESS_ASSOCIATE, this, this);
   }
   
   /**
    * Inits the.
    */
   protected void init()
   {
      
   }
   
   /**
    * Instantiates a new property container and associate a list of elements to it.
    *
    * @param toBeAssociated the elements to be associated. They should use the annotations to be fully associated.
    */
   public PropertyContainer(Object... toBeAssociated)
   {
      this();
      for (Object toAssociate : toBeAssociated)
      {
         associate(toAssociate);
      }
   }
   
   /**
    * Checks for property existence.
    *
    * @param key the property key
    * @return true, if successful
    */
   public abstract boolean hasProperty(String key);
   
   /**
    * Checks for existence of a property and check for coherent class assignment.
    *
    * @param key the key
    * @param referenceClass the reference class
    * @return true, if successful
    */
   public abstract boolean hasProperty(String key, Class<?> referenceClass);
   
   /**
    * Gets the property value.
    *
    * @param <T> the generic type
    * @param key the key
    * @return the property value
    */
   public abstract <T> T getPropertyValue(String key);

   /**
    * Sets the property value.
    *
    * @param key the key
    * @param value the value
    */
  // public abstract void setProperty(String key, Object value);

   /**
    * Sets the property value.
    *
    * @param key the key
    * @param value the value
    */
   public abstract void setPropertyValue(String key, Object value);

   /**
    * Sets the property.
    *
    * @param key the key
    * @param value the value
    */
   public abstract void setProperty(String key, ObjectProperty<Object> value);

   /**
    * Sets the property class.
    *
    * @param key the key
    * @param value the class value
    */
   public abstract void setPropertyClass(String key, Class<?> value);
   
   /**
    * Adds a property listener for the given property key.
    *
    * @param key the property key
    * @param listener the change listener
    */
   public abstract void addPropertyListener(String key, ChangeListener<Object> listener);
   
   /**
    * Removes a property listener.
    *
    * @param key the property key to be desassociated.
    * @param listener the listener
    */
   public abstract void removePropertyListener(String key, ChangeListener<Object> listener);
   
   /**
    * Bind property.
    *
    * @param localKey the local key
    * @param source the source
    * @param originKey the origin key
    */
   public abstract void bindProperty(String localKey, PropertyContainer source, String originKey);
   
   /**
    * Do association.
    *
    * @param key the key
    * @param element the element
    */
   public void doAssociation(String key, Object element)
   {
      // nothing
   }
   

   /**
    * Gets the actions map.
    *
    * @param object the object
    * @return the actions map
    */
   private static Map<String, List<String>> getActionsMap(Object object)
   {
      if (object instanceof PropertyContainer)
      {
         return ((PropertyContainer)object).propertyActionsMap;
      }
      return getGrownField(object, ACTIONSMAP_NAME);
   }
   
   /**
    * Gets the fields map.
    *
    * @param object the object
    * @return the fields map
    */
   private static Map<String, List<ObjectField>> getFieldsMap(Object object)
   {
      if (object instanceof PropertyContainer)
      {
         return ((PropertyContainer)object).fieldsMap;
      }
      return getGrownField(object, FIELDSMAP_NAME);
   }
   
   /**
    * Adds a property listener message for the given property key.
    *
    * @param container the container
    * @param key the property key
    * @param message the message key
    */
   public static final void addPropertyMessage(Object container, String key, String message)
   {
      if (!"".equals(message))
      {
         Map<String, List<String>> actions = getActionsMap(container);
         List<String> propActions = actions.get(key);
         if (propActions == null)
         {
            propActions = new ArrayList<>();
            actions.put(key, propActions);
         }
         if (! propActions.contains(message))
         {
            propActions.add(message);
         }
      }
   }
   
   /**
    * Associate something with the property container.
    * It should have some annotations.
    * The available annotations are :
    *
    * @param element the element
    *  Require(key="key string to define",
    *          type= primitive class or any class type provided it has a constructor without arguments,
    *          action="message identifier")
    * Note : the 'action' parameter is an option. If present, it means that the action will be executed on property change.
    * Entries for required properties will be created in property container at association if needed and initialized to default values.
    */
   public final void associate(Object element)
   {
      associate("", element);
   }
   
   /**
    * Associate something with the property container.
    * It should have some annotations.
    * The available annotations are :
    *
    * @param keycode the keycode
    * @param element the element
    *  Require(key="key string to define",
    *          type= primitive class or any class type provided it has a constructor without arguments,
    *          action="message identifier")
    * Note : the 'action' parameter is an option. If present, it means that the action will be executed on property change.
    * Entries for required properties will be created in property container at association if needed and initialized to default values.
    */
   public final void associate(String keycode, Object element)
   {
      associateElement(keycode, element);
      doAssociation(keycode, element);
   }

   /**
    * Associate element.
    *
    * @param keycode the keycode
    * @param element the element
    */
   private final void associateElement(String keycode, Object element)
   {
      if (element == null)
      {
         return;
      }
      if (("".equals(keycode)) || (ALL_KEYCODES.equals(keycode)))
      {
         reference.put(element, this);
      }
      createRequiredProperties(element, keycode);
      associateActionMethods(this, element, element.getClass(), keycode);
      associateFields(element, element.getClass(), keycode);
      initializeFields(element, element.getClass(), keycode);
      registerItself(element, keycode);
      manageRegistry(element, keycode);
      manageListeners(element, keycode);
      execute(this, MESS_ASSOCIATE, this, element);
   }
   
   /* (non-Javadoc)
    * @see net.alantea.proper.ActionManager#forget(java.lang.Object)
    */
   public void forget(Object object)
   {
      if (object != null)
      {
         reference.remove(object);
         Collection<Object> collection = Collections.unmodifiableCollection(reference.keySet());
         for (Object key : collection)
         {
            if (reference.get(key).equals(object))
            {
               reference.remove(key);
            }
            if (reference.equals(object))
            {
               reference.remove(key);
            }
         }
      }
   }

   /**
    * Creates the required properties for the element.
    * The properties found here are those from the Require type.
    *
    * @param element the element
    * @param keyCode the key code
    */
   private void createRequiredProperties(Object element, String keyCode)
   {
      createRequiredProperties(element, element.getClass(), keyCode);
   }

   /**
    * Creates the required properties for the element.
    * The properties found here are those from the Require type.
    *
    * @param element the element
    * @param cl the cl
    * @param keyCode the key code
    */
   private void createRequiredProperties(Object element, Class<?> cl, String keyCode)
   {
      if ((cl.isAnnotationPresent(Requires.class)) || (cl.isAnnotationPresent(Require.class)))
      {
         Require[] annotations = cl.getAnnotationsByType(Require.class);
         for (Require annotation : annotations)
         {
            if (annotation.code().equals(keyCode))
            {
               createProperty(annotation);
               if (!"".equals(annotation.action()))
               {
                  List<String> propActions = propertyActionsMap.get(annotation.key());
                  if (propActions == null)
                  {
                     propActions = new ArrayList<>();
                     propertyActionsMap.put(annotation.key(), propActions);
                  }
                  if (! propActions.contains(annotation.action()))
                  {
                     propActions.add(annotation.action());
                  }
               }
            }
            // TODO
            if (annotation.importFrom().equals(keyCode))
            {
               if (reference.get(element) != null)
               {
                  PropertyContainer refContainer = reference.get(element);
                  if (annotation.bound())
                  {
                     bindProperty(annotation.key(), refContainer, annotation.key());
                  }
                  else
                  {
                     Object fromValue = getPropertyValue(annotation.key());
                     refContainer.setPropertyValue(annotation.key(), fromValue);
                  }
               }
            }
         }
      }
      if (!cl.equals(Object.class))
      {
         createRequiredProperties(element, cl.getSuperclass(), keyCode);
      }
   }

   /**
    * Registers an element with a property name in container.
    * The properties found here are those from the Register type.
    *
    * @param element the element
    * @param keyCode the key code
    */
   private void registerItself(Object element, String keyCode)
   {
      registerItself(element, element.getClass(), keyCode);
   }

   /**
    * Registers an element with a property name in container.
    * The properties found here are those from the Register type.
    *
    * @param element the element
    * @param cl the cl
    * @param keyCode the key code
    */
   private void registerItself(Object element, Class<?> cl, String keyCode)
   {
      if ((cl.isAnnotationPresent(Registers.class)) || (cl.isAnnotationPresent(Register.class)))
      {
         Register[] annotations = cl.getAnnotationsByType(Register.class);
         for (Register annotation : annotations)
         {
            if ((annotation.code().equals(keyCode)) || (ALL_KEYCODES.equals(keyCode)))
            {
               setPropertyValue(annotation.value(), element);
            }
         }
      }
      if (!cl.equals(Object.class))
      {
         registerItself(element, cl.getSuperclass(), keyCode);
      }
   }

   /**
    * Call a method in element to finalize the register process in a container with a key code.
    * The may do anything, but it should call some PropertyContainer methods to ass properties or so...
    *
    * @param element the element
    * @param keyCode the key code
    */
   private void manageRegistry(Object element, String keyCode)
   {
      manageRegistry(element, element.getClass(), keyCode);
   }

   /**
    * Call a method in element to finalize the register process in a container with a key code.
    * The may do anything, but it should call some PropertyContainer methods to ass properties or so...
    *
    * @param element the element
    * @param cl the cl
    * @param keyCode the key code
    */
   private void manageRegistry(Object element, Class<?> cl, String keyCode)
   {
      for (Method method : cl.getDeclaredMethods())
      {
         if (((method.isAnnotationPresent(ManageRegistry.class))) || ((method.isAnnotationPresent(ManageRegistries.class))))
         {
            ManageRegistry[] annotations = method.getAnnotationsByType(ManageRegistry.class);
            for (ManageRegistry annotation : annotations)
            {
               if ((annotation.value().equals(keyCode)) || (ALL_KEYCODES.equals(keyCode)))
               {
                  if (method.getParameterCount() == 2)
                  {
                     Class<?>[] parmTypes = method.getParameterTypes();
                     if ((parmTypes[0].isAssignableFrom(String.class)) && (parmTypes[1].isAssignableFrom(PropertyContainer.class)))
                     {
                        method.setAccessible(true);
                        try
                        {
                           method.invoke(element, keyCode, this);
                        }
                        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
                        {
                           EventMessage.sendErrorMessage(element, EventMessage.Level.WARNING, "Could not invoke " + annotation.value());
                        }
                     }
                  }
               }
            }
         }
      }
      if (!cl.equals(Object.class))
      {
         manageRegistry(element, cl.getSuperclass(), keyCode);
      }
   }
   
   /**
    * Manage listeners.
    *
    * @param element the element
    * @param keyCode the key code
    */
   private void manageListeners(Object element, String keyCode)
   {
      manageListeners(element, element.getClass(), keyCode);
   }

   /**
    * Manage listeners.
    *
    * @param element the element
    * @param cl the cl
    * @param keyCode the key code
    */
   private void manageListeners(Object element, Class<?> cl, String keyCode)
   {
      for (Method method : cl.getDeclaredMethods())
      {
         if ((method.isAnnotationPresent(Listen.class)) || (method.isAnnotationPresent(Listens.class)))
         {
            Listen[] annotations = method.getAnnotationsByType(Listen.class);
            for (Listen annotation : annotations)
            {
               if ((annotation.code().equals(keyCode)) || (ALL_KEYCODES.equals(keyCode)))
               {
                  if (method.getParameterCount() == 2)
                  {
                    // Class<?>[] parmTypes = method.getParameterTypes();
                    // if (parmTypes[0].isAssignableFrom(PropertyContainer.class))
                     {
                        method.setAccessible(true);
                        addPropertyListener(annotation.value(), (v, o, n) -> 
                        {
                           try
                           {
                              method.invoke(element, this, n);
                           }
                           catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
                           {
                              EventMessage.sendErrorMessage(element, EventMessage.Level.WARNING, "Could not listen (2) to " + annotation.value());
                           }
                        });
                     }
                  }
                  else if (method.getParameterCount() == 1)
                  {
                         method.setAccessible(true);
                         addPropertyListener(annotation.value(), (v, o, n) -> 
                         {
                            try
                            {
                               method.invoke(element, n);
                            }
                            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
                            {
                               EventMessage.sendErrorMessage(element, EventMessage.Level.WARNING, "Could not listen (1) to " + annotation.value());
                            }
                         });
                   }
                  else if (method.getParameterCount() == 0)
                  {
                         method.setAccessible(true);
                         addPropertyListener(annotation.value(), (v, o, n) -> 
                         {
                            try
                            {
                               method.invoke(element);
                            }
                            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
                            {
                               EventMessage.sendErrorMessage(element, EventMessage.Level.WARNING, "Could not listen (0) to " + annotation.value());
                            }
                         });
                   }
               }
            }
         }
      }
      if (!cl.equals(Object.class))
      {
         manageRegistry(element, cl.getSuperclass(), keyCode);
      }
   }
   
   /**
    * Associate fields.
    *
    * @param element the element
    * @param cl the class
    * @param keyCode the key code
    */
   protected void associateFields(Object element, Class<?> cl, String keyCode)
   {
      for (Field field : cl.getDeclaredFields())
      {
         if (field.isAnnotationPresent(Associate.class))
         {
            field.setAccessible(true);
            Associate associates = field.getAnnotation(Associate.class);
            if ((associates.code().equals(keyCode)) || (ALL_KEYCODES.equals(keyCode)))
            {
               if ((!hasProperty(associates.value())) || (getPropertyValue(associates.value()) == null))
               {
                  try
                  {
                     setPropertyValue(associates.value(), field.get(element));
                  }
                  catch (IllegalArgumentException | IllegalAccessException e)
                  {
                     EventMessage.sendErrorMessage(element, EventMessage.Level.WARNING, "Could not associate field " + associates.value());
                  }

               }
               else
               {
                  try
                  {
                     field.set(element, getPropertyValue(associates.value()));
                  }
                  catch (IllegalArgumentException | IllegalAccessException e)
                  {
                     EventMessage.sendErrorMessage(element, EventMessage.Level.WARNING, "Could not get to associate field " + associates.value());
                  }
               }

               Map<String, List<ObjectField>> fields = getFieldsMap(this);
               if (fields != null)
               {
                  List<ObjectField> propFields = fields.get(associates.value());
                  if (propFields == null)
                  {
                     propFields = new ArrayList<>();
                     fields.put(associates.value(), propFields);
                  }
                  propFields.add(new ObjectField(element, field));
               }
            }
         }
      }
      if (!Object.class.equals(cl))
      {
         associateFields(element, cl.getSuperclass(), keyCode);
      }
   }

   /**
    * Initialize fields.
    *
    * @param element the element
    * @param cl the class
    * @param keyCode the key code
    */
   private void initializeFields(Object element, Class<?> cl, String keyCode)
   {
      for (Field field : cl.getDeclaredFields())
      {
         if (field.isAnnotationPresent(Initialize.class))
         {
            field.setAccessible(true);
            Initialize initialize = field.getAnnotation(Initialize.class);
            if ((initialize.code().equals(keyCode)) || (ALL_KEYCODES.equals(keyCode)))
            {
               if (!hasProperty(initialize.value()))
               {
                  try
                  {
                     setPropertyValue(initialize.value(), field.get(element));
                  }
                  catch (IllegalArgumentException | IllegalAccessException e)
                  {
                     EventMessage.sendErrorMessage(element, EventMessage.Level.WARNING, "Could not initialize " + initialize.value());
                  }

               }
               else
               {
                  try
                  {
                     field.set(element, getPropertyValue(initialize.value()));
                  }
                  catch (IllegalArgumentException | IllegalAccessException e)
                  {
                     EventMessage.sendErrorMessage(element, EventMessage.Level.WARNING, "Could get to initialize " + initialize.value());
                  }
               }
            }
         }
      }
      if (!Object.class.equals(cl))
      {
         initializeFields(element, cl.getSuperclass(), keyCode);
      }
   }

   /**
    * Import property fields.
    *
    * @param element the element
    * @param prefix the prefix
    */
   public void importFields(Object element, String prefix)
   {
      importFields(element, element.getClass(), prefix);
   }

   /**
    * Associate fields.
    *
    * @param element the element
    * @param cl the class
    * @param prefix the prefix
    */
   @SuppressWarnings("unchecked")
   protected void importFields(Object element, Class<?> cl, String prefix)
   {
      if (!Object.class.equals(cl))
      {
         importFields(element, cl.getSuperclass(), prefix);
      }
      
      for (Field field : cl.getDeclaredFields())
      {
         if (ObjectProperty.class.isAssignableFrom((Class<?>)field.getType()))
         {
            field.setAccessible(true);
            try
            {
               System.out.println("Importing " + ((prefix == null) ? "" : prefix) + field.getName() + "field");
               setProperty(((prefix == null) ? "" : prefix) + field.getName(), (ObjectProperty<Object>) field.get(element));
            }
            catch (IllegalArgumentException | IllegalAccessException e)
            {
               EventMessage.sendErrorMessage(element, EventMessage.Level.WARNING, "Could import field " + field.getName());
            }
         }
      }
   }
   
   /**
    * Creates the property.
    *
    * @param annotation the annotation
    */
   private void createProperty(Require annotation)
   {
      createProperty(annotation.key(), annotation.type(), annotation.action(), annotation.associate());
   }
   
   /**
    * Creates the property.
    *
    * @param key the key
    * @param type the type
    * @param action the action
    * @param associate to associate with
    */
   public void createProperty(String key, Class<?> type, String action, boolean associate)
   {
      if (!hasProperty(key, type))
      {
         Object value = getDefaultValueForClass(type);
         setPropertyClass(key, type);
         setPropertyValue(key, value);
         
         if (associate)
         {
            associate(value);
         }

       addPropertyListener(key, (v, o, n) ->
       {
          List<ObjectField> propFields = getFieldsMap(this).get(key);
          if (propFields != null)
          {
             for(ObjectField field : propFields)
             {
                if (n != null)
                {
                   try
                   {
                      field.getField().set(field.getObject(), n);
                   }
                   catch (IllegalArgumentException | IllegalAccessException e)
                   {
                      EventMessage.sendErrorMessage(this, EventMessage.Level.WARNING, "Could not send update for property " + key + " to " + action);
                   }
                }
             }
          }
          
          List<String> propActions = getActionsMap(this).get(key);
          if (propActions != null)
          {
             for(String propAction : propActions)
             {
                execute(this, propAction, n);
             }
          }
       });

       if ((!"".equals(action)) && (action != null))
       {
          List<String> propActions = propertyActionsMap.get(key);
          if (propActions == null)
          {
             propActions = new ArrayList<>();
             propertyActionsMap.put(key, propActions);
          }
          if (! propActions.contains(action))
          {
             propActions.add(action);
          }
       }
      }
   }
   
   /**
    * Gets the default value for class.
    *
    * @param cl the cl
    * @return the default value for class
    */
   protected static Object getDefaultValueForClass(Class<?> cl)
   {
      Object value = null;

      try
      {
         Constructor<?>[] cons = cl.getConstructors();
         // Search for constructor without parameters
         for (Constructor<?> con : cons)
         {
            if (con.getParameterCount() == 0)
            {
               value = cl.newInstance();
            }
         }
         
         // No simple initialization, try obvious primitives classes
         // else : we will return a null value.
         if (value == null)
         {
            if (Boolean.class.equals(cl))
            {
               value = new Boolean(false);
            }
            else if (Integer.class.equals(cl))
            {
               value = new Integer(0);
            }
            else if (Long.class.equals(cl))
            {
               value = new Long(0);
            }
            else if (Float.class.equals(cl))
            {
               value = new Float(0.0);
            }
            else if (Double.class.equals(cl))
            {
               value = new Double(0.0);
            }
            else if (String.class.equals(cl))
            {
               value = "";
            }
            else if (Byte.class.equals(cl))
            {
               value = new Byte((byte) 0);
            }
            else if (Short.class.equals(cl))
            {
               value = new Short((short) 0);
            }
         }
      }
      catch (InstantiationException | IllegalAccessException e)
      {
         EventMessage.sendErrorMessage(cl, EventMessage.Level.WARNING, "Could not find default value for " + cl.getName());
      }
      return value;
   }
}
