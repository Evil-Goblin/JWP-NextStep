package jwp.core.mvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ForwardController implements Controller {
    private final String forwardUrl;

    public ForwardController(String forwardUrl) {
        if (forwardUrl == null)
            throw new NullPointerException("forwardUrl must not be null");

        this.forwardUrl = forwardUrl;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        return forwardUrl;
    }
}
