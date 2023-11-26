package org.example.webserver.service.request;

import lombok.Getter;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

        return new RequestUrl(uri, parseQueryParam(queryString));
    }

    private static Map<String, String> parseQueryParam(String query) {
        return Arrays.stream(query.split("&"))
                .filter(s -> s.contains("="))
                .map(RequestUrl::splitQueryParameter)
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
    }

    private static AbstractMap.SimpleEntry<String, String> splitQueryParameter(String it) {
        final int idx = it.indexOf("=");
        final String key = idx > 0 ? it.substring(0, idx) : it;
        final String value = idx > 0 && it.length() > idx + 1 ? it.substring(idx + 1) : null;
        return new AbstractMap.SimpleEntry<>(key, value);
    }
}
