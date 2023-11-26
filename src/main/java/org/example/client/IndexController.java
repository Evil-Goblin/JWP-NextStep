package org.example.client;

import org.example.webserver.annotations.GetMapping;
import org.example.webserver.annotations.RequestMapping;
import org.example.webserver.service.servlet.ModelAndView;
import org.example.webserver.service.servlet.handlertype.QueryParamHandler;
import org.example.webserver.service.variable.HTTPMethod;

import java.util.Map;

@RequestMapping(method = HTTPMethod.GET)
public class IndexController implements QueryParamHandler {

    @Override
    public ModelAndView process(Map<String, String> queryParam) {
        return new ModelAndView("index.html");
    }
}
