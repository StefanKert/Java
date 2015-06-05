package at.skert.swe.ue6.view.dialog;

import at.skert.swe.ue6.contracts.ActionWithParam;
import at.skert.swe.ue6.contracts.data.MenuCategory;
import at.skert.swe.ue6.view.utils.MessageBox;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AddMenuCategoryDialog {
  private Stage primaryStage;
  private TextField txtName;
  private ActionWithParam<MenuCategory> addMenuCategoryMethod;

  public AddMenuCategoryDialog() {
    this.primaryStage = new Stage();
    this.primaryStage.setTitle("Kategorie hinzufügen");
    this.primaryStage.initStyle(StageStyle.UTILITY);
    this.primaryStage.setScene(new Scene(createInputGrid()));
  }

  private GridPane createInputGrid() {
    GridPane gridForm = new GridPane();
    gridForm.getStylesheets().add(
        getClass().getResource("../css/main.css").toExternalForm());
    gridForm.setHgap(10);
    gridForm.setVgap(10);
    gridForm.setPadding(new Insets(25, 25, 25, 25));
    gridForm.add(createTitle(), 0, 0, 2, 1);
    gridForm.add(new Label("Name:"), 0, 2);
    gridForm.add(createNameTextField(), 1, 2);
    gridForm.add(createSaveButton(), 0, 4);
    gridForm.add(createButtonCancel(), 1, 4);
    return gridForm;
  }

  private Text createTitle() {
    Text scenetitle = new Text("Kategorie hinzufügen");
    scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
    GridPane.setHalignment(scenetitle, HPos.CENTER);
    return scenetitle;
  }

  private TextField createNameTextField() {
    txtName = new TextField();
    return txtName;
  }

  private Button createSaveButton() {
    Button btnSave = new Button("Speichern");
    btnSave.getStyleClass().addAll("done-white-button");
    btnSave.setOnAction(Event -> {
      if (txtName.getText().isEmpty()){
        MessageBox.showErrorDialog("Fehler beim Speichern.", "Es wurde kein Name für die Kategorie angegeben.");
        return;
      }

      addMenuCategoryMethod.invoke(new MenuCategory(txtName.getText()));
      primaryStage.close();
    });

    return btnSave;
  }

  private Button createButtonCancel() {
    Button btnCancel = new Button("Abbrechen");
    btnCancel.getStyleClass().addAll("delete-white-button");
    btnCancel.setOnAction(Event -> {
      primaryStage.close();
    });
    GridPane.setHalignment(btnCancel, HPos.RIGHT);
    return btnCancel;
  }

  public void showAndWait() {
    primaryStage.showAndWait();
  }

  public void setAddMenuCategoryMethod(
      ActionWithParam<MenuCategory> addMenuCategoryMethod) {
    this.addMenuCategoryMethod = addMenuCategoryMethod;
  }
}