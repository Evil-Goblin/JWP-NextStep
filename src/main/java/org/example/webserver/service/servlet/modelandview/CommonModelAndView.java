package org.example.webserver.service.servlet.modelandview;

import org.example.webserver.service.exception.InternalException;
import org.example.webserver.service.response.Response;
import org.example.webserver.service.variable.ContentType;
import org.example.webserver.service.variable.StatusCode;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class CommonModelAndView extends ModelAndView {
    private final String view;
    private final Map<String, String> model = new HashMap<>();

    public CommonModelAndView(String view, ContentType contentType) {
        super(contentType, StatusCode.OK);
        this.view = view;
    }

    @Override
    void processView(Response response) {
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
