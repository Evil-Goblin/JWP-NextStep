package org.example.webserver.service.servlet.modelandview;

import org.example.webserver.service.response.Response;
import org.example.webserver.service.variable.ContentType;
import org.example.webserver.service.variable.StatusCode;

public class RedirectModelAndView extends ModelAndView {

    private final String location;

    public RedirectModelAndView(String location, ContentType contentType) {
        super(contentType, StatusCode.FOUND);
        this.location = location;
    }

    @Override
    void processView(Response response) {
        response.setLocation(location);
    }
}
