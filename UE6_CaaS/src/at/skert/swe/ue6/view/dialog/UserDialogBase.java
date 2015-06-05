package at.skert.swe.ue6.view.dialog;

import at.skert.swe.ue6.view.utils.MessageBox;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public abstract class UserDialogBase {
  protected Stage primaryStage;
  protected TextField txtFirstName;
  protected TextField txtLastName;
  protected TextField txtUserName;
  protected PasswordField txtPassword;
  protected CheckBox chkActive;

  protected GridPane createInputGrid() {
    GridPane gridForm = new GridPane();
    gridForm.getStylesheets().add(
        getClass().getResource("../css/main.css").toExternalForm());
    gridForm.setHgap(10);
    gridForm.setVgap(10);
    gridForm.setPadding(new Insets(25, 25, 25, 25));
    gridForm.add(createTitle(), 0, 0, 2, 1);
    gridForm.add(new Label("Vorname:"), 0, 2);
    gridForm.add(createFirstNameTextField(), 1, 2);
    gridForm.add(new Label("Nachname:"), 0, 3);
    gridForm.add(createLastNameTextField(), 1, 3);
    gridForm.add(new Label("Benutzername:"), 0, 4);
    gridForm.add(createUserNameTextField(), 1, 4);
    gridForm.add(new Label("Passwort:"), 0, 5);
    gridForm.add(createPasswordTextField(), 1, 5);
    gridForm.add(new Label("Aktiv:"), 0, 7);
    gridForm.add(createActiveCheckBox(), 1, 7);
    AnchorPane hbox = new AnchorPane();
    HBox.setHgrow(hbox, Priority.ALWAYS);
    Button saveButton = createButtonSave();
    AnchorPane.setLeftAnchor(saveButton, 10.0);
    Button cancelButton = createButtonCancel();
    AnchorPane.setRightAnchor(cancelButton, 10.0);
    hbox.getChildren().addAll(saveButton, cancelButton);
    gridForm.add(hbox, 0, 9, 2, 1);
    return gridForm;
  }

  protected abstract Text createTitle();

  protected Text createTitle(String title) {
    Text scenetitle = new Text(title);
    scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
    GridPane.setHalignment(scenetitle, HPos.CENTER);
    return scenetitle;
  }

  private TextField createFirstNameTextField() {
    txtFirstName = new TextField();
    return txtFirstName;
  }

  private TextField createLastNameTextField() {
    txtLastName = new TextField();
    return txtLastName;
  }

  private TextField createUserNameTextField() {
    txtUserName = new TextField();
    return txtUserName;
  }

  private TextField createPasswordTextField() {
    txtPassword = new PasswordField();
    return txtPassword;
  }

  private CheckBox createActiveCheckBox() {
    chkActive = new CheckBox();
    return chkActive;
  }

  protected Button createButtonSave() {
    Button btnSave = new Button("Speichern");
    btnSave.getStyleClass().addAll("done-white-button");
    return btnSave;
  }

  protected boolean isDialogValid() {
    if (txtFirstName.getText().isEmpty() || txtLastName.getText().isEmpty()
        || txtUserName.getText().isEmpty() || txtPassword.getText().isEmpty()){
      String message = "Die folgenden falschen Angaben wurden erkannt:\n";
      if(txtFirstName.getText().isEmpty())
        message += "\nEs wurde kein Vorname angegeben.";
      if(txtLastName.getText().isEmpty())
        message += "\nEs wurde kein Nachname angegeben.";
      if(txtUserName.getText().isEmpty())
        message += "\nEs wurde kein Benutzername angegeben.";
      if(txtPassword.getText().isEmpty())
        message += "\nEs wurde kein Passwort angegeben.";
      MessageBox.showErrorDialog("Fehler beim Speichern.", message);
      return false;
    }
    return true;
  }

  private Button createButtonCancel() {
    Button btnCancel = new Button("Abbrechen");
    btnCancel.getStyleClass().addAll("delete-white-button");
    btnCancel.setOnAction(event -> primaryStage.close());
    GridPane.setHalignment(btnCancel, HPos.RIGHT);
    return btnCancel;
  }

  public void showAndWait() {
    primaryStage.showAndWait();
  }
}
