package org.example.webserver.annotations;

import org.example.webserver.service.variable.HTTPMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RequestMapping {
    String name() default "";


    HTTPMethod method() default HTTPMethod.GET;
}
