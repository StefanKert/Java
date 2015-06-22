package at.skert.swe.ue7.view.dialog;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import at.skert.swe.ue7.contracts.ActionWithParam;
import at.skert.swe.ue7.contracts.data.User;

public class EditUserDialog extends UserDialogBase {
  private ActionWithParam<User> updateUserMethod;
  private User user;

  public EditUserDialog(User user) {
    super();
    this.user = user;
    primaryStage = new Stage();
    primaryStage.setTitle("Benutzer editieren");
    primaryStage.initStyle(StageStyle.UTILITY);
    primaryStage.setScene(new Scene(createInputGrid()));
    setDataForUser();
  }

  private void setDataForUser() {
    txtFirstName.setText(user.getFirstname());
    txtLastName.setText(user.getLastname());
    txtUserName.setText(user.getUsername());
    txtPassword.setText(user.getPassword());
    chkActive.setSelected(user.getActivated());
  }

  @Override
  protected Text createTitle() {
    return super.createTitle("Benutzer editieren");
  }

  @Override
  protected Button createButtonSave() {
    Button btnSave = super.createButtonSave();
    btnSave.setOnAction(event -> {
      if (!isDialogValid())
        return;
      user.setFirstname(txtLastName.getText());
      user.setLastname(txtLastName.getText());
      user.setUsername(txtUserName.getText());
      user.setPassword(txtPassword.getText());
      user.setActivated(chkActive.isSelected());
      updateUserMethod.invoke(user);
    });
    return btnSave;
  }

  public void setUpdateUserMethod(ActionWithParam<User> updateUserMethod) {
    this.updateUserMethod = updateUserMethod;
  }
}
