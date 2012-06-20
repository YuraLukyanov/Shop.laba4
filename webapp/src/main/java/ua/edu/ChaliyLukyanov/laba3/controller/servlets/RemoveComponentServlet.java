package ua.edu.ChaliyLukyanov.laba3.controller.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ejb.RemoveException;

import org.apache.log4j.Logger;

import ua.edu.ChaliyLukyanov.laba3.model.Application;
import ua.edu.ChaliyLukyanov.laba3.model.ShopException;
import ua.edu.ChaliyLukyanov.laba3.model.EJB.*;

/**
 * Class is responsible for removing component.
 *
 * @author    Yura Lukyanov <lukyanov.yura@gmail.com>
 * @version   19 June 2012
 * @since     1.6
 */
public class RemoveComponentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger=Logger.getLogger("Shoplogger");

	/**
	 * Removes component from database by id.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComponentHome compHome = (ComponentHome) request.getAttribute(Application.COMPONENT_DAO);
		try {
			compHome.remove(new Integer(request.getParameter("id_component")));
			logger.info("Component " + request.getParameter("id_component") + " removes");
			response.sendRedirect(request.getContextPath() + "/removedevices");
		} catch (ShopException e) {
			logger.error(e);
			throw new ShopException(e.getMessage());
		} catch (NumberFormatException e) {
			logger.error(e);
			throw new NumberFormatException("Incorrect value " + e.getMessage());
		} catch (RemoveException e) {
			logger.error(e);
		}
	}
	
	/**
	 * Gets all components from database.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComponentHome compHome = (ComponentHome) request.getAttribute(Application.COMPONENT_DAO);
		try {
			List<Component> components = compHome.findAllComponents();
			request.setAttribute("components", components);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/remove_component.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
}
