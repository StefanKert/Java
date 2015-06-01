package at.skert.swe.ue6.integration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import at.skert.swe.ue6.contracts.IRepository;
import at.skert.swe.ue6.contracts.Menu;
import at.skert.swe.ue6.contracts.MenuCategory;
import at.skert.swe.ue6.contracts.User;
import at.skert.swe.ue6.contracts.exceptions.EntityNotAddedException;
import at.skert.swe.ue6.data.MenuCategoryRepository;
import at.skert.swe.ue6.data.MenuRepository;
import at.skert.swe.ue6.data.UserRepository;
import at.skert.swe.ue6.viewmodel.MenuPlanManagementViewModel;
import at.skert.swe.ue6.viewmodel.UserManagementViewModel;

public class UserManagementInteractions {
  UserManagementViewModel viewModel;
  IRepository<User> userRepository;
  
  public UserManagementInteractions(){
    this.viewModel = new UserManagementViewModel();
    this.userRepository = new UserRepository();
  }
  
  public UserManagementViewModel getIntegratedViewModel() {
    viewModel.setAddUserMethod(() -> addUser());
    viewModel.setDeletUserMethod(user -> deleteUser(user));
    viewModel.setLockUserMethod(user -> lockUser(user));
    viewModel.setUnlockUserMethod(user -> unlockUser(user));
    viewModel.getUserList().addAll(userRepository.getAll());
    return viewModel;
  }
  
  public void addUser(){
    //TODO: Dialog einbauen für das hinzufügen des Users.
    long id = new Random().nextLong();
    User user = new User(id, "Testuser" + id, "Testpassword1");
    userRepository.create(user);
    viewModel.getUserList().clear();
    viewModel.getUserList().addAll(userRepository.getAll());
  }
  
  
  public void deleteUser(User user){
    viewModel.getUserList().removeIf(x -> x.getId() == user.getId());
  }
  
  public void lockUser(User user){
    List<User> tempList = new ArrayList<User>(viewModel.getUserList());
    user.setIsLocked(true);
    viewModel.getUserList().clear();
    viewModel.getUserList().addAll(tempList);
  }
  
  public void unlockUser(User user){
    List<User> tempList = new ArrayList<User>(viewModel.getUserList());
    user.setIsLocked(false);
    viewModel.getUserList().clear();
    viewModel.getUserList().addAll(tempList);
  }
  
}
