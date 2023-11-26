package org.example.webserver.annotations;

import org.example.webserver.service.variable.HTTPMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@RequestMapping(method = HTTPMethod.PUT)
public @interface PutMapping {
    String name() default "";
}
