package org.example.webserver.service.request;

import lombok.Builder;
import lombok.Getter;
import org.example.webserver.service.variable.HTTPMethod;

import java.util.Map;

@Getter
@Builder
public class Request {
    HTTPMethod method;
    RequestUrl requestUrl;
    String pathVariable;
    String version;

    String accept;
    String contentType;
    String host;
    String connection;
    String userAgent;
    String acceptEncoding;

    Map<String, Integer> sec_ch_ua;

    String body;

    public String getRequestURI() {
        return requestUrl.getUri();
    }
    public Map<String, String> getQueryParam() {
        return requestUrl.getQueryParam();}
}
