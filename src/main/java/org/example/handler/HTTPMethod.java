package org.example.handler;

public enum HTTPMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    HEAD("HEAD"),
    DELETE("DELETE"),
    CONNECT("CONNECT"),
    OPTIONS("OPTIONS"),
    TRACE("TRACE"),
    PATCH("PATCH");

    private final String MethodType;

    HTTPMethod(String methodType) {
        MethodType = methodType;
    }
}
