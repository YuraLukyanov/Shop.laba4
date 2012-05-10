package ua.edu.ChaliyLukyanov.laba3.controller.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import ua.edu.ChaliyLukyanov.laba3.model.Component;
import ua.edu.ChaliyLukyanov.laba3.model.Component.PriceComparator;
import ua.edu.ChaliyLukyanov.laba3.model.Component.ProducerComparator;
import ua.edu.ChaliyLukyanov.laba3.model.Component.TitleComparator;
import ua.edu.ChaliyLukyanov.laba3.model.DAO.ComponentDAO;
import ua.edu.ChaliyLukyanov.laba3.model.ShopException;

public class FilterComponentsServlet extends HttpServlet
{
  private static final long serialVersionUID = 1L;
  private static Logger logger = Logger.getLogger("Shoplogger");
  private ServletContext context;

  public void init(ServletConfig sc)
    throws ServletException
  {
    this.context = sc.getServletContext();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    ComponentDAO model = (ComponentDAO)request.getAttribute("shop_components");
    try
    {
      List<Component> components = model.getAllComponents();
      Comparator<Component> comparator = null;
      String order = request.getParameter("orderBy");
      String sort = request.getParameter("sortBy");
      String sign = request.getParameter("sign");
      String priceSt = request.getParameter("price");
      Double price;
      if (priceSt.isEmpty())
        price = null;
      else
        price = Double.valueOf(Double.parseDouble(priceSt));
      StringBuffer sb = new StringBuffer();
      if (!"none".equals(sort))
      {
        if ("title".equals(sort))
          comparator = new Component.TitleComparator();
        else if ("producer".equals(sort))
          comparator = new Component.ProducerComparator();
        else if ("price".equals(sort))
          comparator = new Component.PriceComparator();
        Collections.sort(components, (Comparator)comparator);
        if ("desc".equals(order))
          Collections.reverse(components);
      }
      if (price != null)
        if (">=".equals(sign))
        {
          Iterator<Component> iter = components.iterator();
          while (iter.hasNext())
          {
            if (!(iter.next().getPrice() >= price.doubleValue()))
				iter.remove();
          }
        }
        else if ("<=".equals(sign))
        {
          Iterator<Component> iter = components.iterator();
          while (iter.hasNext())
          {
            if (!(iter.next().getPrice() <= price.doubleValue()))
				iter.remove();
          }
        }
	  
	  for (Component comp : components)
      {
        sb.append("<component>");
        sb.append("<title>" + comp.getTitle() + "</title>");
        sb.append("<producer>" + comp.getProducer() + "</producer>");
        sb.append("<weight>" + comp.getWeight() + "</weight>");
        sb.append("<price>" + comp.getPrice() + "</price>");
        sb.append("<id>" + comp.getId() + "</id>");
        sb.append("</component>");
      }
      response.setContentType("text/xml");
      response.setHeader("Cache-Control", "no-cache");
      response.getWriter().write("<components>" + sb.toString() + "</components>");
    }
    catch (ShopException e)
    {
      logger.error(e);
      throw new ShopException(e.getMessage());
    }
    catch (NumberFormatException e)
    {
      logger.error(e);
      throw new NumberFormatException("Incorrect value " + e.getMessage());
    }
  }
}