package org.example.webserver.service.servlet.adapter.queryparamrequest;

import org.example.webserver.service.request.Request;
import org.example.webserver.service.servlet.modelandview.ModelAndView;

import java.util.Map;

public interface QueryParamRequestHandler {
    ModelAndView process(Map<String, String> queryParam, Request request);
}
