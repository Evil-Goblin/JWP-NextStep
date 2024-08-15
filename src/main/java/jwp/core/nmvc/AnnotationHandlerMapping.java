package jwp.core.nmvc;

import jakarta.servlet.http.HttpServletRequest;
import jwp.core.annotation.Controller;
import jwp.core.annotation.RequestMapping;
import jwp.core.annotation.RequestMethod;
import jwp.core.di.bean.ApplicationContext;
import lombok.extern.slf4j.Slf4j;
import org.reflections.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
public class AnnotationHandlerMapping implements HandlerMapping {

    private final Map<HandlerKey, HandlerExecution> handlerExecutions = new HashMap<>();
    private final ApplicationContext applicationContext;

    public AnnotationHandlerMapping(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void initialize() {
        Map<Class<?>, Object> controllers = getControllers(applicationContext);

        Set<Method> requestMappingMethods = getRequestMappingMethods(controllers.keySet());

        requestMappingMethods.forEach(method -> {
            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);

            log.debug("register handlerExecution : url is {}, method is {}", requestMapping.value(), method);

            handlerExecutions.put(createHandlerKey(requestMapping), new HandlerExecution(controllers.get(method.getDeclaringClass()), method));
        });

        log.info("Initialized AnnotationHandlerMapping!");
    }

    private HandlerKey createHandlerKey(RequestMapping requestMapping) {
        return new HandlerKey(requestMapping.value(), requestMapping.method());
    }

    private Set<Method> getRequestMappingMethods(Set<Class<?>> controllers) {
        HashSet<Method> requestMappingMethods = new HashSet<>();
        controllers.forEach(clazz -> requestMappingMethods.addAll(ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class))));
        return requestMappingMethods;
    }

    private Map<Class<?>, Object> getControllers(ApplicationContext ac) {
        Map<Class<?>, Object> controllers = new HashMap<>();
        for (Class<?> clazz : ac.getBeanClasses()) {
            Annotation annotation = clazz.getAnnotation(Controller.class);
            if (annotation != null) {
                controllers.put(clazz, ac.getBean(clazz));
            }
        }
        return controllers;
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        RequestMethod requestMethod = RequestMethod.valueOf(request.getMethod().toUpperCase());
        log.debug("requestUri : {}, requestMethod : {}", requestUri, requestMethod);
        return handlerExecutions.get(new HandlerKey(requestUri, requestMethod));
    }
}
