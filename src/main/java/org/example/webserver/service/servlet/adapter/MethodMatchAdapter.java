package org.example.webserver.service.servlet.adapter;

import org.example.webserver.annotations.RequestMapping;
import org.example.webserver.service.exception.NotFoundException;
import org.example.webserver.service.request.Request;
import org.example.webserver.service.response.Response;
import org.example.webserver.service.servlet.handlertype.QueryParamHandler;
import org.example.webserver.service.servlet.modelandview.ModelAndView;

import java.util.Arrays;

public class MethodMatchAdapter extends QueryParamAdapter {

    @Override
    public ModelAndView handle(Request request, Response response, Object handler) {
        if (!isMethodMatch(request, handler)) {
            throw new NotFoundException("No Handler For " + request.getMethod().name() + " Method on " + request.getRequestURI());
        }

        QueryParamHandler queryParamHandler = (QueryParamHandler) handler;

        return queryParamHandler.process(request.getBody());
    }

    private boolean isMethodMatch(Request request, Object handler) {
        return Arrays.stream(handler.getClass().getAnnotations())
                .filter(annotation -> annotation instanceof RequestMapping)
                .anyMatch(annotation -> ((RequestMapping) annotation).method().equals(request.getMethod()));
    }
}
