package jwp.core.di.bean;

import jwp.core.annotation.Inject;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.util.Set;

public class BeanFactoryUtils {

    public static Constructor<?> getCreatableConstructor(Class<?> clazz) {
        Set<Constructor> allConstructors = ReflectionUtils.getAllConstructors(clazz, constructor -> constructor.isAnnotationPresent(Inject.class));
        return allConstructors.stream().findFirst().orElseGet(() -> getNoArgsConstructor(clazz));
    }

    public static Constructor<?> getNoArgsConstructor(Class<?> clazz) {
        try {
            return clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Creatable constructor not found[class=" + clazz + "]");
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
}
