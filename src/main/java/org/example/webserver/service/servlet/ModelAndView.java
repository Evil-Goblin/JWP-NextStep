package org.example.webserver.service.servlet;

import org.example.webserver.service.exception.InternalException;
import org.example.webserver.service.response.Response;
import org.example.webserver.service.variable.ContentType;
import org.example.webserver.service.variable.StatusCode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.example.webserver.service.variable.ContentType.TEXT_HTML_VALUE;

public class ModelAndView {
    private final String view;
    private final ContentType contentType;
    private final StatusCode statusCode;
    private final Map<String, String> model = new HashMap<>();

    public static ModelAndView of(String view) {
        ContentType contentType = ContentType.from(view);
        StatusCode statusCode = StatusCode.isRedirect(view);
        return new ModelAndView(view, contentType, statusCode);
    }

    public ModelAndView(String view, ContentType contentType, StatusCode statusCode) {
        this.view = view;
        this.contentType = contentType;
        this.statusCode = statusCode;
    }

    public void loadView(Response response) {
        response.setStatusCode(statusCode);
        response.setContentType(contentType.getValue());
        String viewFile = Paths.get("webapp", view).toString();
        URL resource = getClass().getClassLoader().getResource(viewFile);
        if (resource == null) {
            throw new InternalException("Invalid Page");
        }

        try {
            File file = new File(resource.toURI());
            setContentFromFile(response, file);

        } catch (URISyntaxException e) {
            throw new InternalException(e);
        }
    }

    private void setContentFromFile(Response response, File file) {
        try {
            byte[] body = Files.readAllBytes(file.toPath());
            response.setContent(body, body.length);
        } catch (IOException e) {
            throw new InternalException(e);
        }
    }
}
