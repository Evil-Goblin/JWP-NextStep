package jwp.core.di.bean;

import java.lang.reflect.Method;

public class AnnotatedBeanDefinition extends BeanDefinition {

    private final Method method;

    public AnnotatedBeanDefinition(Class<?> beanClazz, Method method) {
        super(beanClazz);
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }
}
