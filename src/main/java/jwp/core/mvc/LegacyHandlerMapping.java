package jwp.core.mvc;

import jakarta.servlet.http.HttpServletRequest;
import jwp.core.mvc.controller.Controller;
import jwp.core.nmvc.HandlerMapping;
import jwp.next.controller.*;
import jwp.next.controller.qna.AddAnswerController;
import jwp.next.controller.qna.DeleteAnswerController;
import jwp.next.controller.qna.ShowController;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LegacyHandlerMapping implements HandlerMapping {
    private Map<String, Controller> mapping = new HashMap<>();

    public void initMapping() {
        mapping.put("/", new HomeController());
//        mapping.put("/users/form", new ForwardController("/user/form.jsp"));
//        mapping.put("/users/loginForm", new ForwardController("/user/login.jsp"));
//        mapping.put("/users", new ListUserController());
//        mapping.put("/users/login", new LoginController());
//        mapping.put("/users/profile", new ProfileController());
//        mapping.put("/users/logout", new LogoutController());
//        mapping.put("/users/create", new CreateUserController());
//        mapping.put("/users/updateForm", new UpdateFormUserController());
//        mapping.put("/users/update", new UpdateUserController());
        mapping.put("/qna/show", new ShowController());
        mapping.put("/api/qna/addAnswer", new AddAnswerController());
        mapping.put("/api/qna/deleteAnswer", new DeleteAnswerController());

        log.info("Initialized Request Mapping");
    }

    public Controller findController(String url) {
        return mapping.get(url);
    }

    void put(String url, Controller controller) {
        mapping.put(url, controller);
    }

    @Override
    public Object getHandler(HttpServletRequest request) {
        return mapping.get(request.getRequestURI());
    }
}
