package jwp.next.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jwp.core.db.DataBase;
import jwp.core.mvc.ModelAndView;
import jwp.core.mvc.controller.AbstractController;
import jwp.next.model.User;

public class ProfileController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userId = req.getParameter("userId");
        User user = DataBase.findUserById(userId);
        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }
        return jspView("/user/profile.jsp").addObject("user", user);
    }
}
