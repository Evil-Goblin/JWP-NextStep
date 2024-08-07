package jwp.core.web.filter;

import jakarta.servlet.*;
import org.slf4j.MDC;

import java.io.IOException;

public class UUIDFilter implements Filter {

    public static final String UUID = "UUID";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String uuid = java.util.UUID.randomUUID().toString();
        try {
            MDC.put(UUID, uuid);
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.remove(UUID);
        }
    }
}
