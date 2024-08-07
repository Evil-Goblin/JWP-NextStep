package jwp.core.nmvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jwp.core.annotation.Controller;
import jwp.core.annotation.RequestMapping;
import jwp.core.mvc.ModelAndView;

@Controller
public class TestController {

    @RequestMapping("/users/findUserId")
    public ModelAndView findUserId(HttpServletRequest req, HttpServletResponse resp) {
        return null;
    }
}
