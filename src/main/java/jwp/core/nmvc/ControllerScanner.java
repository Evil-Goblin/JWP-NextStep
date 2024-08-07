package jwp.core.nmvc;

import jwp.core.annotation.Controller;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class ControllerScanner {
    private final Reflections reflections;

    public ControllerScanner(Object... basePackages) {
        this.reflections = new Reflections(basePackages);
    }

    public Map<Class<?>, Object> getControllers() {
        Set<Class<?>> preInitiatedControllers = reflections.getTypesAnnotatedWith(Controller.class);

        return instantiateControllers(preInitiatedControllers);
    }

    private Map<Class<?>, Object> instantiateControllers(Set<Class<?>> preInitiatedControllers) {
        Map<Class<?>, Object> controllers = new HashMap<>();
        preInitiatedControllers
                .forEach(clazz -> {
                    try {
                        controllers.put(clazz, clazz.getConstructor().newInstance());
                    } catch (Exception e) {
                        log.error("Initialize Controller Error", e);
                        throw new RuntimeException(e);
                    }
                });
        return controllers;
    }
}
