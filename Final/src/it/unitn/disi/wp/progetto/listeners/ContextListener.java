package it.unitn.disi.wp.progetto.listeners;

import it.unitn.disi.wp.progetto.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.progetto.persistence.dao.factories.DAOFactory;
import it.unitn.disi.wp.progetto.persistence.dao.factories.jdbc.JDBCDAOFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.logging.Logger;

public class ContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        String url = sce.getServletContext().getInitParameter("dburl");
        String username = sce.getServletContext().getInitParameter("dbusername");
        String password = sce.getServletContext().getInitParameter("dbpassword");

        try {
            JDBCDAOFactory.configure(url, username, password);
            DAOFactory daoFactory = JDBCDAOFactory.getInstance();

            sce.getServletContext().setAttribute("daoFactory", daoFactory);

            System.out.println("AAAAAAAAAAAAAAAAAAAAAAA");
        } catch (DAOFactoryException ex) {
            Logger.getLogger(getClass().getName()).severe(ex.toString());
            throw new RuntimeException(ex);
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        DAOFactory daoFactory = (DAOFactory) sce.getServletContext().getAttribute("daoFactory");
        System.out.println("BBBBBBBBBBBBBBBBBBBb");
        if (daoFactory != null) {
            daoFactory.shutdown();
        }
        daoFactory = null;
    }
}
