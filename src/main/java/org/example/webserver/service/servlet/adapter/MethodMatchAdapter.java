package org.example.webserver.service.servlet.adapter;

import org.example.webserver.annotations.RequestMapping;
import org.example.webserver.service.exception.NotFoundException;
import org.example.webserver.service.request.Request;
import org.example.webserver.service.response.Response;
import org.example.webserver.service.servlet.HandlerAdapter;
import org.example.webserver.service.servlet.modelandview.ModelAndView;

import java.util.Arrays;

public abstract class MethodMatchAdapter implements HandlerAdapter {
    @Override
    public ModelAndView handle(Request request, Response response, Object handler) {
        if (!isMethodMatch(request, handler)) {
            throw new NotFoundException("No Handler For " + request.getMethod().name() + " Method on " + request.getRequestURI());
        }

        return internalHandle(request, response, handler);
    }

    private boolean isMethodMatch(Request request, Object handler) {
        return Arrays.stream(handler.getClass().getAnnotations())
                .filter(annotation -> annotation instanceof RequestMapping)
                .anyMatch(annotation -> ((RequestMapping) annotation).method().equals(request.getMethod()));
    }

    abstract public ModelAndView internalHandle(Request request, Response response, Object handler);
}
