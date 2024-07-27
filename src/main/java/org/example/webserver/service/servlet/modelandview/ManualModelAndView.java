package org.example.webserver.service.servlet.modelandview;

import org.example.webserver.service.response.Response;
import org.example.webserver.service.variable.ContentType;
import org.example.webserver.service.variable.StatusCode;

public class ManualModelAndView extends ModelAndView {

    private final byte[] content;

    public ManualModelAndView(ContentType contentType, StatusCode statusCode, String content) {
        super(contentType, statusCode);
        this.content = content.getBytes();
    }

    @Override
    void processView(Response response) {
        response.setContent(content, content.length);
    }
}
