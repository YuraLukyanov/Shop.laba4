package ua.edu.ChaliyLukyanov.laba3.controller.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import javax.ejb.CreateException;

import ua.edu.ChaliyLukyanov.laba3.model.Application;
import ua.edu.ChaliyLukyanov.laba3.model.NoSuchComponentException;
import ua.edu.ChaliyLukyanov.laba3.model.NoSuchDeviceException;
import ua.edu.ChaliyLukyanov.laba3.model.ShopException;
import ua.edu.ChaliyLukyanov.laba3.model.EJB.*;

/**
 * Class is responsible for adding component.
 *
 * @author    Yura Lukyanov <lukyanov.yura@gmail.com>
 * @version   19 June 2012
 * @since     1.6
 */
public class AddComponentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger("Shoplogger");
	
	/**
	 * Gets component's data and creates component.
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		ComponentHome compHome = (ComponentHome) request.getAttribute(Application.COMPONENT_DAO);
		String title = request.getParameter("title");
		String desc = request.getParameter("desc");
		String producer = request.getParameter("producer");
		String img = request.getParameter("img");
		String price = request.getParameter("price");
		String weight = request.getParameter("weight");

		try {
			
			double pr = 0.0;
			double w = 0.0;
			if (!"".equals(price))
				pr = Double.parseDouble(price);

			if (!"".equals(weight))
				w = Double.parseDouble(weight);
			
			if ("".equals(title))
				throw new IllegalArgumentException("Title should be!");

			if ("".equals(desc))
				throw new IllegalArgumentException("Description should be!");
			
			if ("".equals(producer))
				throw new IllegalArgumentException("Producer should be!");

			if (pr < 0 || w < 0) {
				throw new NumberFormatException("Price and weight should be > 0");
			}

			Component component = compHome.create(title, desc, producer, new Double(w), img, new Double(pr));
//			int id = compHome.getIdLastComponent();
			logger.info("Component adds");
			response.sendRedirect(request.getContextPath() + "/showcomponent?id=" + component.getId());
		} catch (ShopException e) {
			logger.error(e);
			response.sendRedirect(request.getContextPath()
					+ "/add_component.jsp?error=" + e.getMessage());
		} catch (NumberFormatException e) {
			logger.error(e);
			response.sendRedirect(request.getContextPath()
					+ "/add_component.jsp?error=" + e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error(e);
			response.sendRedirect(request.getContextPath()
					+ "/add_component.jsp?error=" + e.getMessage());
		} catch (NoSuchComponentException e){
			logger.error(e);
			response.sendRedirect(request.getContextPath()
					+ "/add_component.jsp?error=" + e.getMessage());
		} catch (CreateException e) {
			logger.error(e);
		}
	}
}
