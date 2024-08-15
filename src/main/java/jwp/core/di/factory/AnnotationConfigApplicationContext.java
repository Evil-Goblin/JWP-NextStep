package jwp.core.di.factory;

import jwp.core.annotation.ComponentScan;
import jwp.core.di.bean.AnnotatedBeanDefinitionReader;
import jwp.core.di.bean.ApplicationContext;
import jwp.core.di.bean.BeanFactory;
import jwp.core.di.bean.ClasspathBeanDefinitionScanner;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Slf4j
public class AnnotationConfigApplicationContext implements ApplicationContext {
    private static final String ANNOTATION_PACKAGE = "jwp.core.annotation";
    private final BeanFactory beanFactory;

    public AnnotationConfigApplicationContext(Class<?>... configClasses) {
        Object[] basePackages = findBasePackages(configClasses);
        beanFactory = new BeanFactory();
        AnnotatedBeanDefinitionReader annotatedBeanDefinitionReader = new AnnotatedBeanDefinitionReader(beanFactory);
        annotatedBeanDefinitionReader.register(configClasses);

        if (basePackages.length > 0) {
            ClasspathBeanDefinitionScanner scanner = new ClasspathBeanDefinitionScanner(beanFactory);
            scanner.doScan(ANNOTATION_PACKAGE, basePackages);
        }

        beanFactory.initialize();
    }

    private Object[] findBasePackages(Class<?>[] annotatedClasses) {
        List<Object> basePackages = new ArrayList<>();
        for (Class<?> annotatedClass : annotatedClasses) {
            if (annotatedClass.isAnnotationPresent(ComponentScan.class)) {
                String[] componentScanList = annotatedClass.getAnnotation(ComponentScan.class).value();
                for (String basePackage : componentScanList) {
                    log.info("ComponentScan basePackage : {}", basePackage);
                }
                basePackages.addAll(Arrays.stream(componentScanList).toList());
            }
        }
        return basePackages.toArray();
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        return beanFactory.getBean(clazz);
    }

    @Override
    public Set<Class<?>> getBeanClasses() {
        return beanFactory.getBeanClasses();
    }
}
