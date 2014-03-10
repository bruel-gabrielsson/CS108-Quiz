package listeners;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import app.App;

/**
 * Application Lifecycle Listener implementation class SessionLifetimeListener
 *
 */

//@WebListener
//public class SessionLifetimeListener implements HttpSessionListener {
//
//    /**
//     * Default constructor. 
//     */
//    public SessionLifetimeListener() {
//        // TODO Auto-generated constructor stub
//    }
//
//	/**
//     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
//     */
//    public void sessionCreated(HttpSessionEvent arg0) {
//    	System.out.println("SESSION CREATED");
//    	
//    	if (arg0.getSession().getAttribute("app") == null) {
//	    	System.out.println("APP CREATED IN SESSION");
//	    	App app = new App();
//	    	app.initialize();
//	    	
//	    	arg0.getSession().setAttribute("app", app);
//    	}
//    }
//
//	/**
//     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
//     */
//    public void sessionDestroyed(HttpSessionEvent arg0) {
//    	//CLosing connection
//    	App app = (App) arg0.getSession().getAttribute("app");
//    	app.connector.closeConnection();
//    	System.out.println("CONNECTION CLOSED IN SESSION");
//    }
//	
//}
//
