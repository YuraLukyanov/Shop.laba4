package ua.edu.ChaliyLukyanov.laba3.model.EJB;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

public interface Component extends EJBObject {

	public int getId() throws RemoteException;

	public String getTitle() throws RemoteException;

	public void setTitle(String title) throws RemoteException;

	public String getDescription() throws RemoteException;

	public void setDescription(String description) throws RemoteException;

	public String getProducer() throws RemoteException;

	public void setProducer(String producer) throws RemoteException;

	public double getWeight() throws RemoteException;

	public void setWeight(double weight) throws RemoteException;

	public String getImg() throws RemoteException;

	public void setImg(String img) throws RemoteException;

	public double getPrice() throws RemoteException;

	public void setPrice(double price) throws RemoteException;

}