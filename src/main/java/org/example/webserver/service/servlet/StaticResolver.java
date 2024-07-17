package org.example.webserver.service.servlet;

import org.example.webserver.service.exception.InternalException;
import org.example.webserver.service.exception.NotFoundException;

import java.net.URL;
import java.nio.file.Paths;

public class StaticResolver {

    public ModelAndView resolve(String requestURI) {
        String viewFile = Paths.get("webapp", requestURI).toString();
        URL resource = getClass().getClassLoader().getResource(viewFile);
        if (resource == null) {
            throw new NotFoundException("Resource not found: " + viewFile);
        }

        return ModelAndView.of(requestURI);
    }
}
