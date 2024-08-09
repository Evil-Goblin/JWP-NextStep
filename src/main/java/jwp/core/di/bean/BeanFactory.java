package jwp.core.di.bean;

import jwp.core.annotation.Controller;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.util.*;

public class BeanFactory {
    private final Set<Class<?>> preInstantiateBeans;
    private final Map<Class<?>, Object> beans = new HashMap<>();

    public BeanFactory(Set<Class<?>> preInstantiateBeans) {
        this.preInstantiateBeans = preInstantiateBeans;
    }

    public void initialize() {
        for (Class<?> preInstantiateBean : preInstantiateBeans) {
            if (!beans.containsKey(preInstantiateBean)) {
                instantiateClass(preInstantiateBean);
            }
        }
    }

    public Map<Class<?>, Object> getControllers() {
        Map<Class<?>, Object> controllers = new HashMap<>();
        for (Class<?> clazz : beans.keySet()) {
            if (clazz.isAnnotationPresent(Controller.class)) {
                controllers.put(clazz, beans.get(clazz));
            }
        }
        return controllers;
    }

    private Object instantiateClass(Class<?> clazz) {
        Object bean = beans.get(clazz);
        if (bean != null) {
            return bean;
        }

        Constructor<?> constructor = BeanFactoryUtils.getCreatableConstructor(clazz);

        bean = instantiateConstructor(constructor);

        beans.put(clazz, bean);
        return bean;
    }

    private Object instantiateConstructor(Constructor<?> constructor) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        List<Object> args = new ArrayList<>();
        for (Class<?> parameterType : parameterTypes) {
            Class<?> concreteClass = BeanFactoryUtils.findConcreteClass(parameterType, preInstantiateBeans);

            Object arg = instantiateClass(concreteClass);
            args.add(arg);
        }
        return BeanUtils.instantiateClass(constructor, args.toArray());
    }
}
