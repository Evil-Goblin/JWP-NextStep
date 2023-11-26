package org.example.webserver.service.servlet;

import org.example.webserver.service.request.Request;
import org.example.webserver.service.response.Response;

public interface HandlerAdapter {
    boolean supports(Object handler);

    ModelAndView handle(Request request, Response response, Object handler);
}
