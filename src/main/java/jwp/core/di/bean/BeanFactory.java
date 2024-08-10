package jwp.core.di.bean;

import jwp.core.annotation.Controller;
import jwp.core.di.inject.Injector;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.util.*;

public class BeanFactory {
    private final Set<Class<?>> preInstantiateBeans;
    private final Map<Class<?>, Object> beans = new HashMap<>();
    private final List<Injector> injectors = new ArrayList<>();

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

    public Object getBean(Class<?> beanClass) {
        return beans.get(beanClass);
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

    public void addInjector(Injector injector) {
        injectors.add(injector);
    }

    public Object instantiateClass(Class<?> clazz) {
        for (Injector injector : injectors) {
            injector.inject(clazz);
        }

        return getBean(clazz);
    }

    public boolean hasBean(Class<?> clazz) {
        return beans.containsKey(clazz);
    }

    public void registerBean(Class<?> clazz, Object bean) {
        beans.put(clazz, bean);
    }

    public Set<Class<?>> getPreInstantiateBeans() {
        return Collections.unmodifiableSet(preInstantiateBeans);
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
