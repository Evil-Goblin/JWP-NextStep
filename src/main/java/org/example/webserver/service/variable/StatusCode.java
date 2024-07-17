package org.example.webserver.service.variable;

import lombok.Getter;

@Getter
public enum StatusCode {
    OK(200, "200 OK"),
    CREATE(201, "201 Created"),
    ACCEPTED(202, "202 Accepted"),
    NON_AUTHORITATIVE_INFORMATION(203, "203 Non-Authoritative Information"),
    NO_CONTENT(204, "204 No Content"),
    RESET_CONTENT(205, "205 Reset Content"),
    PARTIAL_CONTENT(206, "206 Partial Content"),

    FOUND(302, "302 Found"),

    NOT_FOUND(404, "404 Not Found"),

    INTERNAL_SERVER_ERROR(500, "500 Internal Server Error");

    private final int code;

    private final String phrase;

    StatusCode(int code, String phrase) {
        this.code = code;
        this.phrase = phrase;
    }

    public static StatusCode isRedirect(String view) {
        if (view.startsWith("redirect:")) {
            return FOUND;
        }

        return OK;
    }
}

