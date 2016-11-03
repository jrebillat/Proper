# Proper library quick documentation

## Concept
In Java, using reflection and annotations allow to had on-fly actions on the classes and objects from your code. This may be used to analyze code elements and store association between them.

in Proper, we use this concept to connect any object to special objects named *property containers*. This way, we are able to share information and manage actions in a very simple way.

## Property containers
Basically, a *property container* is just that : an object containing a map of named properties and offering methods to create, get or modify them, that needs to be derived (from base class `PropertyContainer`) to be really useful. But with some special features.
In standard programming, derived classes will have to call methods like `createProperty`or so to add new properties in the container. With proper, you may simply use annotations to add properties and associate actions to their modification.

## Actions
The actions associated with a property may be connected to methods using another annotation type. Each modification of a property value with actions associated will trigger the associated actions, thus call the corresponding methods.
But the really interesting feature is the ability to associate any kind of object with the container.

## Associating elements
Any object may be associated with a container. But to realize the connection, we will use here also some annotations. The same as for the container may be used to add some needed properties in the container and to associate actions. This way, these actions are connected to property modifications in the container, but may trigger methods in the other associated objects.
It is possible to link fields in the object to properties in the container and so share values. fields linked in elements will have their value changed to reflect the modifications in the corresponding property.
For initial values, or values *not to be changed* it is possible to just initialize them with the current value of a property. This is less memory-consuming than the association.
At the opposite, it is possible to bind properties in an element to a property in the container (either in the way container to element or bidirectional).
It is possible to register the element in a property of the container, thus to give it a name during association.

## Named Containers
It is sometimes easier not to have to worry about containers and work only with elements. You can achieve this with the named containers : containers instantiated automatically and stored in a map, to be referenced afterwards by their name.
The system may create a special container class to hold data (the base PropertyConTainer may well be far enough for the work). It is then possible to not create any instance at all but let the system instantiate a PropertyContainer and store it along with a name. It is then possible to refer to the container to associate elements by its name. Furthermore, you do not have to request the container creation : it is created automatically before first association if it does not already exist.
Named containers are accessible through static methods in the PropertyContainer class.

## Association key codes
It is sometimes interesting to associate an element to several containers, to have fields or methods related to different data sources. Thus it is possible to associate an element to several containers, differentiating them from o,e another by the association key codes (a String).
The key code is given during element association to a container, to specify their relationship. Then all annotations will be process regarding this key code, that may be given as parameter on each annotation (Associate, Bind, Initialize, Manage and require). If a key code is given for the association, only annotations with this key code will be processed on this container.
By default, the key code is the empty String "". Thus if you do not use any key code, in fact you are using the "" key code. One exception to this rule is the Manage annotation, which by default use the ActionManager.ALL_KEYCODES key code and make the annotated method to manage the action for all containers, regardless of the given key code. You may specify the ActionManager.DEFAUT_KEYCODE to make the method only manage default key code container actions.

It is possible to associate a named container to element with a key code.

## The annotations
### @Require
The @Require annotation is usable on any class type. It will make sure that a specified property exist in the container, with required type and, optionally, associated action.
The syntax is :
```java
@Require(
   key="property key"
   type=class type
   action="action key"
   code="key code"
   importFrom="key code"
)
```

The *key* parameter is mandatory. This key will be used in code every time a reference to this property is needed, to get value, to set it, to add listener or any other work.
The *type* parameter is mandatory. It is only use on the first requiring of this property, to select the content type for the Property. ** A work to do will be to verify the coherency in several calls **
The *action* parameter is optional and, if present, will make the system trigger the action with the given name on each modification in the property value. See the @Manage annotation for more on actions.
The *code* parameter is optional and, if present, contains the key code this annotation refers to. The annotation will only be processed for this key code association.
The *importFrom* parameter is optional and, if present, contains the key code this annotation refers to. The property from this key code association will be used to create and set a property with the same name in the default container.

### @Register
The @Register annotation is usable on any instance class definition. It will create or update a property in the container to store the element and refer to it in the code.
The syntax is :
```java
@Register("name")
```
or
```java
@Register(
   value="name"
   code="key code"
)
```

The *name* value is the property key in the container.
The *code* parameter is optional and, if present, contains the key code this annotation refers to.

### @Manage
The @Manage annotation is usable on any instance method (even on private ones). It only work if the method expect no parameter or one parameter of a type compatible with the property value type. It will just inform the container to execute this method each time the corresponding action is triggered.
The syntax is :
```java
@Manage("action key")
```
or
```java
@Manage(
   value="action key"
   code="key code"
)
```

The *action key* value is the key of the corresponding action, set on at least one @Require in one class (the container or any element).
The *code* parameter is optional and, if present, contains the key code this annotation refers to.
As said, the method must not expect more than one parameter (zero or one are OK). If a parameter is expected, it must be assignable from the property value type. The return type of the method is ignored.

### @Associate
The @Associate annotation is usable on any instance field (even on private ones). It only work if the field has a type compatible with the property value type to associate. It will just inform the container to change the field value each time the property content value is changed.
The syntax is :
```java
@Associate("property key")
```
or
```java
@Associate(
   value="property key"
   code="key code"
)
```

The *property key* value is the key of the corresponding property, set on at least one @Require in the class. The property must have a content value compatible with the field.
The *code* parameter is optional and, if present, contains the key code this annotation refers to.

### @Initialize
The @Initialize annotation is usable on any instance field (even on private ones). It only work if the field has a type compatible with the property value type to initialize with. It will just set the field value to the property content value at association. The value will not be affected afterwards, even if the property contant value is changed.
The syntax is :
```java
@Initialize("property key")
```
or
```java
@Initialize(
   value="property key"
   code="key code"
)
```

The *property key* value is the key of the corresponding property, set on at least one @Require in the class. The property must have a content value compatible with the field and have been set to a intelligent value before element instance association with the container.
The *code* parameter is optional and, if present, contains the key code this annotation refers to.

### @Bind
The @Bind annotation is usable on any instance field that derives from a Property (even on private ones). It only work if the content object for the field property has a type compatible with the container property value type to bind with. It will just bind the two properties, either from container property to field property, or bidirectional.
The syntax is :
```java
@Bind("property key")
```
or
```java
@Bind(
   value="property key"
   bidirectional=true|false
   code="key code"
)
```

The *property key* value is the key of the corresponding container property, set on at least one @Require somewhere. The container property must have a content value compatible with the field property.
The *bidirectional* parameter is optional and set to *false* by default. If set to true, the bind is bidirectional. By default or set to false, the bind will be from container property to field property.
The *code* parameter is optional and, if present, contains the key code this annotation refers to.

## Simple example
This is a simple basic (and useless) example that convert a integer in long and a long to double to print it.

### Container definition
The class below is the container-derived class.
```java
package net.alantea.proper.example;

import net.alantea.proper.PropertyContainer;
import net.alantea.proper.Require;

@Require(key=Container.PROP_ONE, type=Integer.class, action=Container.ACT_GOTINTEGER)
public class Container extends PropertyContainer
{
   public static final String PROP_ONE = "PropertyOne";
   public static final String ACT_GOTINTEGER = "GotInteger";

   public static void main(String[] args)
   {
      Container container = new Container();
      container.associate(new IntegerToLongConverter());
      container.associate(new LongToDoubleConverter());
      container.associate(new DoublePrinter());
      
      container.setPropertyValue(PROP_ONE, 1);
      container.setPropertyValue(PROP_ONE, 2);
      container.setPropertyValue(PROP_ONE, 3);
   }
}
```
In this class defining a container, there is a property required, of type Integer, named "PropertyOne". This property is managed by an action named "GotInteger".

It also includes the main method (this is not mandatory) that will create an instance of the container and associate instances of the other needed classes.

### Integer to long converter
This class is just a converter from integer to long, for a container.
```java
package net.alantea.proper.example;

import net.alantea.proper.Associate;
import net.alantea.proper.Initialize;
import net.alantea.proper.Manage;
import net.alantea.proper.Require;

@Require(key=Container.PROP_ONE, type=Integer.class)
@Require(key=IntegerToLongConverter.PROP_TWO, type=Long.class)
public class IntegerToLongConverter
{
   public static final String PROP_TWO = "PropertyTwo";

   @Associate(Container.PROP_ONE)
   private int reference;
   
   @Initialize(Container.PROP_THIS)
   private Container container;
   
   @Manage(Container.ACT_GOTINTEGER)
   private void actionGotInteger()
   {
      System.out.println("got integer : " + reference);
      container.setPropertyValue(IntegerToLongConverter.PROP_TWO, (long)reference);
   }
}
```
This class requires two properties : the same as the container (integer one) and another new property of type long, to be added to container.
It associate the integer property to the integer field *reference* and initialize the container itself to the *container* field, to use it when needed.
It also defines a method to manage the action "GotInteger" defined in the container. This method will change the long property to reflect the integer modification.

### Long to double converter
This class is just a converter from long to double, for a container.
```java
package net.alantea.proper.example;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import net.alantea.proper.Associate;
import net.alantea.proper.Bind;
import net.alantea.proper.Manage;
import net.alantea.proper.Require;

@Require(key=IntegerToLongConverter.PROP_TWO, type=Long.class, action=LongToDoubleConverter.ACT_GOTLONG)
@Require(key=LongToDoubleConverter.PROP_THREE, type=Double.class)
public class LongToDoubleConverter
{
   public static final String PROP_THREE = "PropertyThree";
   public static final String ACT_GOTLONG = "GotLong";

   @Bind(IntegerToLongConverter.PROP_TWO)
   private LongProperty reference = new SimpleLongProperty();
   
   @Initialize(Container.PROP_THIS)
   private Container container;
   
   @Manage(ACT_GOTLONG)
   private void actionGotLong()
   {
      System.out.println("got long : " + reference.longValue());
      container.setPropertyValue(LongToDoubleConverter.PROP_THREE, (double)reference.get());
   }
}
```
This class requires two properties : the same long property as the IntegerToLongConverter class and another new property of type double, to be added to container. It associate an action named "GotLong" with this property value change.
It binds the long property to the long property field *reference* and initialize the container itself to the *container* field, to use it when needed.
It also defines a method to manage the action "GotLong" defined in this element. This method will change the double property value to reflect the long modification.

### Double printer
This class is offering a way to print doubles passed to its method.
```java
package net.alantea.proper.example;

import net.alantea.proper.Manage;
import net.alantea.proper.Require;

@Require(key=LongToDoubleConverter.PROP_THREE, type=Double.class, action=DoublePrinter.ACT_GOTDOUBLE)
public class DoublePrinter
{
   public static final String ACT_GOTDOUBLE = "GotDouble";

   @Manage(ACT_GOTDOUBLE)
   private void actionGotDouble(double value)
   {
      System.out.println("I got a double value : " + value);
   }
}
```
This class requires a double property : the same long property as the LongToDoubleConverter. It associate an action named "GotDouble" with this property value change.
It also defines a method to manage the action "GotDouble" defined in this element. This method will print its argument. In fact, it will be used to print the property value to reflect the double modification.

### Result
The example above should send the following output to the console :
`
got integer : 1
got long : 1
I got a double value : 1.0
got integer : 2
got long : 2
I got a double value : 2.0
got integer : 3
got long : 3
I got a double value : 3.0
`