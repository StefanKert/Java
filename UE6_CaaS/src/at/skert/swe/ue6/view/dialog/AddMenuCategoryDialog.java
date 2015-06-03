package at.skert.swe.ue6.view.dialog;

import at.skert.swe.ue6.contracts.ActionWithParam;
import at.skert.swe.ue6.contracts.data.MenuCategory;
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
  private Button btnSave;
  private Button btnCancel;
  private ActionWithParam<MenuCategory> addMenuCategoryMethod;

  public String getName() {
    return txtName.getText();
  }

  public AddMenuCategoryDialog() {
    this.primaryStage = new Stage();
    this.primaryStage.setTitle("Kategorie hinzufügen");
    this.primaryStage.initStyle(StageStyle.UTILITY);

    this.primaryStage.setScene(new Scene(createInputGrid()));
  }

  private GridPane createInputGrid() {
    GridPane gridForm = new GridPane();
    gridForm.getStylesheets().add(getClass().getResource("../css/main.css").toExternalForm());
    gridForm.setHgap(10);
    gridForm.setVgap(10);
    gridForm.setPadding(new Insets(25, 25, 25, 25));

    // Title
    gridForm.add(createTitle(), 0, 0, 2, 1);

    // Name
    gridForm.add(new Label("Name:"), 0, 2);
    txtName = new TextField();
    gridForm.add(txtName, 1, 2);

    // Save
    gridForm.add(createSaveButton(), 0, 4);

    // Cancel
    btnCancel = createButtonCancel();
    GridPane.setHalignment(btnCancel, HPos.RIGHT);
    gridForm.add(btnCancel, 1, 4);

    return gridForm;
  }

  private Text createTitle() {
    Text scenetitle = new Text("Kategorie hinzufügen");
    scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
    GridPane.setHalignment(scenetitle, HPos.CENTER);
    return scenetitle;
  }

  private Button createSaveButton() {
    btnSave = new Button("Speichern");
    btnSave.getStyleClass().addAll("done-button");
    btnSave.setOnAction(Event -> {
      if (txtName.getText().isEmpty())
        return;

      addMenuCategoryMethod.invoke(new MenuCategory(txtName.getText()));
      primaryStage.close();
    });

    return btnSave;
  }

  private Button createButtonCancel() {
    btnCancel = new Button("Abbrechen");
    btnCancel.getStyleClass().addAll("delete-button");
    btnCancel.setOnAction(Event -> {
      primaryStage.close();
    });

    return btnCancel;
  }

  public void showAndWait() {
    primaryStage.showAndWait();
  }

  public void setAddMenuCategoryMethod(ActionWithParam<MenuCategory> addMenuCategoryMethod) {
    this.addMenuCategoryMethod = addMenuCategoryMethod;
  }
}
