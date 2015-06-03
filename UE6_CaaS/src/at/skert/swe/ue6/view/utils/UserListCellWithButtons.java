package at.skert.swe.ue6.view.utils;

import javafx.scene.control.Button;
import at.skert.swe.ue6.contracts.ActionWithParam;
import at.skert.swe.ue6.contracts.data.User;

public class UserListCellWithButtons extends GenericListCellWithButtons<User> {
  private Button lockButton = new Button();
  private ActionWithParam<User> activateMethod;
  private ActionWithParam<User> deactivateMethod;
  
  public UserListCellWithButtons(boolean showEdit, boolean showDelete) {
    super(showEdit, showDelete);
  }

  @Override
  protected void updateItem(User user, boolean bln) {
    super.updateItem(user, bln);   
    if (user != null)  {
      if (!user.getActivated()) {
        lockButton.getStyleClass().addAll("image-button", "unlock-button");
        if(activateMethod != null)
          lockButton.setOnAction(event -> activateMethod.invoke(user));
      } else {
        lockButton.getStyleClass().addAll("image-button", "lock-button");
        if(deactivateMethod != null)
          lockButton.setOnAction(event -> deactivateMethod.invoke(user));
      }
      box.getChildren().clear();
      box.getChildren().addAll(label, editButton, lockButton, deleteButton);
      setGraphic(box);
    }
    else{
      setGraphic(null);
    }
  }
  
  public void setActivateMethod(ActionWithParam<User> activateMethod){
    this.activateMethod = activateMethod;
  }
  public void setDeactivateMethod(ActionWithParam<User> deactivateMethod){
    this.deactivateMethod = deactivateMethod;
  }
}