package org.example.handler.request;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.handler.HTTPMethod;

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

    @Getter
    private HTTPMethod method;

    @Getter
    private String url;

    @Getter
    private String version;

    @Getter
    private String body;

    private final Pattern headerPattern;

    private final Map<String, String> requestHeader;

    public RequestResolver() {
        headerPattern = Pattern.compile("(.*): (.*)");
        requestHeader = new HashMap<>();
    }

    public void parse(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            parseFirstLine(bufferedReader);

            parseHeader(bufferedReader);

            parseBody(bufferedReader);
        } catch (IOException e) {
            log.error("[RequestResolver:parse] Request Parsing Error:", e);
        }
    }

    public String getProperties(String key) {
        return getProperties(key, "");
    }

    public String getProperties(String key, String def) {
        return requestHeader.getOrDefault(key, def);
    }

    public void clear() {
        method = null;
        url = null;
        version = null;
        body = null;

        requestHeader.clear();
    }

    private void parseBody(BufferedReader bufferedReader) throws IOException {
        int contentLength = Integer.parseInt(requestHeader.getOrDefault("Content-Length", "0"));
        char[] buffer = new char[contentLength];
        bufferedReader.read(buffer, 0, contentLength);
        body = new String(buffer);
        log.info("[RequestResolver:parse] body={}", body);
    }

    private void parseHeader(BufferedReader bufferedReader) throws IOException {
        String line;
        while (!"".equals((line = bufferedReader.readLine()))) {
            Matcher matcher = headerPattern.matcher(line);
            if (matcher.find()) {
                String key = matcher.group(1);
                String value = matcher.group(2);
                log.info("[RequestResolver:parse] Header Key: {}, value: {}", key, value);
                requestHeader.put(key, value);
            }
        }
    }

    private void parseFirstLine(BufferedReader bufferedReader) throws IOException {
        String startLine = bufferedReader.readLine();

        String[] split = startLine.split("\\s");
        method = HTTPMethod.valueOf(split[0]);
        url = split[1];
        version = split[2];
    }
}
