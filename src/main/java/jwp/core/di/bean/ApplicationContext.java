package jwp.core.di.bean;

import java.util.Set;

public interface ApplicationContext {
    <T> T getBean(Class<T> clazz);

    Set<Class<?>> getBeanClasses();
}
