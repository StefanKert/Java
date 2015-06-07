package at.skert.swe.ue7.view;

import java.io.Serializable;
import java.rmi.RemoteException;
import at.skert.swe.ue7.contracts.Action;
import at.skert.swe.ue7.contracts.data.ICaaSConsumer;

public class UpdateLogic implements ICaaSConsumer, Serializable {
  private static final long serialVersionUID = 4750698470437254608L;
  private Action usersUpdatedMethod;
  private Action ordersUpdatedMethod;
  private Action menusUpdatedMethod;
  private Action menuCategoriesUpdatedMethod;
  
  @Override
  public void usersUpdated() throws RemoteException {
    usersUpdatedMethod.invoke();
  }

  @Override
  public void ordersUpdated() throws RemoteException  {
    ordersUpdatedMethod.invoke();
  }

  @Override
  public void menusUpdated() throws RemoteException {
    menusUpdatedMethod.invoke();
  }

  @Override
  public void menuCategoriesUpdated() throws RemoteException  {
    menuCategoriesUpdatedMethod.invoke();
  }
  
  public void setUsersUpdatedMethod(Action usersUpdatedMethod) {
    this.usersUpdatedMethod = usersUpdatedMethod;
  }

  public void setOrdersUpdatedMethod(Action ordersUpdatedMethod) {
    this.ordersUpdatedMethod = ordersUpdatedMethod;
  }

  public void setMenusUpdatedMethod(Action menusUpdatedMethod) {
    this.menusUpdatedMethod = menusUpdatedMethod;
  }

  public void setMenuCategoriesUpdatedMethod(Action menuCategoriesUpdatedMethod) {
    this.menuCategoriesUpdatedMethod = menuCategoriesUpdatedMethod;
  }
}
