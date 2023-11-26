package org.example.webserver.configurer;

public class WebServerPoolSizeConfigurer {

    public WebServerPoolSizeConfigurer getPoolSizeConfigurer(int poolSize) {
        return new WebServerPoolSizeConfigurer(poolSize);
    }

    private static final int DEFAULT_POOL_SIZE = 50;

    public static final WebServerPoolSizeConfigurer DEFAULT_POOL_SIZE_CONFIGURER = new WebServerPoolSizeConfigurer(DEFAULT_POOL_SIZE);

    private final int poolSize;

    private WebServerPoolSizeConfigurer(int poolSize) {
        this.poolSize = poolSize;
    }

    public int getPoolSize() {
        return poolSize;
    }
}
