package jwp.core.di.bean;

import jwp.core.di.TestControllerAnnotate;
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
        assertThat(scan.size()).isEqualTo(4);
    }

    @Test
    void beanFactoryTest() {
        BeanScanner beanScanner = new BeanScanner("jwp.core.di");
        Set<Class<?>> scan = beanScanner.scan();

        BeanFactory beanFactory = new BeanFactory(scan);
        beanFactory.initialize();

        Map<Class<?>, Object> controllers = beanFactory.getControllers();
        assertThat(controllers.size()).isEqualTo(1);

        Object testController = controllers.get(TestControllerAnnotate.class);
        assertThat(testController).isNotNull();
        assertThat(testController).isInstanceOf(TestControllerAnnotate.class);
    }

}
