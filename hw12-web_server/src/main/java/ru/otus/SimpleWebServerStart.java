package ru.otus;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.otus.core.dao.UserDao;
import ru.otus.core.dao.UserDaoImpl;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.helpers.FileSystemHelper;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.TemplateProcessorImpl;
import ru.otus.services.UserAuthService;
import ru.otus.services.UserAuthServiceImpl;
import ru.otus.servlet.LoginServlet;
import ru.otus.servlet.UsersServlet;


/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/users

    // REST сервис
    http://localhost:8080/api/user/3
*/
public class SimpleWebServerStart {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String START_PAGE_NAME = "index.html";
    private static final String COMMON_RESOURCES_DIR = "static";
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        Server server = new Server(WEB_SERVER_PORT);
        ResourceHandler resourceHandler = createResourceHandler();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);

        UserDao userDao = new UserDaoImpl(sessionManager);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

//        UserAuthService userAuthService = new UserAuthServiceImpl(dbServiceUser);

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        HandlerList handlers = new HandlerList();
        handlers.addHandler(resourceHandler);
        servletContextHandler.addServlet(new ServletHolder(new UsersServlet(templateProcessor, dbServiceUser)), "/users");
//        servletContextHandler.addServlet(new ServletHolder(new LoginServlet(templateProcessor, userAuthService)), "/login");
        handlers.addHandler(servletContextHandler);
        server.setHandler(handlers);

        server.start();
        server.join();
    }

    private static ResourceHandler createResourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[]{START_PAGE_NAME});
        resourceHandler.setResourceBase(FileSystemHelper.localFileNameOrResourceNameToFullPath(COMMON_RESOURCES_DIR));
        return resourceHandler;
    }

//    private static Handler getJerseyHandler() {
//        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
//        handler.setContextPath("/");
//        ServletHolder servlet = handler.addServlet(org.glassfish.jersey.serlet.ServletContainer.class);
//        servlet.setInitOrder(0);
//        servlet.setInitParameter("");
//        return handler;
//    }
}
