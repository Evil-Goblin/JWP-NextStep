package jwp.core.ref;

import jwp.next.model.Question;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());

        Arrays.stream(clazz.getDeclaredFields())
                .forEach(System.out::println);
        Arrays.stream(clazz.getDeclaredMethods())
                .forEach(System.out::println);
        Arrays.stream(clazz.getDeclaredConstructors())
                .forEach(System.out::println);
    }
}
