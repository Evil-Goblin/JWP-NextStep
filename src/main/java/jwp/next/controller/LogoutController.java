package jwp.next.controller;

import jwp.core.mvc.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LogoutController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }
}
