package net.alantea.proper;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Repeatable(Requires.class)
@Retention(RUNTIME)
@Target(TYPE)
@Inherited 
public @interface Require
{
   public String key();
   public Class<?> type();
   public String action() default "";
}
