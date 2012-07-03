package ua.edu.ChaliyLukyanov.laba3.model.device;

import java.rmi.RemoteException;
import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface DeviceHome extends EJBHome {

	public Collection<Device> findAll() throws FinderException, RemoteException;

	public Device findByPrimaryKey(Integer id) throws FinderException,
			RemoteException;

	public Device create(String title, Integer id_prev, Integer id_component)
			throws RemoteException, CreateException;

	public Collection<Device> findPrevLevelsDeviceByID(Integer id)
			throws FinderException, RemoteException;

	public Collection<Device> findNextLevelsDeviceByID(Integer id)
			throws FinderException, RemoteException;

	public Collection<Device> findFirstLevelsDeviceByID(Integer id)
			throws FinderException, RemoteException;

	// public Integer findIdLastDevice() throws
	// FinderException, RemoteException;
}
