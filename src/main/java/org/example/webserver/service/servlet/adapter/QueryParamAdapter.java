package org.example.webserver.service.servlet.adapter;

import org.example.webserver.service.servlet.HandlerAdapter;
import org.example.webserver.service.servlet.handlertype.QueryParamHandler;

public abstract class QueryParamAdapter implements HandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return handler instanceof QueryParamHandler;
    }
}
