package org.example;

import org.example.client.IndexController;
import org.example.webserver.WebServer;
import org.example.webserver.WebServerBuilder;
import org.example.webserver.WebServerStarter;
import org.example.webserver.service.servlet.DispatcherServlet;
import org.example.webserver.service.servlet.adapter.MethodMatchAdapter;

public class Main {
    public static void main(String[] args) {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.addAdapter(new MethodMatchAdapter());
        dispatcherServlet.addHandler("/index.html", new IndexController());
        WebServerStarter.run(dispatcherServlet);
    }
}
