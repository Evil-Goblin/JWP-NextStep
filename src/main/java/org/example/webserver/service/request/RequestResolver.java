package org.example.webserver.service.request;

import lombok.extern.slf4j.Slf4j;
import org.example.webserver.service.variable.HTTPMethod;
import org.example.webserver.service.variable.HeaderProperties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class RequestResolver {

    private final Pattern headerPattern = Pattern.compile("(.*): (.*)");

    public Request parse(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String firstLine = bufferedReader.readLine();
        log.debug("[RequestLine]: {}", firstLine);

        String[] split = firstLine.split("\\s");
        HTTPMethod method = HTTPMethod.valueOf(split[0]);
        String url = split[1];
        String version = split[2];

        RequestUrl requestUrl = RequestUrl.from(url);

        Map<String, String> header = new HashMap<>();

        for (String line = bufferedReader.readLine();
             !"".equals(line);
             line = bufferedReader.readLine()) {
            log.debug("[RequestHeader]: {}", line);

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
                .cookies(header.getOrDefault(HeaderProperties.COOKIE, ""))
                .body(body)
                .build();
    }
}
