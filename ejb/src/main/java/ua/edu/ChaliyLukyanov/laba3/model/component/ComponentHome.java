package ua.edu.ChaliyLukyanov.laba3.model.component;

import java.rmi.RemoteException;
import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * Home Interface of Component ejb.
 */
public interface ComponentHome extends EJBHome {
	

    public Component create(String title, String description, String producer,
            double weight, String img, double price) throws
            RemoteException, CreateException;

    public Component findByPrimaryKey(Integer id) throws 
            FinderException, RemoteException;

    public Collection<Component> findAll() throws 
            FinderException, RemoteException;
}
