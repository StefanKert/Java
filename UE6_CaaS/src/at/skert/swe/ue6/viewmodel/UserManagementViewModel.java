package at.skert.swe.ue6.viewmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import at.skert.swe.ue6.contracts.Action;
import at.skert.swe.ue6.contracts.ActionWithParam;
import at.skert.swe.ue6.contracts.Menu;
import at.skert.swe.ue6.contracts.MenuCategory;
import at.skert.swe.ue6.contracts.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserManagementViewModel {
  private final ObservableList<User> userList = FXCollections.observableList(new ArrayList<User>()); 

  private Action addUserMethod;
  private ActionWithParam<User> deleteUserMethod;
  private ActionWithParam<User> lockUserMethod;
  private ActionWithParam<User> unlockUserMethod;

  
  public ObservableList<User> getUserList() {
    return userList;
  }
  
  public void addUser(){
    addUserMethod.invoke();
  }
  
  public void deleteUser(User user){
    deleteUserMethod.invoke(user);
  }
  
  public void lockUser(User user){
    unlockUserMethod.invoke(user);
  }
  
  public void unlockUser(User user){
    unlockUserMethod.invoke(user);
  }
  
  public void setAddUserMethod(Action addUserMethod){
    this.addUserMethod = addUserMethod;
  }
  
  public void setDeletUserMethod(ActionWithParam<User> deleteUserMethod){
    this.deleteUserMethod = deleteUserMethod;
  }

  public void setLockUserMethod(ActionWithParam<User> lockUserMethod){
    this.lockUserMethod = lockUserMethod;
  }
  
  public void setUnlockUserMethod(ActionWithParam<User> unlockUserMethod){
    this.unlockUserMethod = unlockUserMethod;
  }
}
