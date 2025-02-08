package ru.itis.fisd.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.itis.fisd.database.DBConnection;

@WebListener
public class ContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        DBConnection.getInstance();
    }

    public void contextDestroyed(ServletContextEvent sce) {
        DBConnection.getInstance().destroy();
    }
}
