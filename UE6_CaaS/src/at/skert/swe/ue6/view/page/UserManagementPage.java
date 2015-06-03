package at.skert.swe.ue6.view.page;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import at.skert.swe.ue6.contracts.data.User;
import at.skert.swe.ue6.view.utils.UserListCellWithButtons;
import at.skert.swe.ue6.viewmodel.UserManagementViewModel;

public class UserManagementPage extends AnchorPane {
  private UserManagementViewModel viewModel;

  public UserManagementPage(UserManagementViewModel viewModel) {
    super();
    this.viewModel = viewModel;
    this.getChildren().addAll(createMenuView());
  }
  
  private Pane createMenuView(){
    VBox pane = new VBox();
    pane.getChildren().addAll(createTitleHeadBox("Benutzer", createAddButton()), createListView());
    AnchorPane.setTopAnchor(pane, 10.0);
    AnchorPane.setLeftAnchor(pane, 10.0);
    AnchorPane.setRightAnchor(pane, 10.0);
    return pane;
  }
  
  private ListView<User> createListView(){
    ListView<User> listView = new ListView<User>();
    listView.setCellFactory(p -> { 
      UserListCellWithButtons cell = new UserListCellWithButtons(true, true);
      cell.setEditMethod(user -> viewModel.editUser(user));
      cell.setDeleteMethod(user -> viewModel.deleteUser(user));
      cell.setActivateMethod(user -> viewModel.activateUser(user));
      cell.setDeactivateMethod(user -> viewModel.deactivateUser(user));
      return cell;
    });
    listView.setItems(viewModel.getUserList());
    return listView;
  }
  
  private Button createAddButton(){
    Button addButton = new Button();
    addButton.setOnAction(event -> {
      this.viewModel.addUser();
    });    
    addButton.getStyleClass().addAll("image-button", "add-button");
    AnchorPane.setTopAnchor(addButton, 10.0);
    AnchorPane.setRightAnchor(addButton, 10.0);
    return addButton;
  }
  
  private HBox createTitleHeadBox(String title, Button addButton){
    Label titleLabel = new Label(title);
    titleLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
    titleLabel.setMaxWidth(Double.MAX_VALUE);
    HBox.setHgrow(titleLabel, Priority.ALWAYS);
    HBox.setHgrow(addButton, Priority.ALWAYS);
    HBox titlePane = new HBox(titleLabel, addButton);
    titlePane.setPadding(new Insets(10)); 
    return titlePane;
  }
}
