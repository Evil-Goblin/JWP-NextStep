package jwp.next.controller;

import jwp.core.db.DataBase;
import jwp.core.mvc.Controller;
import jwp.next.model.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ProfileController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userId = req.getParameter("userId");
        User user = DataBase.findUserById(userId);
        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }
        req.setAttribute("user", user);
        return "/user/profile.jsp";
    }
}
