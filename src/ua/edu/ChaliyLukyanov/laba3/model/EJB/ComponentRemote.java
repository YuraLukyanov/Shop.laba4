package ua.edu.ChaliyLukyanov.laba3.model.EJB;

import java.util.List;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

import ua.edu.ChaliyLukyanov.laba3.model.Component;

public interface ComponentRemote extends EJBObject {

	List<Component> getAllComponents() throws RemoteException;
	
	Component getComponentByID(int id) throws RemoteException;
	
	void addComponent(Component component) throws RemoteException;
	
	void removeComponent(int id) throws RemoteException;
	
	List<String> getDistinctProducers() throws RemoteException;
	
	void updateComponent(Component component) throws RemoteException;
	
	int getIdLastComponent() throws RemoteException;
}
