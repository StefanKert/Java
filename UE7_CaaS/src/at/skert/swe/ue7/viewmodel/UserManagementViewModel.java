package at.skert.swe.ue7.viewmodel;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import at.skert.swe.ue7.contracts.Action;
import at.skert.swe.ue7.contracts.ActionWithParam;
import at.skert.swe.ue7.contracts.data.User;

public class UserManagementViewModel {
  private final ObservableList<User> userList = FXCollections
      .observableList(new ArrayList<User>());

  private Action addUserMethod;
  private ActionWithParam<User> editUserMethod;
  private ActionWithParam<User> deleteUserMethod;
  private ActionWithParam<User> deactivateUserMethod;
  private ActionWithParam<User> activateUserMethod;

  private Action refreshUsersMethod;

  public void addUser() {
    addUserMethod.invoke();
  }

  public void editUser(User user) {
    editUserMethod.invoke(user);
  }

  public void deleteUser(User user) {
    deleteUserMethod.invoke(user);
  }

  public void deactivateUser(User user) {
    deactivateUserMethod.invoke(user);
  }

  public void activateUser(User user) {
    activateUserMethod.invoke(user);
  }
  
  public void refreshUses(){
    refreshUsersMethod.invoke();
  }

  public ObservableList<User> getUserList() {
    return userList;
  }

  public void setAddUserMethod(Action addUserMethod) {
    this.addUserMethod = addUserMethod;
  }

  public void setEditUserMethod(ActionWithParam<User> editUserMethod) {
    this.editUserMethod = editUserMethod;
  }

  public void setDeletUserMethod(ActionWithParam<User> deleteUserMethod) {
    this.deleteUserMethod = deleteUserMethod;
  }

  public void setDeactivateUserMethod(ActionWithParam<User> deactivateUserMethod) {
    this.deactivateUserMethod = deactivateUserMethod;
  }

  public void setActivateUserMethod(ActionWithParam<User> activateUserMethod) {
    this.activateUserMethod = activateUserMethod;
  }
  
  public void setRefreshUsersMethod(Action refreshUsersMethod) {
    this.refreshUsersMethod = refreshUsersMethod;
  }
}
