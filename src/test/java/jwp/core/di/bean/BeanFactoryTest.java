package jwp.core.di.bean;

import jwp.core.di.*;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class BeanFactoryTest {

    @Test
    void beanScanTest() {
        // given
        BeanFactory beanFactory = new BeanFactory();
        ClasspathBeanDefinitionScanner beanScanner = new ClasspathBeanDefinitionScanner(beanFactory);

        // when
        beanScanner.doScan("jwp.core.annotation", "jwp.core.di");

        // then
        assertThat(beanFactory.getBeanClasses().size()).isEqualTo(8);
    }

    @Test
    void beanFactoryTest() {
        BeanFactory beanFactory = new BeanFactory();
        ClasspathBeanDefinitionScanner beanScanner = new ClasspathBeanDefinitionScanner(beanFactory);
        beanScanner.doScan("jwp.core.annotation", "jwp.core.di");

        beanFactory.initialize();

        Map<Class<?>, Object> controllers = beanFactory.getControllers();
        assertThat(controllers.size()).isEqualTo(1);

        Object testController = controllers.get(TestControllerAnnotate.class);
        assertThat(testController).isNotNull();
        assertThat(testController).isInstanceOf(TestControllerAnnotate.class);

        Object cBean = beanFactory.getBean(C.class);
        assertThat(cBean).isNotNull();
        assertThat(cBean).isInstanceOf(C.class);

        Object fieldInjected = beanFactory.getBean(TestFieldInject.class);
        assertThat(fieldInjected).isNotNull();
        assertThat(fieldInjected).isInstanceOf(TestFieldInject.class);

        assertThat(((TestFieldInject)fieldInjected).getC()).isEqualTo(cBean);

        Object dBean = beanFactory.getBean(D.class);
        assertThat(dBean).isNotNull();
        assertThat(dBean).isInstanceOf(D.class);

        Object setterInjected = beanFactory.getBean(TestSetterInject.class);
        assertThat(setterInjected).isNotNull();
        assertThat(setterInjected).isInstanceOf(TestSetterInject.class);

        assertThat(((TestSetterInject)setterInjected).getD()).isEqualTo(dBean);
    }
}
