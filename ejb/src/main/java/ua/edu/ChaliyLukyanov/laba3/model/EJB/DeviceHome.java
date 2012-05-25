package ua.edu.ChaliyLukyanov.laba3.model.EJB;

import javax.ejb.FinderException;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import ua.edu.ChaliyLukyanov.laba3.model.ShopException;

import java.util.List;

public interface DeviceHome extends EJBHome {
	
	public Device findByPrimaryKey(Integer id) throws FinderException, EJBException, RemoteException;
	
	public List<Device> findAllDevices() throws FinderException, EJBException, RemoteException;
	
	public Device create(Integer id_prev, Integer id_component, String title) throws RemoteException, EJBException, CreateException;
	
	public List<Device> findPrevLevelsDeviceByID(Integer id) throws FinderException, EJBException, RemoteException;
	
	public List<Device> findNextLevelsDeviceByID(Integer id) throws FinderException, EJBException, RemoteException;
	
	public List<Device> findFirstLevelsDeviceByID(Integer id) throws FinderException, EJBException, RemoteException;
	
//	public int getIdLastDevice() throws RemoteException, ShopException;
}