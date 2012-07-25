package ua.edu.ChaliyLukyanov.laba3.model.component;

import java.rmi.RemoteException;
import javax.ejb.EJBObject;

/**
 * Remote interface of component bean
 * Describes getters and setters of component 
 * @author chalyi
 *
 */
public interface Component extends EJBObject {

    public int getId() throws RemoteException;

    public String getTitle() throws RemoteException;

    public String getDescription() throws RemoteException;

    public String getProducer() throws RemoteException;

    public double getWeight() throws RemoteException;

    public String getImg() throws RemoteException;

    public double getPrice() throws RemoteException;

    public void setTitle(String title) throws RemoteException;

    public void setDescription(String desc) throws RemoteException;

    public void setProducer(String producer) throws RemoteException;

    public void setWeight(double weight) throws RemoteException;

    public void setImg(String img) throws RemoteException;

    public void setPrice(double price) throws RemoteException;
}
