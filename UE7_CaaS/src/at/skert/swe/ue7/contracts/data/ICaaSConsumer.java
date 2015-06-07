package at.skert.swe.ue7.contracts.data;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICaaSConsumer extends Remote {
  void usersUpdated()  throws RemoteException;
  void ordersUpdated()  throws RemoteException;
  void menusUpdated()  throws RemoteException;
  void menuCategoriesUpdated()  throws RemoteException;
}
