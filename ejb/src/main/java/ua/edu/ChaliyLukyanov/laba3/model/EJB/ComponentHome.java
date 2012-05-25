package ua.edu.ChaliyLukyanov.laba3.model.EJB;

import javax.ejb.FinderException;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import java.util.List;
import javax.ejb.EJBException;
import ua.edu.ChaliyLukyanov.laba3.model.ShopException;

public interface ComponentHome extends EJBHome {

	public Component findByPrimaryKey(Integer id) throws FinderException, EJBException, RemoteException;

	public List<Component> findAllComponents() throws FinderException, EJBException, RemoteException;

//	public int getIdLastComponent() throws RemoteException, ShopException;

	public Component create(String title, String description, String producer, 
							double weight, String img, double price) 
							throws RemoteException, CreateException, EJBException;
}