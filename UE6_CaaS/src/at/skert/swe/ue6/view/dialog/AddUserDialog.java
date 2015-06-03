package at.skert.swe.ue6.view.dialog;

import at.skert.swe.ue6.contracts.ActionWithParam;
import at.skert.swe.ue6.contracts.data.User;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AddUserDialog {
  private Stage primaryStage;
  private TextField txtFirstName;
  private TextField txtLastName;
  private TextField txtUserName;
  private PasswordField txtPassword;
  private CheckBox chkActive;
  private ActionWithParam<User> addUserMethod;

  public String getFirstname() {
      return txtFirstName.getText();
  }

  public String getLastname() {
      return txtLastName.getText();
  }

  public String getUsername() {
      return txtUserName.getText();
  }

  public String getPassword() {
      return txtPassword.getText();
  }
  
  public boolean isActive() {
      return chkActive.isSelected();
  }
  
  
  public AddUserDialog() {
      this.primaryStage = new Stage();
      this.primaryStage.setTitle("CaaS - Benutzer hinzufügen");
      this.primaryStage.initStyle(StageStyle.UTILITY);
      
      this.primaryStage.setScene(new Scene( createInputGrid()));
  }

  private GridPane createInputGrid() {
      GridPane gridForm = new GridPane();
      gridForm.getStylesheets().add(getClass().getResource("../css/main.css").toExternalForm());
      gridForm.setHgap(10);
      gridForm.setVgap(10);
      gridForm.setPadding(new Insets(25, 25, 25, 25));


      // Title
      gridForm.add(createTitle(), 0, 0, 2, 1);
              
      // First name
      gridForm.add(new Label("Vorname:"), 0, 2);
      txtFirstName = new TextField();
      gridForm.add(txtFirstName, 1, 2);
      
      // Last name
      gridForm.add(new Label("Nachname:"), 0, 3);
      txtLastName = new TextField();
      gridForm.add(txtLastName, 1, 3);

      // Username
      gridForm.add(new Label("Benutzername:"), 0, 4);
      txtUserName = new TextField();
      gridForm.add(txtUserName, 1, 4);

      // Password
      gridForm.add(new Label("Passwort:"), 0, 5);
      txtPassword = new PasswordField();
      gridForm.add(txtPassword, 1, 5);

      // Active
      gridForm.add(new Label("Aktiv:"), 0, 7);
      chkActive = new CheckBox();
      gridForm.add(chkActive, 1, 7);
      
      gridForm.add(createButtonSave(), 0, 9);        
      
      // Cancel
      Button cancelButton = createButtonCancel();
      GridPane.setHalignment(cancelButton, HPos.RIGHT);
      gridForm.add(cancelButton, 1, 9);
      
      return gridForm;
  }

  private Button createButtonCancel() {
      Button btnCancel = new Button("Abbrechen");
      btnCancel.getStyleClass().addAll("delete-button");
      btnCancel.setOnAction(event -> primaryStage.close());  
      return btnCancel;
  }

  private Button createButtonSave() {
      Button btnSave = new Button("Speichern");
      btnSave.getStyleClass().addAll("done-button");
      btnSave.setOnAction(event -> {
              if(getFirstname().isEmpty() || getLastname().isEmpty() || getUsername().isEmpty() || getPassword().isEmpty())
                  return;
              addUserMethod.invoke(new User(getUsername(), getPassword(), getFirstname(), getLastname(), isActive()));
              primaryStage.close();
      });   
      return btnSave;
  }
  
  private Text createTitle() {
      Text scenetitle = new Text("Benutzer hinzufügen");
      scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
      GridPane.setHalignment(scenetitle, HPos.CENTER);        
      return scenetitle;
  }

  public void showAndWait() {
      primaryStage.showAndWait();
  }
  
  public void setAddUserMethod(ActionWithParam<User> addUserMethod){
    this.addUserMethod = addUserMethod;
  }
}
