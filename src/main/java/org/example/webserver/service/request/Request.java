package org.example.webserver.service.request;

import lombok.Builder;
import lombok.Getter;
import org.example.webserver.service.variable.ContentType;
import org.example.webserver.service.variable.HTTPMethod;
import org.example.webserver.util.parameterresolver.JsonResolver;
import org.example.webserver.util.parameterresolver.QueryParamResolver;

import java.util.Collections;
import java.util.Map;

@Getter
public class Request {
    HTTPMethod method;
    RequestUrl requestUrl;
    String pathVariable;
    String version;

    String accept;
    ContentType contentType;
    String host;
    String connection;
    String userAgent;
    String acceptEncoding;

    Map<String, Integer> sec_ch_ua;

    Map<String, String> body;

    @Builder
    public Request(HTTPMethod method, RequestUrl requestUrl, String pathVariable, String version, String accept, String contentType, String host, String connection, String userAgent, String acceptEncoding, Map<String, Integer> sec_ch_ua, String body) {
        this.method = method;
        this.requestUrl = requestUrl;
        this.pathVariable = pathVariable;
        this.version = version;
        this.accept = accept;
        this.contentType = ContentType.of(contentType);
        this.host = host;
        this.connection = connection;
        this.userAgent = userAgent;
        this.acceptEncoding = acceptEncoding;
        this.sec_ch_ua = sec_ch_ua;

        if (method == HTTPMethod.POST) {
            this.body = switch (this.contentType) {
                case APPLICATION_FORM_URLENCODED_VALUE -> QueryParamResolver.parseQueryParam(body);
                case APPLICATION_JSON_VALUE -> JsonResolver.parseJsonParam(body);
                default -> Collections.emptyMap();
            };
        } else {
            this.body = requestUrl.getQueryParam();
        }
    }

    public String getRequestURI() {
        return requestUrl.getUri();
    }
}
