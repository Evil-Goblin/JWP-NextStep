package org.example.webserver.util.assertion;

public abstract class Assert {

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new RuntimeException(message);
        }
    }
}
