package org.example.webserver.service.servlet.adapter.queryparamresponse;

import org.example.webserver.service.response.Response;
import org.example.webserver.service.servlet.modelandview.ModelAndView;

import java.util.Map;

public interface QueryParamResponseHandler {
    ModelAndView process(Map<String, String> queryParam, Response response);
}
