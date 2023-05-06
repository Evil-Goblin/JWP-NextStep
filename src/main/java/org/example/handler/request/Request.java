package org.example.handler.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.handler.HTTPMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Builder
@ToString
public class Request {
    HTTPMethod method;
    String url;
    String version;

//        String host;
//    String connection;
//
//    Map<String, Integer> sec_ch_ua;

    public static Request requestParse(InputStream in) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

        RequestBuilder requestBuilder = Request.builder();

        try {
            String startLine = bufferedReader.readLine();

            String[] split = startLine.split("\\s");
            requestBuilder.method(HTTPMethod.valueOf(split[0]));
            requestBuilder.url(split[1]);
            requestBuilder.version(split[2]);
//            Matcher matcher = Pattern.compile("(.*) (.*) HTTP/(.*)").matcher(startLine);
//            if (matcher.find()) {
//                requestBuilder.method(HTTPMethod.valueOf(matcher.group(1)));
//                requestBuilder.url(matcher.group(2));
//                requestBuilder.version(matcher.group(3));
//            }
        } catch (IOException e) {
            System.out.println("e.getMessage() = " + e.getMessage());
        }

        Pattern pattern = Pattern.compile("(.*): (.*)");
        try {
            // HEADER
            String line;
            while (!"".equals((line = bufferedReader.readLine()))){
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    System.out.println("matcher.group(1) = " + matcher.group(1));
                    System.out.println("matcher.group(2) = " + matcher.group(2));
                }
            }

        } catch (IOException e) {
            System.out.println("e.getMessage() = " + e.getMessage());
        }

        return requestBuilder.build();
    }

//    public static Request requestParse(String requestMessage) {
//        Iterator<String> requestIter = Arrays.stream(requestMessage.split("\\r\\n")).iterator();
//
//
//        Pattern pattern = Pattern.compile("(.*): (.*)");
//        while (requestIter.hasNext()) {
//            String line = requestIter.next();
//            if (0 == line.length())
//                break;
//
//            Matcher matcher = pattern.matcher(line);
//            if (matcher.find()) {
//                System.out.println("matcher.group(1) = " + matcher.group(1));
//                System.out.println("matcher.group(2) = " + matcher.group(2));
//            }
//        }
//
//    }
}
