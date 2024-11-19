package org.example.final_exam.listener;

import jakarta.persistence.PersistenceException;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.example.final_exam.util.HibernateUtil;

@WebListener
public class HibernateListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try{
            HibernateUtil.init();
        } catch (PersistenceException ex) {
            System.err.println("failed." );
            shutdownServer();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (HibernateUtil.getEntityManagerFactory()!=null && HibernateUtil.getEntityManagerFactory().isOpen()){
            HibernateUtil.getEntityManagerFactory().close();
        }
    }
    private void shutdownServer() {
        System.exit(1);
    }
}
