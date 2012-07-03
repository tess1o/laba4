package ua.edu.ChaliyLukyanov.laba3.model.device;

import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface Device extends EJBObject {
    
    public int getId() throws RemoteException;

    public int getIdPrev() throws RemoteException;

    public int getIdComponent() throws RemoteException;

    public String getTitle() throws RemoteException;
    
    public int getLevel() throws RemoteException;
}
