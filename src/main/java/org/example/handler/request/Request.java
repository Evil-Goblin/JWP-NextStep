package org.example.handler.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.example.handler.HTTPMethod;

import java.util.Map;

@Getter
@Builder
@ToString
public class Request {
    HTTPMethod method;
    String url;
    String version;

    String accept;
    String contentType;
    String host;
    String connection;
    String userAgent;
    String acceptEncoding;

    Map<String, Integer> sec_ch_ua;

    String body;
}
