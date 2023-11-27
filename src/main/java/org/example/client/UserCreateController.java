package org.example.client;

import lombok.extern.slf4j.Slf4j;
import org.example.webserver.annotations.RequestMapping;
import org.example.webserver.service.servlet.ModelAndView;
import org.example.webserver.service.servlet.handlertype.QueryParamHandler;
import org.example.webserver.service.variable.HTTPMethod;

import java.util.Map;

@Slf4j
@RequestMapping(method = HTTPMethod.GET)
public class UserCreateController implements QueryParamHandler {
    @Override
    public ModelAndView process(Map<String, String> queryParam) {
        String userId = queryParam.get("userId");
        String password = queryParam.get("password");
        String name = queryParam.get("name");
        String email = queryParam.get("email");

        log.info("[UserCreateController] userId: {}", userId);
        log.info("[UserCreateController] password: {}", password);
        log.info("[UserCreateController] name: {}", name);
        log.info("[UserCreateController] email: {}", email);

        return new ModelAndView("index.html");
    }
}
