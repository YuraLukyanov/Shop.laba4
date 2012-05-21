package ua.edu.ChaliyLukyanov.laba3.model.EJB;

import java.util.List;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

import ua.edu.ChaliyLukyanov.laba3.model.Device;

public interface DeviceRemote extends EJBObject {

	List<Device> getAllDevices() throws RemoteException;
	
	Device getDeviceByID(int id) throws RemoteException;
	
	void addDevice(Device device) throws RemoteException;
	
	void removeDevice(int id) throws RemoteException;
	
	List<Device> getPrevLevelsDeviceByID(int id) throws RemoteException;
	
	List<Device> getNextLevelsDeviceByID(int id) throws RemoteException;
	
	List<Device> getFirstLevelsDeviceByID(int id) throws RemoteException;
	
	int getIdLastDevice() throws RemoteException;
}
