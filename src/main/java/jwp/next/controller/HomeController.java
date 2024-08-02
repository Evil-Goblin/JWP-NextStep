package jwp.next.controller;

import jwp.core.db.DataBase;
import jwp.core.mvc.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HomeController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.setAttribute("users", DataBase.findAll());
        return "home.jsp";
    }
}
