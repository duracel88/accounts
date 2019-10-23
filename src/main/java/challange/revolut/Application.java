package challange.revolut;


import java.util.EnumSet;
import java.util.stream.Stream;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

import com.google.inject.Guice;
import com.google.inject.servlet.GuiceFilter;

import challange.revolut.infrastructure.db.DatabaseModule;
import challange.revolut.service.ServiceModule;
import challange.revolut.ui.UIModule;
import lombok.SneakyThrows;

import static org.eclipse.jetty.servlet.ServletContextHandler.SESSIONS;

public class Application {

    private static final int DEFAULT_PORT = 4200;

    @SneakyThrows
    public static void main(String[] args) {
        createInjector();

        startHttpServer(getPort(args)).join();
    }

    private static void createInjector() {
        Guice.createInjector(new UIModule(), new ServiceModule(), new DatabaseModule());
    }

    public static Server startHttpServer(int port) throws Exception {
        Server server = new Server(port);

        ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/", SESSIONS);
        servletContextHandler.addFilter(GuiceFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
        servletContextHandler.addServlet(DefaultServlet.class, "/");

        server.start();
        return server;

    }

    private static int getPort(String[] args) {
        return Stream.of(args)
                .filter(a -> a.startsWith("--port="))
                .map(a -> a.split("--port=")[1])
                .findFirst()
                .map(Integer::valueOf)
                .orElse(DEFAULT_PORT);
    }

}
