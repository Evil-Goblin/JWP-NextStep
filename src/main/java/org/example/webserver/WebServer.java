package org.example.webserver;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.example.webserver.service.RequestProcessor;
import org.example.webserver.service.request.RequestResolver;
import org.example.webserver.service.servlet.DispatcherServlet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class WebServer {
    private final int port;

    private final ExecutorService executorService;

    private final RequestResolver requestResolver;

    private final DispatcherServlet dispatcherServlet;

    @Builder
    public WebServer(int port, int poolSize, RequestResolver requestResolver, DispatcherServlet dispatcherServlet) {
        this.port = port;
        this.executorService = Executors.newFixedThreadPool(poolSize);
        this.requestResolver = requestResolver;
        this.dispatcherServlet = dispatcherServlet;
    }

    public void runServer() {
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            log.info("Web Application Server Started {} port.", port);
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                RequestProcessor requestProcess = new RequestProcessor(connection, requestResolver, dispatcherServlet);
                executorService.submit(requestProcess);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
