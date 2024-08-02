package jwp.next.controller;

import jwp.core.db.DataBase;
import jwp.core.mvc.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ListUserController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return "redirect:/users/loginForm";
        }

        req.setAttribute("users", DataBase.findAll());
        return "/user/list.jsp";
    }
}
