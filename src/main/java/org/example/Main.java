package org.example;

import org.example.client.IndexController;
import org.example.client.UserCreateController;
import org.example.client.UserFormController;
import org.example.webserver.WebServerStarter;
import org.example.client.model.user.repository.MemoryUserRepository;
import org.example.client.model.user.repository.UserRepository;
import org.example.webserver.service.servlet.DispatcherServlet;
import org.example.webserver.service.servlet.StaticResolver;
import org.example.webserver.service.servlet.adapter.MethodMatchAdapter;
import org.example.webserver.util.idgenerator.AtomicLongIdGenerator;

public class Main {
    public static void main(String[] args) {
        UserRepository memoryUserRepository = new MemoryUserRepository(new AtomicLongIdGenerator());
        StaticResolver staticResolver = new StaticResolver();
        DispatcherServlet dispatcherServlet = new DispatcherServlet(staticResolver);

        dispatcherServlet.addAdapter(new MethodMatchAdapter());
        dispatcherServlet.addHandler("/index.html", new IndexController());
        dispatcherServlet.addHandler("/user/form.html", new UserFormController());
        dispatcherServlet.addHandler("/user/create", new UserCreateController(memoryUserRepository));
        WebServerStarter.run(dispatcherServlet);
    }
}
