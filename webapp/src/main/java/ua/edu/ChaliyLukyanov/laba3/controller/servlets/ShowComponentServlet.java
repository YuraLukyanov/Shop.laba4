package ua.edu.ChaliyLukyanov.laba3.controller.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.edu.ChaliyLukyanov.laba3.model.Application;
import ua.edu.ChaliyLukyanov.laba3.model.NoSuchComponentException;
import ua.edu.ChaliyLukyanov.laba3.model.NoSuchDeviceException;
import ua.edu.ChaliyLukyanov.laba3.model.ShopException;
import ua.edu.ChaliyLukyanov.laba3.model.EJB.*;
import javax.ejb.FinderException;

/**
 * Class is responsible for showing component by id.
 *
 * @author    Yura Lukyanov <lukyanov.yura@gmail.com>
 * @version   19 June 2012
 * @since     1.6
 */
public class ShowComponentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger=Logger.getLogger("Shoplogger");
       
	/**
	 * Gets component from database by id and show it in the view.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComponentHome compHome = (ComponentHome)request.getAttribute(Application.COMPONENT_DAO);
		try {
//			Integer id = Integer.getInteger(request.getParameter("id"));
			Component component = compHome.findByPrimaryKey(new Integer(request.getParameter("id")));
			request.setAttribute("component", component);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/show_component.jsp");
			dispatcher.forward(request, response);			
		} catch (ShopException e) {
			logger.error(e);
			throw new ShopException(e.getMessage());
		} catch (NoSuchComponentException e){
			logger.error(e);
			throw new NoSuchDeviceException(e.getMessage());
		} catch (FinderException e) {
			logger.error(e);
		}
	}

}
