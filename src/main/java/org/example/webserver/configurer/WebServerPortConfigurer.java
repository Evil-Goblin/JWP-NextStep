package org.example.webserver.configurer;

public class WebServerPortConfigurer {
    public static final int DEFAULT_PORT = 8080;

    private int port = DEFAULT_PORT;

    public WebServerPortConfigurer getPortConfigurer(int port) {
        return new WebServerPortConfigurer(port);
    }

    public static final WebServerPortConfigurer DEFAULT_PORT_CONFIGURER = new WebServerPortConfigurer(DEFAULT_PORT);


    private WebServerPortConfigurer(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }
}
