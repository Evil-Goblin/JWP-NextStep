package org.example.webserver.service.servlet.adapter.queryparam;

import org.example.webserver.service.servlet.modelandview.ModelAndView;

import java.util.Map;

public interface QueryParamHandler {
    ModelAndView process(Map<String, String> queryParam);
}
