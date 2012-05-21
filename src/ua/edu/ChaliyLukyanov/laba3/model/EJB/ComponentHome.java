package ua.edu.ChaliyLukyanov.laba3.model.EJB;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface ComponentHome extends EJBHome {
	ComponentRemote create() throws RemoteException, CreateException;
}