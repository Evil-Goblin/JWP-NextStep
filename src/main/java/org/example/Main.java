package org.example;

import org.example.client.controller.UserCreateController;
import org.example.client.controller.UserListController;
import org.example.client.controller.UserLoginController;
import org.example.client.model.user.MemoryUserRepository;
import org.example.client.model.user.UserRepository;
import org.example.client.model.user.UserService;
import org.example.webserver.WebServerStarter;
import org.example.webserver.service.servlet.DispatcherServlet;
import org.example.webserver.service.servlet.StaticResolver;
import org.example.webserver.service.servlet.adapter.queryparam.QueryParamAdapter;
import org.example.webserver.service.servlet.adapter.queryparamrequest.QueryParamRequestAdapter;
import org.example.webserver.service.servlet.adapter.queryparamresponse.QueryParamResponseAdapter;
import org.example.webserver.util.idgenerator.AtomicLongIdGenerator;

public class Main {
    public static void main(String[] args) {
        UserRepository memoryUserRepository = new MemoryUserRepository(new AtomicLongIdGenerator());
        UserService userService = new UserService(memoryUserRepository);
        StaticResolver staticResolver = new StaticResolver();
        DispatcherServlet dispatcherServlet = new DispatcherServlet(staticResolver);

        dispatcherServlet.addAdapter(new QueryParamAdapter());
        dispatcherServlet.addAdapter(new QueryParamResponseAdapter());
        dispatcherServlet.addAdapter(new QueryParamRequestAdapter());
//        dispatcherServlet.addHandler("/index.html", new IndexController());
//        dispatcherServlet.addHandler("/user/form.html", new UserFormController());
        dispatcherServlet.addHandler("/user/create", new UserCreateController(memoryUserRepository));
        dispatcherServlet.addHandler("/user/login", new UserLoginController(userService));
        dispatcherServlet.addHandler("/user/list", new UserListController(userService));
        WebServerStarter.run(dispatcherServlet);
    }
}
