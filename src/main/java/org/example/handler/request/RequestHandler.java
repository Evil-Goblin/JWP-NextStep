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

import static org.example.handler.response.FixedResponse.response404Error;
import static org.example.handler.response.FixedResponse.response500Error;
import static org.example.webserver.WebServer.CONTROLLER_MAP;

public class RequestHandler implements Runnable {

    public static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static final ThreadLocal<RequestResolver> resolverThreadLocal = new ThreadLocal<>();
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

    @Override
    public void run() {
        log.info("New Client Connect! Connected Ip : {}, Port : {}",
                connection.getInetAddress(), connection.getPort());

        RequestResolver requestResolver = resolverThreadLocal.get();
        if (requestResolver == null) {
            requestResolver = new RequestResolver();
            resolverThreadLocal.set(requestResolver);
        }

        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream()) {

            requestResolver.parse(in);

            Request request = new Request.RequestBuilder()
                    .method(requestResolver.getMethod())
                    .url(requestResolver.getUrl())
                    .version(requestResolver.getVersion())
                    .accept(requestResolver.getProperties(RequestProperties.ACCEPT))
                    .contentType(requestResolver.getProperties(RequestProperties.CONTENT_TYPE))
                    .host(requestResolver.getProperties(RequestProperties.HOST))
                    .connection(requestResolver.getProperties(RequestProperties.CONNECTION))
                    .userAgent(requestResolver.getProperties(RequestProperties.USER_AGENT))
                    .acceptEncoding(requestResolver.getProperties(RequestProperties.ACCEPT_ENCODING))
                    .body(requestResolver.getBody())
                    .build();

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
                CONTROLLER_MAP.get(request.getUrl()).invoke(null, request, response);
            } catch (NullPointerException e) {
                // 404 error
                response404Error(request, response);
            } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
                // 500 error
                response500Error(request, response);
            }

            DataOutputStream dos = new DataOutputStream(out);
            response.write(dos);
            dos.flush();
        } catch (IOException e) {
            log.error("[RequestHandler] Error:", e);
        } finally {
            requestResolver.clear();
        }
    }
}
