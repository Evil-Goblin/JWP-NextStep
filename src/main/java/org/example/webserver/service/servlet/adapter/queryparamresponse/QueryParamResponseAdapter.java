package org.example.webserver.service.servlet.adapter.queryparamresponse;

import org.example.webserver.service.request.Request;
import org.example.webserver.service.response.Response;
import org.example.webserver.service.servlet.adapter.MethodMatchAdapter;
import org.example.webserver.service.servlet.modelandview.ModelAndView;

public class QueryParamResponseAdapter extends MethodMatchAdapter {
    @Override
    public ModelAndView internalHandle(Request request, Response response, Object handler) {
        QueryParamResponseHandler queryParamResponseHandler = (QueryParamResponseHandler) handler;

        return queryParamResponseHandler.process(request.getBody(), response);
    }

    @Override
    public boolean supports(Object handler) {
        return handler instanceof QueryParamResponseHandler;
    }
}
