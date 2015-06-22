package at.skert.swe.ue7.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import at.skert.swe.ue7.contracts.data.IRemoteRepository;
import at.skert.swe.ue7.contracts.data.IRepository;
import at.skert.swe.ue7.contracts.data.Menu;
import at.skert.swe.ue7.contracts.data.MenuCategory;
import at.skert.swe.ue7.contracts.data.Order;
import at.skert.swe.ue7.contracts.data.User;
import at.skert.swe.ue7.data.MenuCategoryRepository;
import at.skert.swe.ue7.data.MenuRepository;
import at.skert.swe.ue7.data.OrderRepository;
import at.skert.swe.ue7.data.UserRepository;
import at.skert.swe.ue7.data.remote.RemoteRepository;

public class RmiService {

  private IRepository<MenuCategory> menuCategoryRepository;
  private IRepository<Menu> menuRepository;
  private IRepository<User> userRepository;
  private IRepository<Order> orderRepository;
  private RemoteRepository remoteRepository;

  public RmiService(){
    menuCategoryRepository = new MenuCategoryRepository();
    menuRepository = new MenuRepository(new MenuCategoryRepository());
    userRepository = new UserRepository();
    orderRepository = new OrderRepository(menuRepository, userRepository);
    remoteRepository = new RemoteRepository(menuRepository, menuCategoryRepository, userRepository, orderRepository);
  }
  
  public void launch() throws RemoteException, MalformedURLException {
    String hostPort = "localhost:1099";
    System.out.println("Starting internal rmi registry");
    LocateRegistry.createRegistry(1099);
    IRemoteRepository remoteRepositoryStub = (IRemoteRepository) UnicastRemoteObject.exportObject(remoteRepository, 0);
    Naming.rebind("rmi://" + hostPort + "/CaaSService", remoteRepositoryStub);
    System.out.println("RepositoryService running, waiting for connections");
  }
}
