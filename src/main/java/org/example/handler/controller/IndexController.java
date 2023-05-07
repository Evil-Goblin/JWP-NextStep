package org.example.handler.controller;

import org.example.handler.request.Request;
import org.example.handler.response.Response;
import org.example.webserver.WebServer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.example.handler.ContentType.TEXT_HTML_VALUE;
import static org.example.handler.StatusCode.CODE_200;
import static org.example.handler.StatusCode.CODE_404;
import static org.example.handler.response.FixedResponse.response500Error;

public class IndexController {

    public static void IndexPage(Request request, Response response) {
        try {
            File file = new File(WebServer.WEBAPP_PATH, "index.html");

            FileInputStream fileInputStream = new FileInputStream(file);
            int contentLength = fileInputStream.available();
            byte[] content = new byte[contentLength];
            fileInputStream.read(content);

            response.setVersion(request.getVersion());
            response.setStatusCode(CODE_200);
            response.setContentType(TEXT_HTML_VALUE);

            response.setContentLength(contentLength);
            response.setContent(content);
        } catch (IOException e) {
            response500Error(request, response);
        }
    }
}
