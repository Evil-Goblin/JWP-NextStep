package jwp.core.nmvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jwp.core.mvc.ModelAndView;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class HandlerExecution {
    private final Object declaredObject;
    private final Method method;

    public HandlerExecution(Object declaredObject, Method method) {
        this.declaredObject = declaredObject;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest req, HttpServletResponse resp) {
        try {
            return (ModelAndView) method.invoke(declaredObject, req, resp);
        } catch (InvocationTargetException | IllegalAccessException e ) {
            log.error("{} method invoke fail.", method, e);
            throw new RuntimeException(e);
        }
    }
}
