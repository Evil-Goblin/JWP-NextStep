package org.example.handler.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.handler.HTTPMethod;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@ToString
public class Request {
    HTTPMethod method;
    String url;
    String version;

    //    String host;
//    String connection;
//
//    Map<String, Integer> sec_ch_ua;

    Request(HTTPMethod method, String url, String version) {
        this.method = method;
        this.url = url;
        this.version = version;
    }

    private static Request from(String startLine) {
        if (null == startLine) {
            return null;
        }

        String[] s = startLine.split(" ");
        if (3 != s.length) {
            return null;
        }

        return new Request(HTTPMethod.valueOf(s[0]), s[1], s[2]);
    }

    public static Request requestParse(String requestMessage) {
        Iterator<String> requestIter = Arrays.stream(requestMessage.split("\\r\\n")).iterator();
        Request request = Request.from(requestIter.next());

        Pattern pattern = Pattern.compile("(.*): (.*)");
        while (requestIter.hasNext()) {
            String line = requestIter.next();
            if (0 == line.length())
                break;

            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                System.out.println("matcher.group(1) = " + matcher.group(1));
                System.out.println("matcher.group(2) = " + matcher.group(2));
            }
        }

        return request;
    }
}
