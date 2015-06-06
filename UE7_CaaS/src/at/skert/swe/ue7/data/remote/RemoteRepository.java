package at.skert.swe.ue7.data.remote;

import java.lang.reflect.Type;
import java.rmi.RemoteException;
import java.util.List;

import at.skert.swe.ue7.contracts.data.IEntity;
import at.skert.swe.ue7.contracts.data.IRemoteRepository;
import at.skert.swe.ue7.contracts.data.IRepository;
import at.skert.swe.ue7.contracts.data.Menu;
import at.skert.swe.ue7.contracts.data.MenuCategory;
import at.skert.swe.ue7.contracts.data.Order;
import at.skert.swe.ue7.contracts.data.User;

public class RemoteRepository implements IRemoteRepository {
  private IRepository<Menu> menuRepository;
  private IRepository<MenuCategory> menuCategoryRepository;
  private IRepository<User> userRepository;
  private IRepository<Order> orderRepository;
  
  public RemoteRepository(IRepository<Menu> menuRepository, IRepository<MenuCategory> menuCategoryRepository, IRepository<User> userRepository, IRepository<Order> orderRepository){
    this.menuRepository = menuRepository;
    this.menuCategoryRepository = menuCategoryRepository;
    this.userRepository = userRepository;
    this.orderRepository = orderRepository;
  }

  @Override
  public void create(IEntity entity) throws RemoteException {
    if(entity instanceof Menu)
      create((Menu) entity);
    else if(entity instanceof MenuCategory)
      create((MenuCategory) entity);
    else if(entity instanceof User)
      create((User) entity);
    else if(entity instanceof Order)
      create((Order) entity);
    else
      throw new RemoteException("Type " + entity.getClass().getName() + " not a valid type.");
 }
  
  private void create(Menu menu) throws RemoteException {
     this.menuRepository.create(menu, () -> { }, exception -> {
       throw new RuntimeException(exception);
     });
  }

  private void create(MenuCategory menuCategory) throws RemoteException {
    this.menuCategoryRepository.create(menuCategory, () -> { }, exception -> {
      throw new RuntimeException(exception);
    });
  }

  private void create(User user) throws RemoteException {
    this.userRepository.create(user, () -> { }, exception -> {
      throw new RuntimeException(exception);
    });
  }

  private void create(Order order) throws RemoteException {
    this.orderRepository.create(order, () -> { }, exception -> {
      throw new RuntimeException(exception);
    });
  }

  @Override
  public void update(IEntity entity) throws RemoteException {
    if(entity instanceof Menu)
      update((Menu) entity);
    else if(entity instanceof MenuCategory)
      update((MenuCategory) entity);
    else if(entity instanceof User)
      update((User) entity);
    else if(entity instanceof Order)
      update((Order) entity);
    else
      throw new RemoteException("Type " + entity.getClass().getName() + " not a valid type.");
 }

  private void update(MenuCategory menuCategory) throws RemoteException {
    this.menuCategoryRepository.update(menuCategory, () -> { }, exception -> {
      throw new RuntimeException(exception);
    });
  }

  private void update(Menu menu) throws RemoteException {
    this.menuRepository.update(menu, () -> { }, exception -> {
      throw new RuntimeException(exception);
    });
  }

  private void update(User user) throws RemoteException {
    this.userRepository.update(user, () -> { }, exception -> {
      throw new RuntimeException(exception);
    });
  }

  private void update(Order order) throws RemoteException {
    this.orderRepository.update(order, () -> { }, exception -> {
      throw new RuntimeException(exception);
    });
  }

  @Override
  public void delete(IEntity entity) throws RemoteException {
    if(entity instanceof Menu)
      delete((Menu) entity);
    else if(entity instanceof MenuCategory)
      delete((MenuCategory) entity);
    else if(entity instanceof User)
      delete((User) entity);
    else if(entity instanceof Order)
      delete((Order) entity);
    else
      throw new RemoteException("Type " + entity.getClass().getName() + " not a valid type.");
 }

  private void delete(MenuCategory menuCategory) throws RemoteException {
    this.menuCategoryRepository.delete(menuCategory, () -> { }, exception -> {
      throw new RuntimeException(exception);
    });
  }

  private void delete(Menu menu) throws RemoteException {
    this.menuRepository.delete(menu, () -> { }, exception -> {
      throw new RuntimeException(exception);
    });
  }

  private void delete(User user) throws RemoteException {
    this.userRepository.delete(user, () -> { }, exception -> {
      throw new RuntimeException(exception);
    });
  }

  private void delete(Order order) throws RemoteException {
    this.orderRepository.delete(order, () -> { }, exception -> {
      throw new RuntimeException(exception);
    });
  }

  @Override
  public <T extends IEntity> List<T> getAllForType(Type type) throws RemoteException {
    if(type.getTypeName().equals(Menu.class.getTypeName()))
      return (List<T>) menuRepository.getAll();
    else if(type.getTypeName().equals(MenuCategory.class.getTypeName()))
      return (List<T>) menuCategoryRepository.getAll();
    else if(type.getTypeName().equals(User.class.getName()))
      return (List<T>) userRepository.getAll();
    else if(type.getTypeName().equals(Order.class.getTypeName()))
      return (List<T>) orderRepository.getAll();
    else
      throw new RemoteException("Type " + type.getTypeName() + " not a valid type.");
  }
}
 