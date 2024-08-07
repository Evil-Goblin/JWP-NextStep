package jwp.core.nmvc;

import jwp.core.mvc.ModelAndView;
import jwp.core.mvc.view.JsonView;
import jwp.core.mvc.view.JspView;

public abstract class AbstractNewController {
    protected ModelAndView jspView(String forwardUrl) {
        return new ModelAndView(new JspView(forwardUrl));
    }

    protected ModelAndView jsonView() {
        return new ModelAndView(new JsonView());
    }
}
