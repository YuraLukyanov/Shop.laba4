package ua.edu.ChaliyLukyanov.laba3.controller.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.edu.ChaliyLukyanov.laba3.model.Application;
import ua.edu.ChaliyLukyanov.laba3.model.ShopException;
import ua.edu.ChaliyLukyanov.laba3.model.EJB.*;
import javax.ejb.RemoveException;

/**
 * Class is responsible for removing devices.
 *
 * @author    Yura Lukyanov <lukyanov.yura@gmail.com>
 * @version   19 June 2012
 * @since     1.6
 */
public class RemoveDeviceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger=Logger.getLogger("Shoplogger");

	/**
	 * Removes devices from database by id.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DeviceHome devHome = (DeviceHome) request.getAttribute(Application.DEVICE_DAO);
		try {
			Enumeration<String> names = (Enumeration<String>)request.getParameterNames();
			while(names.hasMoreElements()) {
				Integer id = new Integer(names.nextElement());
				devHome.remove(id);
				logger.info("Device " + id + " removes");
			}
			response.sendRedirect(request.getContextPath() + "/removedevices");
		} catch (ShopException e) {
			logger.error(e);
			throw new ShopException(e.getMessage());
		} catch (NumberFormatException e) {
			logger.error(e);
			throw new NumberFormatException(e.getMessage());
		} catch (RemoveException e) {
			logger.error(e);
		}
	}
	
	/**
	 * Gets all devices from database.
	 */	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DeviceHome devHome = (DeviceHome) request.getAttribute(Application.DEVICE_DAO);
		try {
			List<Device> devices = devHome.findAllDevices();
			request.setAttribute("devices", devices);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/remove_device.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			logger.error(e);
		}
	}
}
