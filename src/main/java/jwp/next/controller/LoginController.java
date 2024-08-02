package jwp.next.controller;

import jwp.core.db.DataBase;
import jwp.core.mvc.Controller;
import jwp.next.model.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        User user = DataBase.findUserById(userId);
        if (user == null) {
            req.setAttribute("loginFailed", true);
            return "/user/login.jsp";
        }
        if (user.matchPassword(password)) {
            HttpSession session = req.getSession();
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
            return "redirect:/";
        } else {
            req.setAttribute("loginFailed", true);
            return "/user/login.jsp";
        }
    }
}
