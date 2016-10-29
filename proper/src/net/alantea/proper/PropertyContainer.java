package net.alantea.proper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
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
 * @Require(key="key string to define", type= primitive class or any class type provided it has a constructor without arguments,
 * action="action key"). The action parameter is an option.
 * Entries for required properties will be created and added to the container at instance creation and initialized to default values
 *  : 0 value for primitive types, false for booleans, "" for String and null for other object types.
 * 
 * Methods with  zero or one parameter in property container or element class may be extended with one or more @Manage annotations :
 * @Manage("action key string").
 * The action key may correspond to an 'action' parameter value in one or more @Require definition, in the property container,
 * another associated element or even the same class. Each time the value of the property change, the annotated methods with the
 * action key in any element will be called. If a parameter is needed, the value passed to the method is the new property value.
 * But caution with it : there is no class compatibility verification here.
 * Methods with @Manage annotations in elements may also be called directly using the property container 'execute('action key', value).
 * 
 * Fields in elements may also have a single @Associate annotation : @Associate("property key"). If the property already exists in
 * the container, then the value of the field will be set to the field value. If not, then a new property with the field value is 
 * created. The field value will be set each time the property value change to reflect the new value. Changing the value of the field
 * will not change the property value.
 * 
 * Fields in elements may also have a single @Initialize annotation : @Initialize("property key"). If the property already exists in
 * the container, then the value of the field will be set at association to the current property value at that time. If not, then a new
 * property with the field value is created. Changes will not be reflected afterwards.
 */
public class PropertyContainer
{
   
   /** The Constant PROP_THIS. Use to represent the container itself in the property list. */
   public static final String PROP_THIS = "PropertyContainerThis";
   
   /** The properties. */
   private Map<String, ObjectProperty<Object>> properties;
   
   /** The action map. */
   private Map<String, List<ObjectMethod>> actionMap;
   
   /** The associated fields map. */
   private Map<String, List<ObjectField>> fieldsMap;
   
   /** The associated actions map. */
   private Map<String, List<String>> propertyActionsMap;
   
   /**
    * Instantiates a new container.
    */
   public PropertyContainer()
   {
      super();
      properties = new LinkedHashMap<>();
      actionMap = new LinkedHashMap<>();
      fieldsMap = new LinkedHashMap<>();
      propertyActionsMap = new LinkedHashMap<>();

      setPropertyValue(PROP_THIS, this);
      createProperties();
      associateActionMethods(this, getClass());
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
    * Adds a property listener for the given property key.
    *
    * @param key the property key
    * @param listener the change listener
    */
   public final void addPropertyListener(String key, ChangeListener<Object> listener)
   {
      if (listener != null)
      {
         if (!properties.containsKey(key))
         {
            setPropertyValue(key, null);
         }
         properties.get(key).addListener(listener);
      }
   }
   
   /**
    * Removes a property listener.
    *
    * @param key the property key to be desassociated.
    * @param listener the listener
    */
   public final void removePropertyListener(String key, ChangeListener<Object> listener)
   {
      if ((listener != null) && (key != null))
      {
         ObjectProperty<Object> entry = properties.get(key);
         if (entry != null)
         {
            entry.removeListener(listener);
         }
      }
   }
   
   /**
    * Checks for property existence.
    *
    * @param key the property key
    * @return true, if successful
    */
   public final boolean hasProperty(String key)
   {
      return properties.containsKey(key);
   }
   
   /**
    * Checks for existence of a property and check for coherent class assignment.
    *
    * @param key the key
    * @param referenceClass the reference class
    * @return true, if successful
    */
   public final boolean hasProperty(String key, Class<?> referenceClass)
   {
      if ((referenceClass != null) && (key != null))
      {
         if (properties.containsKey(key))
         {
            Object object = properties.get(key).get();
            if (object == null)
            {
               return true;
            }
            return referenceClass.isAssignableFrom(object.getClass());
         }
      }
      return false;
   }

   /**
    * Gets the property.
    *
    * @param <T> the generic type
    * @param key the key
    * @return the property
    */
   @SuppressWarnings("unchecked")
   public final <T> ObjectProperty<T> getProperty(String key)
   {
      return (ObjectProperty<T>) properties.get(key);
   }
   
   /**
    * Gets the property value.
    *
    * @param <T> the generic type
    * @param key the key
    * @return the property value
    */
   @SuppressWarnings("unchecked")
   public final <T> T getPropertyValue(String key)
   {
      if (properties.containsKey(key))
      {
         Object value = properties.get(key).get();
         if (value != null)
         {
            return (T) value;
         }
      }
      return null;
   }

   /**
    * Sets the property value.
    *
    * @param key the key
    * @param value the value
    */
   public final void setPropertyValue(String key, Object value)
   {
      if (!properties.containsKey(key))
      {
         properties.put(key, new SimpleObjectProperty<Object>(value));
      }
      properties.get(key).set(value);
   }
   
   /**
    * Associate something with the property container.
    * It should have some annotations.
    * The available annotations are :
    *
    * @Require(key="key string to define",
    *          type= primitive class or any class type provided it has a constructor without arguments,
    *          action="message identifier")
    * Note : the 'action' parameter is an option. If present, it means that the action will be executed on property change.
    * Entries for required properties will be created in property container at association if needed and initialized to default values.
    * 
    * @param element the element
    */
   public final void associate(Object element)
   {
      createRequiredProperties(element);
      associateActionMethods(element, element.getClass());
      associateFields(element, element.getClass());
      initializeFields(element, element.getClass());
      bindFields(element, element.getClass());
   }

   /**
    * Creates the required properties for the element.
    * The properties found here are those from the Require type.
    *
    * @param element the element
    */
   private void createRequiredProperties(Object element)
   {
      Class<?> cl = element.getClass();
      if ((cl.isAnnotationPresent(Requires.class)) || (cl.isAnnotationPresent(Require.class)))
      {
         Require[] annotations = cl.getAnnotationsByType(Require.class);
         for (Require annotation : annotations)
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
      }
   }

   /**
    * Associate action methods.
    *
    * @param element the element
    * @param cl the cl
    */
   private void associateActionMethods(Object element, Class<?> cl)
   {
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
    * Associate fields.
    *
    * @param element the element
    * @param cl the cl
    */
   private void associateFields(Object element, Class<?> cl)
   {
      for (Field field : cl.getDeclaredFields())
      {
         if (field.isAnnotationPresent(Associate.class))
         {
            field.setAccessible(true);
            Associate associates = field.getAnnotation(Associate.class);
            if (!this.hasProperty(associates.value()))
            {
               try
               {
                  setPropertyValue(associates.value(), field.get(element));
               }
               catch (IllegalArgumentException | IllegalAccessException e)
               {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
               }
               
            }
            else
            {
               try
               {
                  field.set(element, getProperty(associates.value()).get());
               }
               catch (IllegalArgumentException | IllegalAccessException e)
               {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
               }
            }

            List<ObjectField> propFields = fieldsMap.get(associates.value());
            if (propFields == null)
            {
               propFields = new ArrayList<>();
               fieldsMap.put(associates.value(), propFields);
            }
            propFields.add(new ObjectField(element, field));
         }
      }
      cl = cl.getSuperclass();
      if (!Object.class.equals(cl))
      {
         associateFields(element, cl);
      }
   }

   /**
    * Initialize fields.
    *
    * @param element the element
    * @param cl the class
    */
   private void initializeFields(Object element, Class<?> cl)
   {
      for (Field field : cl.getDeclaredFields())
      {
         if (field.isAnnotationPresent(Initialize.class))
         {
            field.setAccessible(true);
            Initialize initialize = field.getAnnotation(Initialize.class);
            if (!this.hasProperty(initialize.value()))
            {
               try
               {
                  setPropertyValue(initialize.value(), field.get(element));
               }
               catch (IllegalArgumentException | IllegalAccessException e)
               {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
               }
               
            }
            else
            {
               try
               {
                  field.set(element, getProperty(initialize.value()).get());
               }
               catch (IllegalArgumentException | IllegalAccessException e)
               {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
               }
            }
         }
      }
      cl = cl.getSuperclass();
      if (!Object.class.equals(cl))
      {
         initializeFields(element, cl);
      }
   }

   /**
    * Bind fields.
    *
    * @param element the element
    * @param cl the cl
    */
   @SuppressWarnings({ "unchecked", "rawtypes" })
   private void bindFields(Object element, Class<?> cl)
   {
      for (Field field : cl.getDeclaredFields())
      {
         if ((field.isAnnotationPresent(Bind.class)) && (Property.class.isAssignableFrom(field.getType())))
         {
            field.setAccessible(true);
            Bind associates = field.getAnnotation(Bind.class);
            if (!this.hasProperty(associates.value()))
            {
               try
               {
                  setPropertyValue(associates.value(), ((Property<?>) field.get(element)).getValue());
               }
               catch (IllegalArgumentException | IllegalAccessException e)
               {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
               }
               
            }
            
            Property containerProperty = getProperty(associates.value());
            Property elementProperty = null;
            try
            {
               elementProperty = (Property) field.get(element);
            }
            catch (IllegalArgumentException | IllegalAccessException e)
            {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
            
            if (elementProperty != null)
            {
               if (associates.biDirectional())
               {
                  elementProperty.bindBidirectional(containerProperty);
               }
               else
               {
                  elementProperty.bind(containerProperty);
               }
            }
         }
      }
      cl = cl.getSuperclass();
      if (!Object.class.equals(cl))
      {
         associateFields(element, cl);
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
   private void execute(String actionKey, Object actionContent, boolean useParameter)
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

   /**
    * Creates the properties.
    */
   private void createProperties()
   {
      Require[] existing = getClass().getAnnotationsByType(Require.class);
      for (Require annotation : existing)
      {
         createProperty(annotation);
      }
   }
   
   /**
    * Creates the property.
    *
    * @param annotation the annotation
    */
   private void createProperty(Require annotation)
   {
      if (!hasProperty(annotation.key(), annotation.type()))
      {
         Object value = getDefaultValueForClass(annotation.type());
         setPropertyValue(annotation.key(), value);

       addPropertyListener(annotation.key(),
             (v, o, n) ->
       {
          List<ObjectField> propFields = fieldsMap.get(annotation.key());
          if (propFields != null)
          {
             for(ObjectField field : propFields)
             {
                try
                {
                   field.getField().set(field.getObject(), n);
                }
                catch (IllegalArgumentException | IllegalAccessException e)
                {
                   // TODO Auto-generated catch block
                   e.printStackTrace();
                }
             }
          }
          
          List<String> propActions = propertyActionsMap.get(annotation.key());
          if (propActions != null)
          {
             for(String action : propActions)
             {
                execute(action, n);
             }
          }
       });

      }
   }

   /**
    * Gets the default value for class.
    *
    * @param cl the cl
    * @return the default value for class
    */
   private Object getDefaultValueForClass(Class<?> cl)
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
         e.printStackTrace();
      }
      return value;
   }
}
