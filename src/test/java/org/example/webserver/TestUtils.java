package org.example.webserver;

import java.io.File;
import java.net.URL;

public class TestUtils {

    public File getResourceByFileName(String fileName) {
        URL resource = getClass().getClassLoader().getResource(fileName);
        if (resource == null) {
            throw new RuntimeException("Resource not found: " + fileName);
        }

        return new File(resource.getFile());
    }
}
