package org.example.webserver.util.parameterresolver;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class QueryParamResolver {
    public static Map<String, String> parseQueryParam(String query) {
        return Arrays.stream(query.split("&"))
                .filter(s -> s.contains("="))
                .map(QueryParamResolver::splitQueryParameter)
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
    }

    public static AbstractMap.SimpleEntry<String, String> splitQueryParameter(String it) {
        final int idx = it.indexOf("=");
        final String key = idx > 0 ? it.substring(0, idx) : it;
        final String value = idx > 0 && it.length() > idx + 1 ? it.substring(idx + 1) : null;
        return new AbstractMap.SimpleEntry<>(key, value);
    }
}
