package ua.edu.ChaliyLukyanov.laba3.model.EJB;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

public interface Device extends EJBObject {
	
	public int getId() throws RemoteException;

	public int getIdPrev() throws RemoteException;

	public int getIdComponent() throws RemoteException;

	public String getTitle() throws RemoteException;
}