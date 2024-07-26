package org.example.webserver.service.servlet.modelandview;

import org.example.webserver.service.response.Response;
import org.example.webserver.service.variable.ContentType;
import org.example.webserver.service.variable.StatusCode;

public abstract class ModelAndView {

    private final ContentType contentType;
    private final StatusCode statusCode;

    public static ModelAndView of(String view) {
        ContentType contentType = ContentType.from(view);

        if (isRedirect(view)) {
            return new RedirectModelAndView(extractLocation(view), contentType);
        }
        return new CommonModelAndView(view, contentType);
    }

    private static String extractLocation(String view) {
        return view.substring("redirect:".length());
    }

    private static boolean isRedirect(String view) {
        return view.startsWith("redirect:");
    }

    public ModelAndView(ContentType contentType, StatusCode statusCode) {
        this.contentType = contentType;
        this.statusCode = statusCode;
    }

    public void loadView(Response response) {
        response.setStatusCode(statusCode);
        response.setContentType(contentType.getValue());

        processView(response);
    }

    abstract void processView(Response response);
}
