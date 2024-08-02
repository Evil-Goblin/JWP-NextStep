package jwp.next.controller;

import jwp.core.db.DataBase;
import jwp.core.mvc.Controller;
import jwp.next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CreateUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"),
                req.getParameter("email"));
        log.debug("User : {}", user);

        DataBase.addUser(user);
        return "redirect:/";
    }
}
