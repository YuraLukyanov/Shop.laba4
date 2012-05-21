package ua.edu.ChaliyLukyanov.laba3.model;

import java.io.IOException;

import ua.edu.ChaliyLukyanov.laba3.model.EJB.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.ejb.CreateException;
import java.rmi.RemoteException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Application implements ServletRequestListener, ServletContextListener {

	public static final String DEVICE_DAO = "shop_devices";
	public static final String COMPONENT_DAO = "shop_components";

	public void requestDestroyed(ServletRequestEvent event) {

	}

	public void requestInitialized(ServletRequestEvent event) {
		try {
			Context context_comp = new InitialContext();
			ComponentHome comp_h = (ComponentHome)context_comp.lookup("java:comp/env/ejb/ComponentBean");
			ComponentRemote components = (ComponentRemote) comp_h.create();
			Context context_dev = new InitialContext();
			DeviceHome dev_h = (DeviceHome)context_dev.lookup("java:comp/env/ejb/DeviceBean");
			DeviceRemote devices = (DeviceRemote) dev_h.create();
			event.getServletRequest().setAttribute(DEVICE_DAO, devices);
			event.getServletRequest().setAttribute(COMPONENT_DAO, components);
		} catch (NamingException e) {
			
		} catch (CreateException e) {
		
		} catch (RemoteException e) {
		
		}
	}

	
	public static void sendErrorRedirect(HttpServletRequest request, HttpServletResponse response, String errorPageURL, String error)
			throws ServletException, IOException {
		request.setAttribute("exception", error);
		RequestDispatcher dispatcher = request.getRequestDispatcher(errorPageURL);
		dispatcher.forward(request, response);
	}
	
	public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        System.out.println(context.getRealPath("/"));
        System.setProperty("rootPath", context.getRealPath("/"));
}
	 
	 public void contextDestroyed(ServletContextEvent event) 
	 {
	   
	 }
}
