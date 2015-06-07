package at.skert.swe.ue7.contracts.data;

import java.lang.reflect.Type;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IRemoteRepository extends Remote {
  void create(IEntity entity) throws RemoteException;
  void update(IEntity entity) throws RemoteException;
  void delete(IEntity entity) throws RemoteException;
  <T extends IEntity> List<T> getAllForType(Type type) throws RemoteException;
  void registerConsumer(ICaaSConsumer consumer)  throws RemoteException;
}
