package org.example.handler.response;

import org.example.handler.request.Request;

import static org.example.handler.ContentType.TEXT_HTML_VALUE;
import static org.example.handler.StatusCode.CODE_404;
import static org.example.handler.StatusCode.CODE_500;

public class FixedResponse {

    public static void response404Error(Request request, Response response) {
        response.setVersion(request.getVersion());
        response.setStatusCode(CODE_404);
        response.setContentType(TEXT_HTML_VALUE);
        response.setContentLength(0);
    }

    public static void response500Error(Request request, Response response) {
        response.setVersion(request.getVersion());
        response.setStatusCode(CODE_500);
        response.setContentType(TEXT_HTML_VALUE);
        response.setContentLength(0);
    }
}
