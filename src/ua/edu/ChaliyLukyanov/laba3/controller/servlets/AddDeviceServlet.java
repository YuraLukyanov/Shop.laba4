package ua.edu.ChaliyLukyanov.laba3.controller.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import javax.ejb.CreateException;

import ua.edu.ChaliyLukyanov.laba3.model.Application;
import ua.edu.ChaliyLukyanov.laba3.model.NoSuchDeviceException;
import ua.edu.ChaliyLukyanov.laba3.model.ShopException;
import ua.edu.ChaliyLukyanov.laba3.model.EJB.*;


public class AddDeviceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger=Logger.getLogger("Shoplogger");

	
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
			throw new ShopException(e.getMessage());
		} catch (IllegalArgumentException e){
			logger.error(e);
			response.sendRedirect(request.getContextPath()
					+ "/add_device.jsp?&prev=" + request.getParameter("id_prev_device")
					+ "&component=" + request.getParameter("id_component") 
					+ "&error=" + e.getMessage());
		} catch (NoSuchDeviceException e){
			logger.error(e);
			throw new NoSuchDeviceException(e.getMessage());
		} catch (CreateException e) {
			logger.error(e);
		}
	}

}
