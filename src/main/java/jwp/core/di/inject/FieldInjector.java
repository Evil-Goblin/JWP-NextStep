package jwp.core.di.inject;

import jwp.core.di.bean.BeanFactory;
import jwp.core.di.bean.BeanFactoryUtils;

import java.lang.reflect.Field;
import java.util.Set;

public class FieldInjector extends AbstractInjector {

    public FieldInjector(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    public void inject(Class<?> clazz) {
        Object bean = getBean(clazz);
        Set<Field> allInjectableFields = BeanFactoryUtils.getAllInjectableFields(clazz);
        for (Field field : allInjectableFields) {
            field.setAccessible(true);
            try {
                field.set(bean, instantiateClass(field.getType()));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
