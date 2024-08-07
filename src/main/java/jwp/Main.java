package jwp;

import jakarta.servlet.ServletRegistration;
import jwp.core.mvc.DispatcherServlet;
import jwp.core.web.filter.CharacterEncodingFilter;
import jwp.core.web.filter.ResourceFilter;
import jwp.core.web.filter.UUIDFilter;
import jwp.next.support.context.ContextLoaderListener;
import org.apache.catalina.servlets.DefaultServlet;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;

public class Main {
    public static void main(String[] args) {
        TomcatServletWebServerFactory servletWebServerFactory = new TomcatServletWebServerFactory();

        servletWebServerFactory.getWebServer(servletContext -> {
            servletContext.addListener(new ContextLoaderListener());

            servletContext.addFilter("UUIDFilter", new UUIDFilter()).addMappingForUrlPatterns(null, false, "/*");
            servletContext.addFilter("CharacterEncodingFilter", new CharacterEncodingFilter()).addMappingForUrlPatterns(null, false, "/*");
            servletContext.addFilter("ResourceFilter", new ResourceFilter()).addMappingForUrlPatterns(null, false, "/*");

            ServletRegistration.Dynamic dispatcherServlet = servletContext.addServlet("dispatcherServlet", new DispatcherServlet());
            dispatcherServlet.setLoadOnStartup(1);
            dispatcherServlet.addMapping("/");

            servletContext.addServlet("default", new DefaultServlet()).addMapping("/");
        }).start();
    }
}
