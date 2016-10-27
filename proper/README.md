# Proper library quick documentation

## Concept
In Java, using reflection and annotations allow to had on-fly actions on the classes and objects from your code. This may be used to analyze code elements and store association between them.

in Proper, we use this concept to connect any object to special objects named *property containers*. This way, we are able to share information and manage actions in a very simple way.

## Property containers
Basically, a *property container* is just that : an object containing a map of named properties and offering methods to create, get or modify them, that needs to be derived to be really useful. But with some special features.
In standard programming, derived classes will have to call methods like `createProperty`or so to add new properties in the container. With proper, you may simply use annotations to add properties and associate actions to their modification.
These actions may be connected to methods using another annotation type. Each modification of a property value with actions associated will trigger the associated actions, thus call the corresponding methods.
But the really interesting feature is the ability to associate any kind of object with the container.

## Associating elements
Any object may be associated with a container. But to realize the connection, we will use here also some annotations. The same as for the container may be used to add some needed properties in the container and to associate actions. This way, these actions are connected to property modifications in the container, but may trigger methods in the associated objects.
It is also possible to link fields in the object to properties in the container and so share values.

## Simple example
This is a simple basic example
### Container definition
