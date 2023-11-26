package org.example.webserver;

import org.example.webserver.configurer.WebServerPoolSizeConfigurer;
import org.example.webserver.configurer.WebServerPortConfigurer;
import org.example.webserver.service.request.RequestResolver;
import org.example.webserver.service.servlet.DispatcherServlet;

public class WebServerBuilder {
    private WebServerPoolSizeConfigurer poolSizeConfigurer = WebServerPoolSizeConfigurer.DEFAULT_POOL_SIZE_CONFIGURER;
    private WebServerPortConfigurer portConfigurer = WebServerPortConfigurer.DEFAULT_PORT_CONFIGURER;

    private DispatcherServlet dispatcherServlet;

    public void setDispatcherServlet(DispatcherServlet dispatcherServlet) {
        this.dispatcherServlet = dispatcherServlet;
    }

    public WebServer build() {
        return WebServer.builder()
                .poolSize(poolSizeConfigurer.getPoolSize())
                .port(portConfigurer.getPort())
                .requestResolver(new RequestResolver())
                .dispatcherServlet(dispatcherServlet)
                .build();
    }


}
