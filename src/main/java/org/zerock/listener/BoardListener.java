package org.zerock.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class BoardListener
 *
 */
public class BoardListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public BoardListener() {
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
    	ServletContext app  = sce.getServletContext();
    	
    	String contextRoot = app.getContextPath();
    	
    	app.setAttribute("root", contextRoot);
    	
    	
    }
	
}
