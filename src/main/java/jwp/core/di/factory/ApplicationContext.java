package jwp.core.di.factory;

import jwp.core.di.bean.BeanFactory;
import jwp.core.di.bean.ClasspathBeanDefinitionScanner;

import java.util.Set;

public class ApplicationContext {
    private static final String ANNOTATION_PACKAGE = "jwp.core.annotation";
    private final BeanFactory beanFactory;

    public ApplicationContext(Object... basePackage) {
        beanFactory = new BeanFactory();
        ClasspathBeanDefinitionScanner scanner = new ClasspathBeanDefinitionScanner(beanFactory);
        scanner.doScan(ANNOTATION_PACKAGE, basePackage);
        beanFactory.initialize();
    }

    public <T> T getBean(Class<T> clazz) {
        return beanFactory.getBean(clazz);
    }

    public Set<Class<?>> getBeanClasses() {
        return beanFactory.getBeanClasses();
    }
}
