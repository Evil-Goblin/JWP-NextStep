package jwp.core.di.inject;

import jwp.core.di.bean.BeanFactory;
import jwp.core.di.bean.BeanFactoryUtils;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class ConstructorInjector extends AbstractInjector {

    public ConstructorInjector(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    public void inject(Class<?> clazz) {
        if (beanFactory.hasBean(clazz)) {
            return;
        }

        Constructor<?> constructor = BeanFactoryUtils.getCreatableConstructor(clazz);

        beanFactory.registerBean(clazz, instantiateConstructor(constructor));
    }

    private Object instantiateConstructor(Constructor<?> constructor) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        List<Object> args = new ArrayList<>();
        for (Class<?> parameterType : parameterTypes) {
            Class<?> concreteClass = BeanFactoryUtils.findConcreteClass(parameterType, beanFactory.getPreInstantiateBeans());

            Object arg = instantiateClass(concreteClass);
            args.add(arg);
        }
        return BeanUtils.instantiateClass(constructor, args.toArray());
    }
}
