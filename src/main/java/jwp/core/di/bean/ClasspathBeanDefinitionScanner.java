package jwp.core.di.bean;

import jwp.core.annotation.Component;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.util.Set;

@Slf4j
public class ClasspathBeanDefinitionScanner {
    private final BeanDefinitionRegistry registry;

    public ClasspathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void doScan(Object... basePackages) {
        Reflections reflections = new Reflections(basePackages);
        Set<Class<?>> beanClasses = reflections.getTypesAnnotatedWith(Component.class);
        for (Class<?> clazz : beanClasses) {
            if (!clazz.isAnnotation()) {
                registry.registerBeanDefinition(clazz, new BeanDefinition(clazz));
            }
        }
    }
}
