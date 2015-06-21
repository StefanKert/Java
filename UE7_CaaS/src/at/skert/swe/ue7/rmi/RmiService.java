package at.skert.swe.ue7.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;

import at.skert.swe.ue7.contracts.data.IRemoteRepository;
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

  public static void launch() throws RemoteException, MalformedURLException {
    String hostPort = "localhost:1099";
    System.out.println("Starting internal rmi registry");
    LocateRegistry.createRegistry(1099);
    MenuCategoryRepository menuCategoryRepository = new MenuCategoryRepository();
    MenuRepository menuRepository = new MenuRepository(new MenuCategoryRepository());
    UserRepository userRepository = new UserRepository();
    OrderRepository orderRepository = new OrderRepository(menuRepository, userRepository);
    RemoteRepository menu = new RemoteRepository(menuRepository, menuCategoryRepository, userRepository, orderRepository);
    IRemoteRepository menuRepositoryStub = (IRemoteRepository) UnicastRemoteObject.exportObject(menu, 0);
    Naming.rebind("rmi://" + hostPort + "/RepositoryService", menuRepositoryStub);
    System.out.println("RepositoryService running, waiting for connections");
  }
}
