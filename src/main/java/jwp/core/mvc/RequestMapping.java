package jwp.core.mvc;

import jwp.next.controller.*;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RequestMapping {
    private Map<String, Controller> mapping = new HashMap<>();

    void initMapping() {
        mapping.put("/", new HomeController());
        mapping.put("/users/form", new ForwardController("/user/form.jsp"));
        mapping.put("/users/loginForm", new ForwardController("/user/login.jsp"));
        mapping.put("/users", new ListUserController());
        mapping.put("/users/login", new LoginController());
        mapping.put("/users/profile", new ProfileController());
        mapping.put("/users/logout", new LogoutController());
        mapping.put("/users/create", new CreateUserController());
        mapping.put("/users/updateForm", new UpdateFormUserController());
        mapping.put("/users/update", new UpdateUserController());
    }

    public Controller findController(String url) {
        return mapping.get(url);
    }

    void put(String url, Controller controller) {
        mapping.put(url, controller);
    }
}
