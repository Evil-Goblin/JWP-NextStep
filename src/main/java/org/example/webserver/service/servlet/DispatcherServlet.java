package org.example.webserver.service.servlet;

import org.example.webserver.service.exception.NotFoundException;
import org.example.webserver.service.request.Request;
import org.example.webserver.service.response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DispatcherServlet {

    private final List<HandlerAdapter> handlerAdaptor = new ArrayList<>();
    private final Map<String, Object> handlerMapping = new HashMap<>();

    private final StaticResolver staticResolver;

    public DispatcherServlet(StaticResolver staticResolver) {
        this.staticResolver = staticResolver;
    }

    public ModelAndView doDispatcher(Request request, Response response) {
        String requestURI = request.getRequestURI();

        Object handler = handlerMapping.get(requestURI);
        if (handler == null) {
            // static resolver
            return staticResolver.resolve(requestURI);
        }

        HandlerAdapter handleAdapter = getHandleAdapter(handler);
        return handleAdapter.handle(request, response, handler);
    }

    private HandlerAdapter getHandleAdapter(Object handler) {
        for (HandlerAdapter handlerAdapter : handlerAdaptor) {
            if (handlerAdapter.supports(handler)) {
                return handlerAdapter;
            }
        }
        throw new NotFoundException("No Adapter for Servlet");
    }

    public void addAdapter(HandlerAdapter adapter) {
        handlerAdaptor.add(adapter);
    }

    public void addHandler(String path, Object handler) {
        if (handlerMapping.containsKey(path)) {
            throw new IllegalArgumentException("Duplicate Path");
        }

        handlerMapping.put(path, handler);
    }
}
