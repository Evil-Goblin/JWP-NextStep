package jwp.next.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jwp.core.db.DataBase;
import jwp.core.mvc.ModelAndView;
import jwp.core.mvc.controller.AbstractController;
import jwp.next.controller.UserSessionUtils;
import jwp.next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateUserController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(UpdateUserController.class);

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = DataBase.findUserById(req.getParameter("userId"));
        if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }

        User updateUser = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"),
                req.getParameter("email"));
        log.debug("Update User : {}", updateUser);
        user.update(updateUser);

        return jspView("redirect:/");
    }
}
