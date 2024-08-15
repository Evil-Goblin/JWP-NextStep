package jwp.core.di.bean;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Set;

@Slf4j
public class AnnotatedBeanDefinitionReader {

    private final BeanDefinitionRegistry beanDefinitionRegistry;

    public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    public void register(Class<?>... configureClasses) {
        for (Class<?> configureClass : configureClasses) {
            registerBean(configureClass);
        }
    }

    public void registerBean(Class<?> configureClass) {
        beanDefinitionRegistry.registerBeanDefinition(configureClass, new BeanDefinition(configureClass));
        Set<Method> beanMethods = BeanFactoryUtils.getBeanMethods(configureClass);
        for (Method beanMethod : beanMethods) {
            log.debug("@Bean method: {}", beanMethod);
            AnnotatedBeanDefinition annotatedBeanDefinition = new AnnotatedBeanDefinition(beanMethod.getReturnType(), beanMethod);
            beanDefinitionRegistry.registerBeanDefinition(beanMethod.getReturnType(), annotatedBeanDefinition);
        }
    }
}
