package jwp.next.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jwp.core.mvc.ModelAndView;
import jwp.core.mvc.controller.AbstractController;
import jwp.next.controller.UserSessionUtils;

public class LogoutController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);
        return jspView("redirect:/");
    }
}
