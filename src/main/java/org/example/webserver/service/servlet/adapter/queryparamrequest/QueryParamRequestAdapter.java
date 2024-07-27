package org.example.webserver.service.servlet.adapter.queryparamrequest;

import org.example.webserver.service.request.Request;
import org.example.webserver.service.response.Response;
import org.example.webserver.service.servlet.adapter.MethodMatchAdapter;
import org.example.webserver.service.servlet.modelandview.ModelAndView;

public class QueryParamRequestAdapter extends MethodMatchAdapter {
    @Override
    public ModelAndView internalHandle(Request request, Response response, Object handler) {
        QueryParamRequestHandler queryParamRequestHandler = (QueryParamRequestHandler) handler;

        return queryParamRequestHandler.process(request.getBody(), request);
    }

    @Override
    public boolean supports(Object handler) {
        return handler instanceof QueryParamRequestHandler;
    }
}
