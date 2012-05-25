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


public class EditComponentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger=Logger.getLogger("Shoplogger");
      

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComponentHome compHome = (ComponentHome) request.getAttribute(Application.COMPONENT_DAO);
		try {
			Integer id = new Integer(request.getParameter("id"));
			Component component = compHome.findByPrimaryKey(id);
			request.setAttribute("component", component);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/edit_component.jsp");
			dispatcher.forward(request, response);			
		} catch (ShopException e) {
			logger.error(e);
			throw new ShopException(e.getMessage());
		} catch (NoSuchComponentException e){
			logger.error(e);
			throw new NoSuchComponentException(e.getMessage());
		} catch (FinderException e) {
			logger.error(e);
		}
	}
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComponentHome compHome = (ComponentHome) request.getAttribute(Application.COMPONENT_DAO);
		try {
			Integer id = new Integer(request.getParameter("id_component"));
			Component component = compHome.findByPrimaryKey(id);
			component.setTitle(request.getParameter("title"));
			component.setDescription(request.getParameter("description"));
			component.setProducer(request.getParameter("producer"));
			component.setPrice(Double.valueOf(request.getParameter("price")));
			logger.info("Component " + id + " updates");
			response.sendRedirect(request.getContextPath() + "/showcomponent?id=" + id);
		} catch (ShopException e) {
			logger.error(e);
			throw new ShopException(e.getMessage());
		} catch (FinderException e) {
			logger.error(e);
		}
	}

}
