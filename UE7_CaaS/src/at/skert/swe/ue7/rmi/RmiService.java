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

  public static void main(String[] args) throws RemoteException, MalformedURLException {
    String hostPort = "localhost:1099";
    System.out.println("Starting internal rmi registry");
    LocateRegistry.createRegistry(1099);
    MenuCategoryRepository menuCategoryRepository = new MenuCategoryRepository();
    menuCategoryRepository.create(new MenuCategory("RMI Menükategorie"), () -> {}, exception -> {});
    MenuRepository menuRepository = new MenuRepository(new MenuCategoryRepository());
    menuRepository.create(new Menu("RMI Menü", 12.0, menuCategoryRepository.getAll().get(0)), () -> {}, exception -> {});
    UserRepository userRepository = new UserRepository();
    userRepository.create(new User("TesterÜberRMI", "PasswordüberRMI", "RMI", "RIMINI", true), () -> {}, exception -> {});
    OrderRepository orderRepository = new OrderRepository(menuRepository, userRepository);
    orderRepository.create(new Order(menuRepository.getAll().get(0), userRepository.getAll().get(0), LocalDateTime.now()), () -> {}, exception -> {});
    RemoteRepository menu = new RemoteRepository(menuRepository, menuCategoryRepository, userRepository, orderRepository);
    IRemoteRepository menuRepositoryStub = (IRemoteRepository) UnicastRemoteObject.exportObject(menu, 0);
    Naming.rebind("rmi://" + hostPort + "/RepositoryService", menuRepositoryStub);
    System.out.println("RepositoryService running, waiting for connections");
  }
}
