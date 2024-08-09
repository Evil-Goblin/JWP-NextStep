package jwp.core.nmvc;

import jakarta.servlet.http.HttpServletRequest;
import jwp.core.annotation.RequestMapping;
import jwp.core.annotation.RequestMethod;
import jwp.core.di.bean.BeanFactory;
import jwp.core.di.bean.BeanScanner;
import lombok.extern.slf4j.Slf4j;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
public class AnnotationHandlerMapping implements HandlerMapping {

    private final Object[] basePackage;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions = new HashMap<>();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        BeanScanner beanScanner = new BeanScanner("jwp.next");
        BeanFactory beanFactory = new BeanFactory(beanScanner.scan());
        beanFactory.initialize();

        Map<Class<?>, Object> controllers = beanFactory.getControllers();

        Set<Method> requestMappingMethods = getRequestMappingMethods(controllers.keySet());

        requestMappingMethods.forEach(method -> {
            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);

            log.debug("register handlerExecution : url is {}, method is {}", requestMapping.value(), method);

            handlerExecutions.put(createHandlerKey(requestMapping), new HandlerExecution(controllers.get(method.getDeclaringClass()), method));
        });
    }

    private HandlerKey createHandlerKey(RequestMapping requestMapping) {
        return new HandlerKey(requestMapping.value(), requestMapping.method());
    }

    private Set<Method> getRequestMappingMethods(Set<Class<?>> controllers) {
        HashSet<Method> requestMappingMethods = new HashSet<>();
        controllers.forEach(clazz -> requestMappingMethods.addAll(ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class))));
        return requestMappingMethods;
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        RequestMethod requestMethod = RequestMethod.valueOf(request.getMethod().toUpperCase());
        log.debug("requestUri : {}, requestMethod : {}", requestUri, requestMethod);
        return handlerExecutions.get(new HandlerKey(requestUri, requestMethod));
    }
}
