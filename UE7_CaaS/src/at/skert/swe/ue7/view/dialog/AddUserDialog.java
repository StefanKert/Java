package at.skert.swe.ue7.view.dialog;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import at.skert.swe.ue7.contracts.ActionWithParam;
import at.skert.swe.ue7.contracts.data.User;

public class AddUserDialog extends UserDialogBase {
  private ActionWithParam<User> addUserMethod;

  public AddUserDialog() {
    this.primaryStage = new Stage();
    this.primaryStage.setTitle("Benutzer hinzufügen");
    this.primaryStage.initStyle(StageStyle.UTILITY);
    this.primaryStage.setScene(new Scene(createInputGrid()));
  }

  @Override
  protected Text createTitle() {
    return super.createTitle("Benutzer hinzufügen");
  }

  @Override
  protected Button createButtonSave() {
    Button btnSave = super.createButtonSave();
    btnSave
        .setOnAction(event -> {
          if (!isDialogValid())
            return;
          User user = new User(txtFirstName.getText(), txtLastName.getText(),
              txtUserName.getText(), txtPassword.getText(), chkActive
                  .isSelected());
          addUserMethod.invoke(user);
          primaryStage.close();
        });
    return btnSave;
  }

  public void setAddUserMethod(ActionWithParam<User> addUserMethod) {
    this.addUserMethod = addUserMethod;
  }
}
