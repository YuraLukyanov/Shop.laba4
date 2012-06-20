package ua.edu.ChaliyLukyanov.laba3.controller.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import org.apache.log4j.Logger;

import javax.ejb.CreateException;

import ua.edu.ChaliyLukyanov.laba3.model.Application;
import ua.edu.ChaliyLukyanov.laba3.model.NoSuchDeviceException;
import ua.edu.ChaliyLukyanov.laba3.model.ShopException;
import ua.edu.ChaliyLukyanov.laba3.model.EJB.*;

/**
 * Class is responsible for adding device.
 *
 * @author    Yura Lukyanov <lukyanov.yura@gmail.com>
 * @version   19 June 2012
 * @since     1.6
 */
public class AddDeviceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger=Logger.getLogger("Shoplogger");

	/**
	 * Gets device's data from view and creates device.
	 */	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DeviceHome devHome = (DeviceHome) request.getAttribute(Application.DEVICE_DAO);
		try {
			String title = request.getParameter("title");
			
			if ("".equals(title))
				throw new IllegalArgumentException("Title should be!");
			
			Device dev = devHome.create(new Integer(request.getParameter("id_prev_device")), 
										new Integer(request.getParameter("id_component")),
										title);
			logger.info("Device adds");
//			int id = devHome.getIdLastDevice();
			response.sendRedirect(request.getContextPath() + "/shownextleveldevices?id=" + dev.getId());
			
		} catch (ShopException e) {
			logger.error(e);
			response.sendRedirect(request.getContextPath()
					+ "/add_device.jsp?error=" + e.getMessage());
		} catch (IllegalArgumentException e){
			logger.error(e);
			response.sendRedirect(request.getContextPath()
					+ "/add_device.jsp?error=" + e.getMessage());
		} catch (NoSuchDeviceException e){
			logger.error(e);
			response.sendRedirect(request.getContextPath()
					+ "/add_device.jsp?error=" + e.getMessage());
		} catch (CreateException e) {
			logger.error(e);
		}
	}
	
	/**
	 * Gets all devices and components from database.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComponentHome compHome = (ComponentHome) request.getAttribute(Application.COMPONENT_DAO);
		DeviceHome deviceHome = (DeviceHome) request.getAttribute(Application.DEVICE_DAO);
		try {
			List<Component> components = compHome.findAllComponents();
			List<Device> devices = deviceHome.findAllDevices();
			request.setAttribute("components", components);
			request.setAttribute("devices", devices);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/add_device.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
}
