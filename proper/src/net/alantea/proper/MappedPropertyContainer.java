package net.alantea.proper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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
 * The property container or element class should be extended with  Require annotations :
 *  Require(key="key string to define", type= primitive class or any class type provided it has a constructor without arguments,
 * action="action key"). The action parameter is an option.
 * Entries for required properties will be created and added to the container at instance creation and initialized to default values
 *  : 0 value for primitive types, false for booleans, "" for String and null for other object types.
 * 
 * Methods with  zero or one parameter in property container or element class may be extended with one or more @Manage annotations :
 *  Manage("action key string").
 * The action key may correspond to an 'action' parameter value in one or more  Require definition, in the property container,
 * another associated element or even the same class. Each time the value of the property change, the annotated methods with the
 * action key in any element will be called. If a parameter is needed, the value passed to the method is the new property value.
 * But caution with it : there is no class compatibility verification here.
 * Methods with  Manage annotations in elements may also be called directly using the property container 'execute('action key', value).
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
public class MappedPropertyContainer extends PropertyContainer
{
   /** The meta properties. */
   private static Map<Object, MappedPropertyContainer> metaProperties = new HashMap<>();
   
   /** The properties. */
   private Map<String, ObjectProperty<Object>> properties;
   
   /**
    * Instantiates a new container.
    */
   public MappedPropertyContainer()
   {
      this((String)null);
   }
   
   /**
    * Instantiates a new container.
    *
    * @param name the name for container
    */
   public MappedPropertyContainer(String name)
   {
      super(name);
   }
   
   /**
    * Instantiates a new container.
    *
    * @param name the name for container
    * @param properties the properties
    */
   public MappedPropertyContainer(String name, Map<String, Object> properties)
   {
      super(name);
      mapProperties(properties);
   }
   
   /**
    * Instantiates a new property container and associate a list of elements to it.
    *
    * @param toBeAssociated the elements to be associated. They should use the annotations to be fully associated.
    */
   public MappedPropertyContainer(Object... toBeAssociated)
   {
      this();
      for (Object toAssociate : toBeAssociated)
      {
         associate(toAssociate);
      }
   }
   
   /* (non-Javadoc)
    * @see net.alantea.proper.PropertyContainer#doAssociation(java.lang.String, java.lang.Object)
    */
   public void doAssociation(String keycode, Object element)
   {
      bindFields(element, element.getClass(), keycode);
   }
   
   /**
    * Gets the named manager.
    *
    * @param hook the hook
    * @return the manager
    */
   public static MappedPropertyContainer getContainer(Object hook)
   {
      if (hook == null)
      {
         return null;
      }
      MappedPropertyContainer container = null;
      if (hook instanceof MappedPropertyContainer)
      {
         container = (MappedPropertyContainer) hook;
      }
      else
      {
         container = metaProperties.get(hook);
         if (container == null)
         {
            container = new MappedPropertyContainer(hook);
            metaProperties.put(hook, container);
         }
      }
      return container;
   }

   /**
    * Checks for property existence.
    *
    * @param key the property key
    * @return true, if successful
    */
   public boolean hasProperty(String key)
   {
      if (properties == null)
      {
         properties = new LinkedHashMap<>();
      }
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
      if (properties == null)
      {
         properties = new LinkedHashMap<>();
      }
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
    * Gets the property keys.
    *
    * @return the property keys list
    */
   public final List<String> getPropertyKeys()
   {
      if (properties == null)
      {
         properties = new LinkedHashMap<>();
      }
      List<String> ret = new LinkedList<>();
      ret.addAll(properties.keySet());
      return ret;
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
      if (properties == null)
      {
         properties = new LinkedHashMap<>();
      }
      if ((key != null) && (properties.containsKey(key)))
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
   public void setPropertyValue(String key, Object value)
   {
      if (properties == null)
      {
         properties = new LinkedHashMap<>();
      }
      if (key == null)
      {
         return;
      }
      if (!properties.containsKey(key))
      {
         properties.put(key, new SimpleObjectProperty<Object>(value));
      }
      else
      {
         properties.get(key).set(value);
      }
      ActionManager.execute(this, MESS_SETVALUE, key, value);
   }
   
   /**
    * Adds a property listener for the given property key.
    *
    * @param key the property key
    * @param listener the change listener
    */
   public void addPropertyListener(String key, ChangeListener<Object> listener)
   {
      if (properties == null)
      {
         properties = new LinkedHashMap<>();
      }
      if ((key != null) && (listener != null))
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
      if (properties == null)
      {
         properties = new LinkedHashMap<>();
      }
      if ((listener != null) && (key != null))
      {
         ObjectProperty<Object> entry = properties.get(key);
         if (entry != null)
         {
            entry.removeListener(listener);
         }
      }
   }
   
   /* (non-Javadoc)
    * @see net.alantea.proper.PropertyContainer#bindProperty(java.lang.String, net.alantea.proper.PropertyContainer, java.lang.String)
    */
   @SuppressWarnings("unchecked")
   public void bindProperty(String localKey, PropertyContainer source, String originKey)
   {
      @SuppressWarnings("rawtypes")
      Property fromProperty = properties.get(localKey);
      @SuppressWarnings("rawtypes")
      Property toProperty = ((MappedPropertyContainer)source).properties.get(originKey);
      toProperty.bind(fromProperty);
   }

   /**
    * Bind fields.
    *
    * @param element the element
    * @param cl the cl
    * @param keyCode the key code
    */
   @SuppressWarnings({ "unchecked", "rawtypes" })
   private void bindFields(Object element, Class<?> cl, String keyCode)
   {
      for (Field field : cl.getDeclaredFields())
      {
         if ((field.isAnnotationPresent(Bind.class)) && (Property.class.isAssignableFrom(field.getType())))
         {
            field.setAccessible(true);
            Bind associates = field.getAnnotation(Bind.class);
            if ((associates.code().equals(keyCode)) || (ALL_KEYCODES.equals(keyCode)))
            {
               if (!hasProperty(associates.value()))
               {
                  try
                  {
                     setPropertyValue(associates.value(), ((Property<?>) field.get(element)));
                  }
                  catch (IllegalArgumentException | IllegalAccessException e)
                  {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                  }
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
         associateFields(element, cl, keyCode);
      }
   }

   /**
    * Gets the property.
    *
    * @param value the value
    * @return the property
    */
   public ObjectProperty<?> getProperty(String value)
   {
      return properties.get(value);
   }

   /**
    * Sets the property.
    *
    * @param key the key
    * @param value the value
    */
   public void setProperty(String key, Object value)
   {
      properties.put(key, new SimpleObjectProperty<Object>(value));
   }
   
   /**
    * Map properties.
    *
    * @param map the map
    */
   public void mapProperties(Map<String, Object> map)
   {
      for (String key : map.keySet())
      {
         setProperty(key, map.get(key));
      }
   }

   /**
    * Associate hook.
    *
    * @param hook the hook
    * @param key the key
    * @param element the element
    */
   public static void associateHook(Object hook, String key, Object element)
   {
      if (hook instanceof MappedPropertyContainer)
      {
         ((MappedPropertyContainer) hook).associate(key, element);
         return;
      }
      
      MappedPropertyContainer hidden = metaProperties.get(hook);
      if (hidden == null)
      {
         hidden = new MappedPropertyContainer();
         metaProperties.put(hook, hidden);
      }
      hidden.associate(key, element);
   }

   /**
    * Sets the property value.
    *
    * @param hook the hook
    * @param key the key
    * @param value the value
    */
   public static void setPropertyValue(Object hook, String key, Object value)
   {
      if (hook instanceof MappedPropertyContainer)
      {
         ((MappedPropertyContainer) hook).setPropertyValue(key, value);
         return;
      }
      
      MappedPropertyContainer hidden = metaProperties.get(hook);
      if (hidden == null)
      {
         hidden = new MappedPropertyContainer();
         metaProperties.put(hook, hidden);
      }
      hidden.setPropertyValue(key, value);
   }

   /**
    * Gets the hook.
    *
    * @param container the container
    * @return the hook
    */
   public Object getHook(PropertyContainer container)
   {
      if (! metaProperties.containsValue(container))
      {
         return container;
      }
      for (Object key : metaProperties.keySet())
      {
         if (metaProperties.get(key).equals(container))
         {
            return key;
         }
      }
      return null;
   }
}
