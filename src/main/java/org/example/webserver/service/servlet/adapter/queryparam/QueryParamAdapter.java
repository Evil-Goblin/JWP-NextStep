package org.example.webserver.service.servlet.adapter.queryparam;

import org.example.webserver.service.request.Request;
import org.example.webserver.service.response.Response;
import org.example.webserver.service.servlet.adapter.MethodMatchAdapter;
import org.example.webserver.service.servlet.modelandview.ModelAndView;

public class QueryParamAdapter extends MethodMatchAdapter {

    @Override
    public boolean supports(Object handler) {
        return handler instanceof QueryParamHandler;
    }

    @Override
    public ModelAndView internalHandle(Request request, Response response, Object handler) {
        QueryParamHandler queryParamHandler = (QueryParamHandler) handler;

        return queryParamHandler.process(request.getBody());
    }
}
