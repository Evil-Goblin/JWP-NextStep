package org.example.webserver.service.response;

import org.example.webserver.service.exception.InternalException;
import org.example.webserver.service.request.Request;
import org.example.webserver.service.variable.StatusCode;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static org.example.webserver.service.variable.ContentType.TEXT_HTML_VALUE;
import static org.example.webserver.service.variable.StatusCode.INTERNAL_SERVER_ERROR;
import static org.example.webserver.service.variable.StatusCode.NOT_FOUND;

public class Response {
    private final String version;
    private StatusCode statusCode;

    private String contentType;

    private int contentLength;
    private byte[] content;

    private boolean isRedirect;
    private String location;

    private final Map<String, String> cookies = new HashMap<>();

    private final DataOutputStream outputStream;

    public Response(String version, OutputStream outputStream) {
        this.version = version;
        this.isRedirect = false;
        this.outputStream = new DataOutputStream(outputStream);
    }

    public void setStatusCode(StatusCode code) {
        statusCode = code;
    }

    public void setContent(byte[] content, int contentLength) {
        this.content = content;
        this.contentLength = contentLength;
    }

    public void setLocation(String location) {
        this.isRedirect = true;
        this.location = location;
    }

    public void setCookie(String key, String value) {
        cookies.put(key, value);
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void send() throws IOException {
        outputStream.writeBytes(String.format("%s %s\r\n", version, statusCode.getPhrase()));

        fillContent();

        outputStream.flush();
    }

    private void fillContent() throws IOException {
        if (isRedirect) {
            outputStream.writeBytes("Location: " + location);
        } else {
            outputStream.writeBytes("Content-Type: " + contentType + "\r\n");

            for (Map.Entry<String, String> entry : cookies.entrySet()) {
                outputStream.writeBytes("Set-Cookie: " + entry.getKey() + "=" + entry.getValue() + "\n");
            }

            outputStream.writeBytes("Content-Length: " + contentLength + "\r\n");
            outputStream.writeBytes("\r\n");
            outputStream.write(content, 0, contentLength);
            outputStream.writeBytes("\r\n");
        }
    }

    public void response500Error(Request request) {
        setStatusCode(INTERNAL_SERVER_ERROR);
        setContentType(TEXT_HTML_VALUE.getValue());
        setContent(new byte[0], 0);
    }

    public void response400Error(Request request) {
        setStatusCode(NOT_FOUND);
        setContentType(TEXT_HTML_VALUE.getValue());
        setContent(new byte[0], 0);
    }
}
