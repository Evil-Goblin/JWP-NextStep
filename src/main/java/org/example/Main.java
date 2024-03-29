package org.example;

import org.example.client.IndexController;
import org.example.client.UserCreateController;
import org.example.client.UserFormController;
import org.example.webserver.WebServerStarter;
import org.example.webserver.service.servlet.DispatcherServlet;
import org.example.webserver.service.servlet.adapter.MethodMatchAdapter;

public class Main {
    public static void main(String[] args) {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.addAdapter(new MethodMatchAdapter());
        dispatcherServlet.addHandler("/index.html", new IndexController());
        dispatcherServlet.addHandler("/user/form.html", new UserFormController());
        dispatcherServlet.addHandler("/user/create", new UserCreateController());
        WebServerStarter.run(dispatcherServlet);
    }
}
