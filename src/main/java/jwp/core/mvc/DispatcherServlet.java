package jwp.core.mvc;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class DispatcherServlet extends HttpServlet {

    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private RequestMapping requestMapping;

    @Override
    public void init(ServletConfig config) throws ServletException {
        requestMapping = new RequestMapping();
        requestMapping.initMapping();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        log.debug("Method: {} URI: {}", req.getMethod(), requestURI);

        Controller controller = requestMapping.findController(requestURI);
        try {
            String viewName = controller.execute(req, resp);
            move(viewName, req, resp);
        } catch (Throwable throwable) {
            log.error("Exception : {}", throwable);
            throw new ServletException(throwable.getMessage());
        }
    }

    private void move(String viewName, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            resp.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
            return;
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher(viewName);
        requestDispatcher.forward(req, resp);
    }
}
