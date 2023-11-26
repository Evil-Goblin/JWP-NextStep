package org.example.webserver;

import org.example.webserver.service.servlet.DispatcherServlet;

public class WebServerStarter {
    public static void run(Class<?> entryPointClass, String... args) {
        String basePackageName = entryPointClass.getPackage().getName();
    }

    public static void run(DispatcherServlet dispatcherServlet) {
        WebServerBuilder webServerBuilder = new WebServerBuilder();
        webServerBuilder.setDispatcherServlet(dispatcherServlet);
        WebServer webServer = webServerBuilder.build();

        webServer.runServer();
    }
}
