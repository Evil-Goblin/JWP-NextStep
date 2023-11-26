package org.example.webserver.service.request;

import org.example.webserver.service.exception.InternalException;
import org.example.webserver.service.variable.HTTPMethod;
import org.example.webserver.service.variable.HeaderProperties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RequestResolver {

    private final Pattern headerPattern = Pattern.compile("(.*): (.*)");

    public Request parse(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String firstLine = bufferedReader.readLine();
        String[] split = firstLine.split("\\s");
        HTTPMethod method = HTTPMethod.valueOf(split[0]);
        String url = split[1];
        String version = split[2];

        RequestUrl requestUrl = RequestUrl.from(url);

        Map<String, String> header = new HashMap<>();

        for (String line = bufferedReader.readLine();
             !"".equals(line);
             line = bufferedReader.readLine()) {
            Matcher matcher = headerPattern.matcher(line);
            if (matcher.find()) {
                String key = matcher.group(1);
                String value = matcher.group(2);
                header.put(key, value);
            }
        }

        int contentLength = Integer.parseInt(header.getOrDefault("Content-Length", "0"));
        char[] buffer = new char[contentLength];
        bufferedReader.read(buffer, 0, contentLength);
        String body = new String(buffer);

        return Request.builder()
                .method(method)
                .requestUrl(requestUrl)
                .version(version)
                .accept(header.getOrDefault(HeaderProperties.ACCEPT, ""))
                .contentType(header.getOrDefault(HeaderProperties.CONTENT_TYPE, ""))
                .host(header.getOrDefault(HeaderProperties.HOST, ""))
                .connection(header.getOrDefault(HeaderProperties.CONNECTION, ""))
                .userAgent(header.getOrDefault(HeaderProperties.USER_AGENT, ""))
                .acceptEncoding(header.getOrDefault(HeaderProperties.ACCEPT_ENCODING, ""))
                .body(body)
                .build();
    }
}
