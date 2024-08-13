package jwp.core.di.bean;

import lombok.Getter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class BeanDefinition {
    private final Class<?> beanClazz;

    @Getter
    private final Constructor<?> injectConstructor;

    @Getter
    private final Set<Field> injectFields;

    public BeanDefinition(Class<?> beanClazz) {
        this.beanClazz = beanClazz;
        this.injectConstructor = getInjectConstructor(beanClazz);
        this.injectFields = getInjectFields(beanClazz);
    }

    private static Constructor<?> getInjectConstructor(Class<?> beanClass) {
        return BeanFactoryUtils.getCreatableConstructor(beanClass);
    }

    private static Set<Field> getInjectFields(Class<?> clazz) {
        Set<Field> injectFields = new HashSet<>();
        Set<Class<?>> injectProperties = getInjectPropertiesType(clazz);
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (injectProperties.contains(field.getType())) {
                injectFields.add(field);
            }
        }
        return injectFields;
    }

    private static Set<Class<?>> getInjectPropertiesType(Class<?> clazz) {
        Set<Class<?>> injectProperties = new HashSet<>();
        Set<Method> injectMethod = BeanFactoryUtils.getAllInjectableMethods(clazz);
        for (Method method : injectMethod) {
            Class<?>[] paramTypes = method.getParameterTypes();
            if (paramTypes.length != 1) {
                throw new IllegalStateException("DI할 메소드 인자는 하나여야 합니다.");
            }

            injectProperties.add(paramTypes[0]);
        }

        Set<Field> injectField = BeanFactoryUtils.getAllInjectableFields(clazz);
        for (Field field : injectField) {
            injectProperties.add(field.getType());
        }
        return injectProperties;
    }

    @Override
    public String toString() {
        return "BeanDefinition[" +
                "beanClazz=" + beanClazz +
                ", injectConstructor=" + injectConstructor +
                ", injectFields=" + injectFields +
                ']';
    }
}
