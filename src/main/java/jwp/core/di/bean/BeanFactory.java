package jwp.core.di.bean;

import jwp.core.annotation.Controller;
import jwp.core.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

@Slf4j
public class BeanFactory implements BeanDefinitionRegistry {
    private final Map<Class<?>, Object> beans = new HashMap<>();

    private final Map<Class<?>, BeanDefinition> beanDefinitions = new HashMap<>();

    public void initialize() {
        for (Class<?> clazz : getBeanClasses()) {
            getBean(clazz);
        }
    }

    public Set<Class<?>> getBeanClasses() {
        return beanDefinitions.keySet();
    }

    public <T> T getBean(Class<T> clazz) {
        Object bean = beans.get(clazz);
        if (bean != null) {
            return (T) bean;
        }

        BeanDefinition definition = beanDefinitions.get(clazz);
        if (definition instanceof AnnotatedBeanDefinition) {
            bean = createAnnotatedBean(definition);
            beans.put(clazz, bean);
            initialize(bean, clazz);
            return (T) bean;
        }

        Class<?> concreteClass = findConcreteClass(clazz);
        BeanDefinition beanDefinition = beanDefinitions.get(concreteClass);
        bean = inject(beanDefinition);

        beans.put(concreteClass, bean);
        initialize(bean, concreteClass);
        return (T) bean;
    }

    private void initialize(Object bean, Class<?> beanClass) {
        Set<Method> initializeMethods = BeanFactoryUtils.getBeanMethods(beanClass, PostConstruct.class);

        if (initializeMethods.isEmpty()) {
            return;
        }

        for (Method method : initializeMethods) {
            log.debug("@PostConstruct Initialize method: {}", method);
            BeanFactoryUtils.invokeMethod(method, bean, populateArguments(method.getParameterTypes()));
        }
    }

    private Object createAnnotatedBean(BeanDefinition beanDefinition) {
        AnnotatedBeanDefinition annotatedBeanDefinition = (AnnotatedBeanDefinition) beanDefinition;
        Method method = annotatedBeanDefinition.getMethod();
        Object[] objects = populateArguments(method.getParameterTypes());

        return BeanFactoryUtils.invokeMethod(method, getBean(method.getDeclaringClass()), objects);
    }

    private Object[] populateArguments(Class<?>[] parameterTypes) {
        List<Object> args = new ArrayList<>();
        for (Class<?> param : parameterTypes) {
            Object bean = getBean(param);
            if (bean == null) {
                throw new NullPointerException(param + " Not Found");
            }
            args.add(getBean(param));
        }
        return args.toArray();
    }

    private Object inject(BeanDefinition beanDefinition) {
        Object bean = instantiateClass(beanDefinition);

        injectFields(bean, beanDefinition.getInjectFields());

        return bean;
    }

    private void injectFields(Object bean, Set<Field> fields) {
        for (Field field : fields) {
            injectField(bean, field);
        }
    }

    private void injectField(Object bean, Field field) {
        log.debug("Inject Bean: {}, Field: {}", bean, field.getName());
        try {
            field.setAccessible(true);
            field.set(bean, getBean(field.getType()));
        } catch (Exception e) {
            log.error("Inject Bean: {}, Error: ", bean, e);
        }

    }

    private Object instantiateClass(BeanDefinition beanDefinition) {
        log.debug("Instantiate Bean: {}", beanDefinition);
        Constructor<?> constructor = beanDefinition.getInjectConstructor();

        List<Object> args = new ArrayList<>();
        for (Class<?> clazz : constructor.getParameterTypes()) {
            args.add(getBean(clazz));
        }

        Object bean = BeanUtils.instantiateClass(constructor, args.toArray());
        return bean;
    }

    private Class<?> findConcreteClass(Class<?> clazz) {
        Set<Class<?>> beanClasses = getBeanClasses();
        Class<?> concreteClass = BeanFactoryUtils.findConcreteClass(clazz, beanClasses);
        if (!beanClasses.contains(concreteClass)) {
            throw new IllegalStateException(clazz + " is not a Bean");
        }
        return concreteClass;
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

    public boolean hasBean(Class<?> clazz) {
        return beans.containsKey(clazz);
    }

    public void registerBean(Class<?> clazz, Object bean) {
        beans.put(clazz, bean);
    }

    public Set<Class<?>> getPreInstantiateBeans() {
        return Collections.unmodifiableSet(beanDefinitions.keySet());
    }

    @Override
    public void registerBeanDefinition(Class<?> clazz, BeanDefinition beanDefinition) {
        log.debug("register bean {}", clazz);
        beanDefinitions.put(clazz, beanDefinition);
    }
}
