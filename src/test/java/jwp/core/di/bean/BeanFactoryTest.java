package jwp.core.di.bean;

import jwp.core.di.*;
import jwp.core.di.inject.ConstructorInjector;
import jwp.core.di.inject.FieldInjector;
import jwp.core.di.inject.SetterInjector;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class BeanFactoryTest {

    @Test
    void beanScanTest() {
        // given
        BeanScanner beanScanner = new BeanScanner("jwp.core.di");

        // when
        Set<Class<?>> scan = beanScanner.scan();

        // then
        assertThat(scan.size()).isEqualTo(8);
    }

    @Test
    void beanFactoryTest() {
        BeanScanner beanScanner = new BeanScanner("jwp.core.di");
        Set<Class<?>> scan = beanScanner.scan();

        BeanFactory beanFactory = new BeanFactory(scan);
        beanFactory.addInjector(new ConstructorInjector(beanFactory));
        beanFactory.initialize();

        Map<Class<?>, Object> controllers = beanFactory.getControllers();
        assertThat(controllers.size()).isEqualTo(1);

        Object testController = controllers.get(TestControllerAnnotate.class);
        assertThat(testController).isNotNull();
        assertThat(testController).isInstanceOf(TestControllerAnnotate.class);
    }

    @Test
    void beanFieldInjectTest() {
        BeanScanner beanScanner = new BeanScanner("jwp.core.di");
        Set<Class<?>> scan = beanScanner.scan();

        BeanFactory beanFactory = new BeanFactory(scan);
        beanFactory.addInjector(new ConstructorInjector(beanFactory));
        beanFactory.addInjector(new FieldInjector(beanFactory));
        beanFactory.initialize();

        Object bean = beanFactory.getBean(C.class);
        assertThat(bean).isNotNull();
        assertThat(bean).isInstanceOf(C.class);

        Object fieldInjected = beanFactory.getBean(TestFieldInject.class);
        assertThat(fieldInjected).isNotNull();
        assertThat(fieldInjected).isInstanceOf(TestFieldInject.class);

        assertThat(((TestFieldInject)fieldInjected).getC()).isEqualTo(bean);
    }

    @Test
    void beanSetterInjectTest() {
        BeanScanner beanScanner = new BeanScanner("jwp.core.di");
        Set<Class<?>> scan = beanScanner.scan();

        BeanFactory beanFactory = new BeanFactory(scan);
        beanFactory.addInjector(new ConstructorInjector(beanFactory));
        beanFactory.addInjector(new SetterInjector(beanFactory));
        beanFactory.initialize();

        Object bean = beanFactory.getBean(D.class);
        assertThat(bean).isNotNull();
        assertThat(bean).isInstanceOf(D.class);

        Object setterInjected = beanFactory.getBean(TestSetterInject.class);
        assertThat(setterInjected).isNotNull();
        assertThat(setterInjected).isInstanceOf(TestSetterInject.class);

        assertThat(((TestSetterInject)setterInjected).getD()).isEqualTo(bean);
    }
}
