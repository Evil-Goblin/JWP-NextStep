package org.example.webserver.service.request;

import lombok.Getter;
import org.example.webserver.util.parameterresolver.QueryParamResolver;

import java.util.HashMap;
import java.util.Map;

@Getter
public class RequestUrl {

    private final String uri;
    private final Map<String, String> queryParam;

    public RequestUrl(String uri, Map<String, String> queryParam) {
        this.uri = uri;
        this.queryParam = queryParam;
    }

    public static RequestUrl from(String url) {
        int queryToken = url.indexOf('?');
        if (queryToken == -1) {
            return new RequestUrl(url, new HashMap<>());
        }

        final String uri = url.substring(0, queryToken);
        final String queryString = url.length() > queryToken + 1 ? url.substring(queryToken + 1) : "";

        return new RequestUrl(uri, QueryParamResolver.parseQueryParam(queryString));
    }
}
