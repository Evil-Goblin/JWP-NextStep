package jwp.core.mvc.view;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

public class JspView implements View {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private final String viewName;

    public JspView(String viewName) {
        if (viewName == null) throw new NullPointerException("viewName is null");
        this.viewName = viewName;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            resp.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
            return;
        }

        for (String key: model.keySet()) {
            req.setAttribute(key, model.get(key));
        }

        req.getRequestDispatcher(viewName).forward(req, resp);
    }
}
