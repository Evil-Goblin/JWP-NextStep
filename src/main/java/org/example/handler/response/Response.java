package org.example.handler.response;

import lombok.Setter;
import lombok.ToString;

import java.io.DataOutputStream;
import java.io.IOException;

@Setter
@ToString
public class Response {
    String version;
    String statusCode;

    String contentType;

    int contentLength;
    byte[] content;

    public void write(DataOutputStream dos) throws IOException {
        dos.writeBytes(String.format("%s %s\r\n", this.version, this.statusCode));
        dos.writeBytes("Content-Type: " + this.contentType + "\r\n");
        dos.writeBytes("Content-Length: " + this.contentLength + "\r\n");
        dos.writeBytes("\r\n");
        dos.write(this.content, 0, this.contentLength);
        dos.writeBytes("\r\n");
    }
}
