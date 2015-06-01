package at.skert.swe.ue6.view;

import java.util.ArrayList;
import java.util.List;

import at.skert.swe.ue6.contracts.User;
import at.skert.swe.ue6.viewmodel.UserManagementViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class UserManagementPage extends AnchorPane {
  private UserManagementViewModel viewModel;
  private Button addButton = new Button();;
  private ListView<User> listView = new ListView<User>();

  private class UserListCell extends ListCell<User> {
    public UserListCell(){}

    @Override
    protected void updateItem(User user, boolean bln) {
      super.updateItem(user, bln);
      
      if (user != null)  {
        HBox box = new HBox();        
        Label label = new Label();   
        Button editButton = new Button();
        editButton.setId("user-edit-button");
        Button lockButton = new Button();
        Button deleteButton = new Button();        
        deleteButton.setId("user-delete-button");
        deleteButton.setOnAction(event -> {
          viewModel.deleteUser(user);
        });
        label.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(label, Priority.ALWAYS);
        if (user.getIsLocked()) {
          lockButton.setId("user-unlock-button");
          lockButton.setOnAction(event -> {
            viewModel.unlockUser(user);
          });
        } else {
          lockButton.setId("user-lock-button");
          lockButton.setOnAction(event -> {
            viewModel.lockUser(user);
          });
        }
        label.setText(user.getUserName());
        box.getChildren().addAll(label, editButton, lockButton, deleteButton);
        setGraphic(box);
      }
      else{
        setGraphic(null);
      }
    }
  }

  public UserManagementPage(UserManagementViewModel viewModel) {
    super();
    this.viewModel = viewModel;
    initAddButton();
    initListView();
    this.getChildren().addAll(listView, addButton);
  }
  
  private void initListView(){
    listView.setCellFactory(p -> new UserListCell());
    listView.setItems(viewModel.getUserList());

    AnchorPane.setTopAnchor(listView, 50.0);
    AnchorPane.setLeftAnchor(listView, 10.0);
    AnchorPane.setRightAnchor(listView, 10.0);
  }
  
  private void initAddButton(){
    addButton.setOnAction(event -> {
      this.viewModel.addUser();
    });    
    addButton.setId("user-add-button");
    AnchorPane.setTopAnchor(addButton, 10.0);
    AnchorPane.setRightAnchor(addButton, 10.0);
  }
}
