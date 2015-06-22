package at.skert.swe.ue7.view.dialog;

import java.util.List;

import at.skert.swe.ue7.contracts.ActionWithParam;
import at.skert.swe.ue7.contracts.data.Menu;
import at.skert.swe.ue7.contracts.data.MenuCategory;
import at.skert.swe.ue7.utils.Validator;
import at.skert.swe.ue7.view.utils.MessageBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AddMenuDialog {
  private Stage primaryStage;
  private TextField txtName;
  private TextField txtPrice;
  private CheckBox chkLimited;
  private DatePicker dateFrom, dateTo;
  private ComboBox<MenuCategory> comboCategory;
  private ActionWithParam<Menu> addMenuMethod;
  private Validator validator;

  public MenuCategory getSelectedMenuCategory() {
    return comboCategory.getSelectionModel().getSelectedItem();
  }

  public AddMenuDialog(List<MenuCategory> categories) {
    this.validator = new Validator();
    this.primaryStage = new Stage();
    this.primaryStage.setTitle("Hauptspeise hinzufügen");
    this.primaryStage.initStyle(StageStyle.UTILITY);
    this.primaryStage.setScene(new Scene(createInputGrid(categories)));
  }

  private GridPane createInputGrid(List<MenuCategory> categories) {
    GridPane gridForm = new GridPane();
    gridForm.getStylesheets().add(
        getClass().getResource("../css/main.css").toExternalForm());
    gridForm.setHgap(10);
    gridForm.setVgap(10);
    gridForm.setPadding(new Insets(25, 25, 50, 25));
    gridForm.add(createTitle(), 0, 0, 2, 1);
    gridForm.add(new Label("Name:"), 0, 2);
    gridForm.add(createNameTextField(), 1, 2);
    gridForm.add(new Label("Preis in Euro:"), 0, 3);
    gridForm.add(createPriceTextField(), 1, 3);
    gridForm.add(new Label("Kategorie:"), 0, 4);
    gridForm.add(createComboBox(categories), 1, 4);
    gridForm.add(new Label("Zeitlich begrenzt:"), 0, 5);
    gridForm.add(createCheckboxLimitedToTimePeriod(), 1, 5);
    gridForm.add(new Label("Von:"), 0, 6);
    gridForm.add(createDateFromPicker(), 1, 6);
    gridForm.add(new Label("Bis:"), 0, 7);
    gridForm.add(createDateToPicker(), 1, 7);
    gridForm.add(createSaveButton(), 0, 9);
    gridForm.add(createButtonCancel(), 1, 9);
    return gridForm;
  }

  private Text createTitle() {
    Text scenetitle = new Text("Hauptspeise hinzufügen");
    scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
    GridPane.setHalignment(scenetitle, HPos.CENTER);
    return scenetitle;
  }

  private TextField createNameTextField() {
    txtName = new TextField();
    return txtName;
  }

  private TextField createPriceTextField() {
    txtPrice = new TextField();
    return txtPrice;
  }

  private ComboBox<MenuCategory> createComboBox(List<MenuCategory> categories) {
    comboCategory = new ComboBox<MenuCategory>(
        FXCollections.observableArrayList(categories));
    return comboCategory;
  }

  private CheckBox createCheckboxLimitedToTimePeriod() {
    chkLimited = new CheckBox();
    chkLimited.selectedProperty().addListener(new ChangeListener<Boolean>() {
      public void changed(ObservableValue<? extends Boolean> ov,
          Boolean old_val, Boolean new_val) {
        dateTo.setDisable(!new_val);
        dateFrom.setDisable(!new_val);
      }
    });

    return chkLimited;
  }

  private DatePicker createDateFromPicker() {
    dateFrom = new DatePicker();
    dateFrom.setDisable(true);
    return dateFrom;
  }

  private DatePicker createDateToPicker() {
    dateTo = new DatePicker();
    dateTo.setDisable(true);
    return dateTo;
  }

  private Button createSaveButton() {
    Button btnSave = new Button("Speichern");
    btnSave.getStyleClass().addAll("done-white-button");
    btnSave.setOnAction(Event -> {
      if (!isDialogValid())
        return;

      addMenuMethod.invoke(new Menu(txtName.getText(), Double.valueOf(txtPrice
          .getText().replace(',', '.')), getSelectedMenuCategory(), dateFrom
          .getValue(), dateTo.getValue()));
    });

    return btnSave;
  }

  private boolean isDialogValid() {
    if (txtName.getText().isEmpty()
        || txtPrice.getText().isEmpty()
        || !validator.isDoubleValid(txtPrice.getText())
        || getSelectedMenuCategory() == null
        || (chkLimited.isSelected() && (dateFrom.getValue() == null || dateTo
            .getValue() == null))) {
      String message = "Die folgenden falschen Angaben wurden erkannt:\n";
      if (txtName.getText().isEmpty())
        message += "\nEs wurde kein Name angegeben.";
      if (txtPrice.getText().isEmpty())
        message += "\nEs wurde kein Preis angegeben.";
      if (!validator.isDoubleValid(txtPrice.getText()))
        message += "\nBitte geben Sie für den Preis nur eine gültige Zahl ein.";
      if(getSelectedMenuCategory() == null)
        message += "\nSie haben keine Kategorie ausgewählt.";
      if(chkLimited.isSelected()){
        if (dateFrom.getValue() == null)
          message += "\nEs wurde kein Von-Zeit angegeben.";
        if (dateTo.getValue() == null)
          message += "\nEs wurde kein Bis-Zeit angegeben."; 
      }
      MessageBox.showErrorDialog("Fehler beim Speichern.", message);
      return false;
    } else
      return true;
  }

  private Button createButtonCancel() {
    Button btnCancel = new Button("Abbrechen");
    btnCancel.getStyleClass().addAll("delete-white-button");
    GridPane.setHalignment(btnCancel, HPos.RIGHT);
    btnCancel.setOnAction(Event -> {
      primaryStage.close();
    });

    return btnCancel;
  }

  public void showAndWait() {
    primaryStage.showAndWait();
  }

  public void setAddMenuMethod(ActionWithParam<Menu> addMenuMethod) {
    this.addMenuMethod = addMenuMethod;
  }
  public void close(){
    primaryStage.close();
  }
}