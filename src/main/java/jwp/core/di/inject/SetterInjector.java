package jwp.core.di.inject;

import jwp.core.di.bean.BeanFactory;
import jwp.core.di.bean.BeanFactoryUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class SetterInjector extends AbstractInjector {

    public SetterInjector(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    public void inject(Class<?> clazz) {
        Object bean = getBean(clazz);

        Set<Method> allInjectableMethods = BeanFactoryUtils.getAllInjectableMethods(clazz);
        for (Method method : allInjectableMethods) {
            try {
                method.invoke(bean, instantiateClass(getSetterType(method)));
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Class<?> getSetterType(Method method) {
        return method.getParameterTypes()[0];
    }
}
