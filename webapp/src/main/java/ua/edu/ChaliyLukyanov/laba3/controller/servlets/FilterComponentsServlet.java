package ua.edu.ChaliyLukyanov.laba3.controller.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;								
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import ua.edu.ChaliyLukyanov.laba3.model.Comparator;
import ua.edu.ChaliyLukyanov.laba3.model.Comparator.PriceComparator;
import ua.edu.ChaliyLukyanov.laba3.model.Comparator.ProducerComparator;
import ua.edu.ChaliyLukyanov.laba3.model.Comparator.TitleComparator;
import ua.edu.ChaliyLukyanov.laba3.model.EJB.*;
import ua.edu.ChaliyLukyanov.laba3.model.ShopException;
import javax.ejb.FinderException;

import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

/**
 * Class is responsible for filtering components.
 *
 * @author    Yura Lukyanov <lukyanov.yura@gmail.com>
 * @version   19 June 2012
 * @since     1.6
 */
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
    ComponentHome compHome = (ComponentHome)request.getAttribute("shop_components");
    try
    {
      List<Component> components = compHome.findAllComponents();
      java.util.Comparator<Component> comparator = null;
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
          comparator = new Comparator.TitleComparator();
        else if ("producer".equals(sort))
          comparator = new Comparator.ProducerComparator();
        else if ("price".equals(sort))
          comparator = new Comparator.PriceComparator();
        Collections.sort(components, (java.util.Comparator)comparator);
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
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
        Transformer t = TransformerFactory.newInstance().newTransformer();
//        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.transform(new DOMSource(this.write(components)), new StreamResult(response.getWriter()));
/*	  
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
      response.getWriter().write("<components>" + sb.toString() + "</components>");*/
    }
    catch (ShopException e){
      logger.error(e);
      throw new ShopException(e.getMessage());
    }
    catch (NumberFormatException e){
      logger.error(e);
      throw new NumberFormatException("Incorrect value " + e.getMessage());
    } catch (FinderException e) {
		logger.error(e);
	} catch (ParserConfigurationException e) {
		logger.error(e);
	} catch (TransformerException e) {
		logger.error(e);
	}
  }
  
	private Document write(List<Component> components) throws ParserConfigurationException, java.rmi.RemoteException {
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element comps = doc.createElement("components");
        doc.appendChild(comps);
		for (Component comp : components) {
			Element component = doc.createElement("component");
			comps.appendChild(component);
			Element title = doc.createElement("title");
			component.appendChild(title);
			Text title_value = doc.createTextNode(comp.getTitle());
			title.appendChild(title_value);
			Element producer = doc.createElement("producer");
			component.appendChild(producer);
			Text producer_value = doc.createTextNode(comp.getProducer());
			producer.appendChild(producer_value);	
			Element weight = doc.createElement("weight");
			component.appendChild(weight);
			Text weight_value = doc.createTextNode(String.valueOf(comp.getWeight()));
			weight.appendChild(weight_value);		
			Element price = doc.createElement("price");
			component.appendChild(price);
			Text price_value = doc.createTextNode(String.valueOf(comp.getPrice()));
			price.appendChild(price_value);		
			Element id = doc.createElement("id");
			component.appendChild(id);
			Text id_value = doc.createTextNode(String.valueOf(comp.getId()));
			id.appendChild(id_value);
		}
		return doc;
	}
/*	
	public void print(Document doc){
            Element element = doc.getDocumentElement();
            Element elements = (Element)element.getChildNodes().item(0);
			System.out.println(elements.getTagName());
	}*/
}