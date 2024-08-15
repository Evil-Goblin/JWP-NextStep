package jwp.core.di.bean;

import jwp.core.di.bean.test.TestConfigure;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

class AnnotatedBeanDefinitionReaderTest {

    @Test
    void register_simple() {
        BeanFactory beanFactory = new BeanFactory();
        AnnotatedBeanDefinitionReader annotatedBeanDefinitionReader = new AnnotatedBeanDefinitionReader(beanFactory);
        annotatedBeanDefinitionReader.register(TestConfigure.class);

        beanFactory.initialize();

        Assertions.assertThat(beanFactory.getBean(DataSource.class)).isNotNull();
    }

}
