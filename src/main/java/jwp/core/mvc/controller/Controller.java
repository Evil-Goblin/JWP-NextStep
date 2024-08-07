package jwp.core.mvc.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jwp.core.mvc.ModelAndView;

public interface Controller {
    ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
