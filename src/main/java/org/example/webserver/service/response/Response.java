package org.example.webserver.service.response;

import org.example.webserver.service.exception.InternalException;
import org.example.webserver.service.request.Request;
import org.example.webserver.service.variable.StatusCode;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.example.webserver.service.variable.ContentType.TEXT_HTML_VALUE;
import static org.example.webserver.service.variable.StatusCode.INTERNAL_SERVER_ERROR;
import static org.example.webserver.service.variable.StatusCode.NOT_FOUND;

public class Response {
    private final String version;
    private StatusCode statusCode;

    private String contentType;

    private int contentLength;
    private byte[] content;

    private final DataOutputStream outputStream;

    public Response(String version, OutputStream outputStream) {
        this.version = version;
        this.outputStream = new DataOutputStream(outputStream);
    }

    public void setStatusCode(StatusCode code) {
        statusCode = code;
    }

    public void setContent(byte[] content, int contentLength) {
        this.content = content;
        this.contentLength = contentLength;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void send() throws IOException {
        outputStream.writeBytes(String.format("%s %s\r\n", version, statusCode.getPhrase()));
        outputStream.writeBytes("Content-Type: " + contentType + "\r\n");
        outputStream.writeBytes("Content-Length: " + contentLength + "\r\n");
        outputStream.writeBytes("\r\n");
        outputStream.write(content, 0, contentLength);
        outputStream.writeBytes("\r\n");
        outputStream.flush();
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
