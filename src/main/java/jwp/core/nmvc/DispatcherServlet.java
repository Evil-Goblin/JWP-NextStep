package jwp.core.nmvc;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jwp.core.di.bean.ApplicationContext;
import jwp.core.di.factory.AnnotationConfigApplicationContext;
import jwp.core.mvc.LegacyHandlerMapping;
import jwp.core.mvc.ModelAndView;
import jwp.core.mvc.controller.ControllerHandlerAdapter;
import jwp.next.config.MyConfiguration;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DispatcherServlet extends HttpServlet {

    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";
    private final List<HandlerMapping> mappings = new ArrayList<>();
    private final List<HandlerAdapter> handlerAdapters = new ArrayList<>();


    @Override
    public void init(ServletConfig config) throws ServletException {
        LegacyHandlerMapping legacyHandlerMapping = new LegacyHandlerMapping();
        legacyHandlerMapping.initMapping();

        ApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MyConfiguration.class);

        AnnotationHandlerMapping annotationHandlerMapping = new AnnotationHandlerMapping(annotationConfigApplicationContext);
        annotationHandlerMapping.initialize();

        mappings.add(legacyHandlerMapping);
        mappings.add(annotationHandlerMapping);

        handlerAdapters.add(new ControllerHandlerAdapter());
        handlerAdapters.add(new HandlerExecutionHandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object handler = getHandler(req);
        if (handler == null) {
            throw new IllegalArgumentException("No handler found for request: " + req.getRequestURI());
        }

        try {
            ModelAndView mv = execute(handler, req, resp);
            if (mv != null) {
                mv.getView().render(mv.getModel(), req, resp);
            }
        } catch (Exception e) {
            log.error("Exception: ", e);
            throw new ServletException(e);
        }
    }

    private Object getHandler(HttpServletRequest request) {
        for (HandlerMapping mapping : mappings) {
            Object handler = mapping.getHandler(request);
            if (handler != null) {
                return handler;
            }
        }
        return null;
    }

    private ModelAndView execute(Object handler, HttpServletRequest request, HttpServletResponse response) throws Exception {
        for (HandlerAdapter handlerAdapter : handlerAdapters) {
            if (handlerAdapter.supports(handler)) {
                return handlerAdapter.handle(request, response, handler);
            }
        }
        return null;
    }

    private void move(String viewName, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            resp.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
            return;
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher(viewName);
        requestDispatcher.forward(req, resp);
    }
}
