package net.alantea.proper;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Repeatable(Manages.class)
@Retention(RUNTIME)
@Target(METHOD)
@Inherited 
public @interface Manage
{
   public String value();
}
