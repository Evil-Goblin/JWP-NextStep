package org.example.client.controller;

import lombok.RequiredArgsConstructor;
import org.example.client.model.user.User;
import org.example.client.model.user.UserService;
import org.example.webserver.annotations.RequestMapping;
import org.example.webserver.service.response.Response;
import org.example.webserver.service.servlet.adapter.queryparamresponse.QueryParamResponseHandler;
import org.example.webserver.service.servlet.modelandview.ModelAndView;
import org.example.webserver.service.variable.HTTPMethod;
import org.example.webserver.util.assertion.Assert;

import java.util.Map;

@RequiredArgsConstructor
@RequestMapping(method = HTTPMethod.POST)
public class UserLoginController implements QueryParamResponseHandler {

    private final UserService userService;

    @Override
    public ModelAndView process(Map<String, String> queryParam, Response response) {
        String userId = queryParam.get("userId");
        String password = queryParam.get("password");

        try {
            Assert.notNull(userId, "userId must not be null");
            Assert.notNull(password, "password must not be null");

            User user = userService.findByUserId(userId);
            user.passwordValidation(password);
        } catch (RuntimeException e) {
            response.setCookie("logined", "false");
            return ModelAndView.of("user/login_failed.html");
        }

        response.setCookie("logined", "true");
        return ModelAndView.of("index.html");
    }
}
