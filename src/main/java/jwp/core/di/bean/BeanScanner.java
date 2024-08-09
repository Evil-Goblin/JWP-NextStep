package jwp.core.di.bean;

import jwp.core.annotation.Component;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class BeanScanner {
    public static final String ANNOTATION_PACKAGE = "jwp.core.annotation";
    private final Reflections reflections;

    public BeanScanner(Object... basePackages) {
        reflections = new Reflections(ANNOTATION_PACKAGE, basePackages);
    }

    public Set<Class<?>> scan() {
        Set<Class<?>> classes = new HashSet<>();
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Component.class);
        for (Class<?> clazz : typesAnnotatedWith) {
            if (!clazz.isAnnotation()) {
                classes.add(clazz);
            }
        }
        return classes;
    }
}
