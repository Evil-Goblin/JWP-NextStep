package jwp;

import jwp.core.web.filter.CharacterEncodingFilter;
import jwp.next.controller.*;
import jwp.next.support.context.ContextLoaderListener;
import org.apache.catalina.servlets.DefaultServlet;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;

public class Main {
    public static void main(String[] args) {
        ServletWebServerFactory servletWebServerFactory = new TomcatServletWebServerFactory();

        servletWebServerFactory.getWebServer(servletContext -> {
            servletContext.addListener(new ContextLoaderListener());

            servletContext.addFilter("CharacterEncodingFilter", new CharacterEncodingFilter());

            servletContext.addServlet("default", new DefaultServlet()).addMapping("/");
            servletContext.addServlet("Home", new HomeController()).addMapping("");

            CreateUserController createUserController = new CreateUserController();
            servletContext.addServlet("UserCreate", createUserController).addMapping("/users/create");
            servletContext.addServlet("UserCreateForm", createUserController).addMapping("/users/form");

            ListUserController listUserController = new ListUserController();
            servletContext.addServlet("UserList", listUserController).addMapping("/users");

            LoginController loginController = new LoginController();
            servletContext.addServlet("Login", loginController).addMapping("/users/login");
            servletContext.addServlet("LoginForm", loginController).addMapping("/users/loginForm");

            LogoutController logoutController = new LogoutController();
            servletContext.addServlet("Logout", logoutController).addMapping("/users/logout");

            UpdateUserController updateUserController = new UpdateUserController();
            servletContext.addServlet("UserUpdate", updateUserController).addMapping("/users/update");
            servletContext.addServlet("UserUpdateForm", updateUserController).addMapping("/users/updateForm");

        }).start();
    }
}
