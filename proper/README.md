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

## The annotations
### @Require
The @Require annotation is usable on any class type. It will make sure that a specified property exist in the container, with required type and, optionally, associated action.
The syntax is :
```java
@Require(
   key="property key"
   type=class type
   action="action key"
)
```

The *key* parameter is mandatory. This key will be used in code every time a reference to this property is needed, to get value, to set it, to add listener or any other work.
The *type* parameter is mandatory. It is only use on the first requiring of this property, to select the content type for the Property. ** A work to do will be to verify the coherency in several calls **
The *action* parameter is optional and, if present, will make the system trigger the action with the given name on each modification in the property value. See the @Manage annotation for more on actions.

## Simple example
This is a simple basic (and useless) example that convert a integer in long and a long to double to print it.

### Container definition
The class below is the container-derived class.
```java
package net.alantea.proper.example;

import net.alantea.proper.PropertyContainer;
import net.alantea.proper.Require;

@Require(key=Container.PROP_ONE, type=Integer.class)
public class Container extends PropertyContainer
{
   public static final String PROP_ONE = "PropertyOne";

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
In this class defining a container, there is a property required, of type Integer, named "PropertyOne".

It also includes the main method (this is not mandatory) that will create an instance of the container and associate instances of the other needed classes.

### Integer to long converter
This class is just a converter from integer to long, for a container.
```java
package net.alantea.proper.example;

import net.alantea.proper.Associate;
import net.alantea.proper.Manage;
import net.alantea.proper.Require;

@Require(key=Container.PROP_ONE, type=Integer.class, action=IntegerToLongConverter.ACT_GOTINTEGER)
@Require(key=IntegerToLongConverter.PROP_TWO, type=Long.class)
public class IntegerToLongConverter
{
   public static final String PROP_TWO = "PropertyTwo";
   public static final String ACT_GOTINTEGER = "GotInteger";

   @Associate(Container.PROP_ONE)
   private int reference;
   
   @Associate(Container.PROP_THIS)
   private Container container;
   
   @Manage(ACT_GOTINTEGER)
   private void actionGotInteger()
   {
      System.out.println("got integer : " + reference);
      container.setPropertyValue(IntegerToLongConverter.PROP_TWO, (long)reference);
   }
}
```
