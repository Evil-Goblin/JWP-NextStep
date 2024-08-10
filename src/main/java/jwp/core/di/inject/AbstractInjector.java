package jwp.core.di.inject;

import jwp.core.di.bean.BeanFactory;
import jwp.core.di.bean.BeanFactoryUtils;

import java.lang.reflect.Constructor;

public abstract class AbstractInjector implements Injector {

    protected final BeanFactory beanFactory;

    public AbstractInjector(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    protected Object getBean(Class<?> clazz) {
        Class<?> concreteClass = BeanFactoryUtils.findConcreteClass(clazz, beanFactory.getPreInstantiateBeans());

        return beanFactory.getBean(concreteClass);
    }

    protected Object instantiateClass(Class<?> clazz) {
        return beanFactory.instantiateClass(clazz);
    }

    protected Object instantiateClassByNoArgsConstructor(Class<?> clazz) {
        Object bean = beanFactory.getBean(clazz);
        if (bean != null) {
            return bean;
        }

        Constructor<?> noArgsConstructor = BeanFactoryUtils.getNoArgsConstructor(clazz);
        try {
            return noArgsConstructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
