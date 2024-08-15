package jwp.core.di.bean;

import jwp.core.annotation.Bean;
import jwp.core.annotation.Inject;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import static org.reflections.ReflectionUtils.*;

@Slf4j
public class BeanFactoryUtils {

    public static Constructor<?> getCreatableConstructor(Class<?> clazz) {
        Set<Constructor> allConstructors = getAllConstructors(clazz, withAnnotation(Inject.class));
        return allConstructors.stream().findFirst().orElseGet(() -> getNoArgsConstructor(clazz));
    }

    public static Constructor<?> getNoArgsConstructor(Class<?> clazz) {
        try {
            return clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            log.error("Creatable constructor not found[class={}]", clazz);
            return null;
        }
    }

    public static Class<?> findConcreteClass(Class<?> clazz, Set<Class<?>> preInstantiateBeans) {
        if (!clazz.isInterface() && preInstantiateBeans.contains(clazz)) {
            return clazz;
        }

        for (Class<?> preInstantiateBean : preInstantiateBeans) {
            Set<Class<?>> interfaces = Set.of(preInstantiateBean.getInterfaces());
            if (interfaces.contains(clazz)) {
                return preInstantiateBean;
            }
        }

        throw new RuntimeException("Not found any interfaces for " + clazz);
    }

    public static Set<Field> getAllInjectableFields(Class<?> clazz) {
        return getAllFields(clazz, withAnnotation(Inject.class));
    }

    public static Set<Method> getAllInjectableMethods(Class<?> clazz) {
        return getAllMethods(clazz, withAnnotation(Inject.class));
    }

    public static Set<Method> getBeanMethods(Class<?> clazz) {
        return getAllMethods(clazz, withAnnotation(Bean.class));
    }

    public static Set<Method> getBeanMethods(Class<?> clazz, Class<? extends Annotation> filterAnnotation) {
        return getAllMethods(clazz, withAnnotation(filterAnnotation));
    }

    public static Object invokeMethod(Method method, Object bean, Object[] args) {
        try {
            return method.invoke(bean, args);
        } catch (InvocationTargetException | IllegalAccessException e) {
            log.error("invoke method error", e);
            return null;
        }
    }
}
