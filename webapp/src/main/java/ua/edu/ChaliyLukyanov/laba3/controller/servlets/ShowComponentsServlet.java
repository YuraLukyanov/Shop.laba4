package ua.edu.ChaliyLukyanov.laba3.controller.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.edu.ChaliyLukyanov.laba3.model.Application;
import ua.edu.ChaliyLukyanov.laba3.model.ShopException;
import ua.edu.ChaliyLukyanov.laba3.model.EJB.*;
import javax.ejb.FinderException;

/**
 * Class is responsible for showing components.
 *
 * @author    Yura Lukyanov <lukyanov.yura@gmail.com>
 * @version   19 June 2012
 * @since     1.6
 */
public class ShowComponentsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger=Logger.getLogger("Shoplogger");

	/**
	 * Gets all components from database and.
	 */	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ComponentHome compHome = (ComponentHome) request.getAttribute(Application.COMPONENT_DAO);
		try {
			List<Component> components = compHome.findAllComponents();
//			List<String> producers = compHome.getDistinctProducers();
			request.setAttribute("components", components);
//			request.setAttribute("producers", producers);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/show_components.jsp");
			dispatcher.forward(request, response);
		} catch (ShopException e) {
			logger.error(e);
			throw new ShopException(e.getMessage());
		} catch (NumberFormatException e) {
			logger.error(e);
			throw new NumberFormatException(e.getMessage());
		} catch (FinderException e) {
			logger.error(e);
		}
	}
}
