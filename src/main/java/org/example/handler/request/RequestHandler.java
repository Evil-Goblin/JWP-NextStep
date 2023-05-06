package org.example.handler.request;

import org.example.handler.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

import static org.example.handler.ContentType.TEXT_HTML_VALUE;
import static org.example.handler.StatusCode.CODE_404;
import static org.example.handler.StatusCode.CODE_500;
import static org.example.webserver.WebServer.CONTROLLER_MAP;

public class RequestHandler implements Runnable {

    public static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private final Socket connection;

    public RequestHandler(Socket connection) {
        this.connection = connection;
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response404Error(Request request, Response response) {
        response.setVersion(request.getVersion());
        response.setStatusCode(CODE_404);
        response.setContentType(TEXT_HTML_VALUE);
    }

    private void response500Error(Request request, Response response) {
        response.setVersion(request.getVersion());
        response.setStatusCode(CODE_500);
        response.setContentType(TEXT_HTML_VALUE);
    }

    @Override
    public void run() {
        log.info("New Client Connect! Connected Ip : {}, Port : {}",
                connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream()) {
            Request request = Request.requestParse(in);
            System.out.println("request = " + request);
            Response response = new Response();

//            Method controller = CONTROLLER_MAP.get(request.getUrl());
//            if (null != controller) {
//                try {
//                    controller.invoke(request, response);
//                } catch (IllegalAccessException | InvocationTargetException e) {
//                    // 500 error
//                    response500Error(request, response);
//                }
//            } else {
//                // 404 error
//                response404Error(request, response);
//            }

            // 위와 아래 어떤게 더 나은지 모르겠다...
            try {
                CONTROLLER_MAP.get(request.getUrl()).invoke(request, response);
            } catch (NullPointerException e) {
                // 404 error
                response404Error(request, response);
            } catch (IllegalAccessException | InvocationTargetException e) {
                // 500 error
                response500Error(request, response);
            }

            DataOutputStream dos = new DataOutputStream(out);
            response.write(dos);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
