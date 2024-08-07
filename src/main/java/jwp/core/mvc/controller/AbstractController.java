package jwp.core.mvc.controller;

import jwp.core.mvc.ModelAndView;
import jwp.core.mvc.view.JsonView;
import jwp.core.mvc.view.JspView;

public abstract class AbstractController implements Controller {
    protected ModelAndView jspView(String forwardUrl) {
        return new ModelAndView(new JspView(forwardUrl));
    }

    protected ModelAndView jsonView() {
        return new ModelAndView(new JsonView());
    }
}
