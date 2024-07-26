package org.example.webserver.service;

import lombok.extern.slf4j.Slf4j;
import org.example.webserver.service.exception.InternalException;
import org.example.webserver.service.exception.NotFoundException;
import org.example.webserver.service.request.Request;
import org.example.webserver.service.request.RequestResolver;
import org.example.webserver.service.response.Response;
import org.example.webserver.service.servlet.DispatcherServlet;
import org.example.webserver.service.servlet.modelandview.ModelAndView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

@Slf4j
public class RequestProcessor implements Runnable {

    private final Socket connection;
    private final RequestResolver requestResolver;

    private final DispatcherServlet dispatcherServlet;

    public RequestProcessor(Socket connection, RequestResolver requestResolver, DispatcherServlet dispatcherServlet) {
        this.connection = connection;
        this.requestResolver = requestResolver;
        this.dispatcherServlet = dispatcherServlet;
    }

    @Override
    public void run() {
        try (InputStream inputStream = connection.getInputStream();
             OutputStream outputStream = connection.getOutputStream()) {
            Request request = requestResolver.parse(inputStream);
            Response response = new Response(request.getVersion(), outputStream);

            processServlet(request, response);
        } catch (IOException e) {
            log.error("Servlet Failed", e);
        }
    }

    private void processServlet(Request request, Response response) throws IOException {
        try {
            ModelAndView modelAndView = dispatcherServlet.doDispatcher(request, response);
            modelAndView.loadView(response);

        } catch (InternalException e) {
            response.response500Error(request);
        } catch (NotFoundException e) {
            response.response400Error(request);
        }

        response.send();
    }
}
