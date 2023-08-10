package org.example.webserver;

import org.example.handler.controller.IndexController;
import org.example.handler.request.Request;
import org.example.handler.request.RequestHandler;
import org.example.handler.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {

    public static final Logger log = LoggerFactory.getLogger(WebServer.class);

    public static final String  WEBAPP_PATH = "./src/main/webapp";
    public static final int DEFAULT_PORT = 8080;
    public static final int DEFAULT_POOL_SIZE = 50;
    public static Map<String, Method> CONTROLLER_MAP = new HashMap<>();

    public static void initializeController() {
        try {
            CONTROLLER_MAP.put("/index.html", IndexController.class.getMethod("IndexPage", Request.class, Response.class));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        int serverPort = DEFAULT_PORT;
        if (args != null && args.length != 0) {
            serverPort = Integer.parseInt(args[0]);
        }

        initializeController();

        ExecutorService executorService = Executors.newFixedThreadPool(DEFAULT_POOL_SIZE);

        try (ServerSocket listenSocket = new ServerSocket(serverPort)) {
            log.info("Web Application Server Started {} port.", serverPort);

            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                RequestHandler requestHandler = new RequestHandler(connection);
                executorService.submit(requestHandler);
            }
        }
    }
}
