package jwp.core.mvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jwp.core.mvc.controller.AbstractController;

public class ForwardController extends AbstractController {
    private final String forwardUrl;

    public ForwardController(String forwardUrl) {
        if (forwardUrl == null)
            throw new NullPointerException("forwardUrl must not be null");

        this.forwardUrl = forwardUrl;
    }

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        return jspView(forwardUrl);
    }
}
