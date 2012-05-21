package ua.edu.ChaliyLukyanov.laba3.model.EJB;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface DeviceHome extends EJBHome {
	DeviceRemote create() throws RemoteException, CreateException;
}