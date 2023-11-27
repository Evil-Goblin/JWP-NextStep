package org.example.webserver.service.servlet;

import org.example.webserver.service.exception.InternalException;
import org.example.webserver.service.response.Response;
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
    private final Map<String, String> model = new HashMap<>();

    public ModelAndView(String view) {
        this.view = view;
    }

    public void loadView(Response response) {
        response.setStatusCode(StatusCode.OK);
        response.setContentType(TEXT_HTML_VALUE.getValue());
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
