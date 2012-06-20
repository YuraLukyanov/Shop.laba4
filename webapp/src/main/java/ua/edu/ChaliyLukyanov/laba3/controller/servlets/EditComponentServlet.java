package ua.edu.ChaliyLukyanov.laba3.controller.servlets;

import java.io.IOException;
import javax.ejb.FinderException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.edu.ChaliyLukyanov.laba3.model.Application;
import ua.edu.ChaliyLukyanov.laba3.model.NoSuchComponentException;
import ua.edu.ChaliyLukyanov.laba3.model.ShopException;
import ua.edu.ChaliyLukyanov.laba3.model.EJB.*;

/**
 * Class is responsible for editing component.
 *
 * @author    Yura Lukyanov <lukyanov.yura@gmail.com>
 * @version   19 June 2012
 * @since     1.6
 */
public class EditComponentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger=Logger.getLogger("Shoplogger");
      
	/**
	 * Gets component from database by id.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComponentHome compHome = (ComponentHome) request.getAttribute(Application.COMPONENT_DAO);
		try {
			Integer id = new Integer(request.getParameter("id"));
			Component component = compHome.findByPrimaryKey(id);
			request.setAttribute("component", component);
			String message = request.getParameter("error");
			request.setAttribute("error", message);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/edit_component.jsp");
			dispatcher.forward(request, response);			
		} catch (ShopException e) {
			logger.error(e);
			throw new ShopException(e.getMessage());
		}/* catch (NoSuchComponentException e){
			logger.error(e);
			throw new NoSuchComponentException(e.getMessage());
		}*/ catch (FinderException e) {
			logger.error(e);
		}
	}
	
	/**
	 * Gets edited component's data from view and edits component.
	 */	    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComponentHome compHome = (ComponentHome) request.getAttribute(Application.COMPONENT_DAO);
					Integer id = new Integer(request.getParameter("id_component"));
		try {
			String title = request.getParameter("title");
			if (title.isEmpty()) {
				throw new IllegalArgumentException("Title should be!");
			}
			Double price = new Double(request.getParameter("price"));
			
			Component component = compHome.findByPrimaryKey(id);
			component.setTitle(title);
			component.setDescription(request.getParameter("description"));
			component.setProducer(request.getParameter("producer"));
			component.setPrice(price);
			logger.info("Component " + id + " updates");
			response.sendRedirect(request.getContextPath() + "/showcomponent?id=" + id);
		} catch (ShopException e) {
			logger.error(e);
			throw new ShopException(e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error(e);
			response.sendRedirect(request.getContextPath() + "/editcomponent?id=" + id + "&error=" + e.getMessage());
		} catch (FinderException e) {
			logger.error(e);
		}
	}

}
