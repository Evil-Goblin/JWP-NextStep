package jwp.core.nmvc;

import jwp.core.annotation.RequestMethod;

import java.util.Objects;

public class HandlerKey {
    private final String url;
    private final RequestMethod requestMethod;

    @Override
    public String toString() {
        return "HandlerKey [" +
                "url='" + url + '\'' +
                ", requestMethod=" + requestMethod +
                ']';
    }

    public HandlerKey(String url, RequestMethod requestMethod) {
        this.url = url;
        this.requestMethod = requestMethod;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        HandlerKey that = (HandlerKey) object;
        return Objects.equals(url, that.url) && requestMethod == that.requestMethod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, requestMethod);
    }
}
