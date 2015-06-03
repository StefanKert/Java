package at.skert.swe.ue6.view.dialog;

import java.time.LocalDate;
import java.util.List;

import at.skert.swe.ue6.contracts.ActionWithParam;
import at.skert.swe.ue6.contracts.data.Menu;
import at.skert.swe.ue6.contracts.data.MenuCategory;
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
    private Button btnSave;
    private Button btnCancel;
    private ComboBox<MenuCategory> comboCategory;
    private ActionWithParam<Menu> addMenuMethod;

    public String getName() {
        return txtName.getText();
    }

    public double getPrice() {
        // Ignore input differences (17,10 and 17.10)
        return Double.valueOf(txtPrice.getText().replace(',', '.'));
    }

    public boolean isLimitedToTimePeriod() {
        return chkLimited.isSelected();
    }

    public LocalDate getFrom() {
        return dateFrom.getValue();
    }

    public LocalDate getTo() {
        return dateTo.getValue();
    }
    
    public MenuCategory getCategory() {
        return comboCategory.getSelectionModel().getSelectedItem();
    }

    public AddMenuDialog(List<MenuCategory> categories) {
        this.primaryStage = new Stage();
        this.primaryStage.setTitle("Hauptspeise hinzufügen");
        this.primaryStage.initStyle(StageStyle.UTILITY);
        
        this.primaryStage.setScene(new Scene(createInputGrid(categories)));
    }

    private GridPane createInputGrid(List<MenuCategory> categories) {
        GridPane gridForm = new GridPane();
        gridForm.getStylesheets().add(getClass().getResource("../css/main.css").toExternalForm());
        gridForm.setHgap(10);
        gridForm.setVgap(10);
        gridForm.setPadding(new Insets(25, 25, 50, 25));

        // Title
        gridForm.add(createTitle(), 0, 0, 2, 1);
        
        // Name
        gridForm.add(new Label("Name:"), 0, 2);
        txtName = new TextField();
        gridForm.add(txtName, 1, 2);
        
        // Price
        gridForm.add(new Label("Preis in €:"), 0, 3);
        txtPrice = new TextField();
        gridForm.add(txtPrice, 1, 3);

        // Category
        gridForm.add(new Label("Kategorie:"), 0, 4);
        comboCategory = new ComboBox<MenuCategory>(FXCollections.observableArrayList(categories));
        gridForm.add(comboCategory, 1, 4);
        
        
        // Limited to time period
        gridForm.add(new Label("Zeitlich begrenzt:"), 0, 5);
        gridForm.add(createCheckboxLimitedToTimePeriod(), 1, 5);
        
        // From
        gridForm.add(new Label("Von:"), 0, 6);
        dateFrom = new DatePicker();
        dateFrom.setDisable(true);
        gridForm.add(dateFrom, 1, 6);
        
        // To
        gridForm.add(new Label("Bis:"), 0, 7);
        dateTo = new DatePicker();
        dateTo.setDisable(true);
        gridForm.add(dateTo, 1, 7);
        
        // Save
        gridForm.add(createSaveButton(), 0, 9); 
        
        // Cancel
        btnCancel = createButtonCancel();
        GridPane.setHalignment(btnCancel, HPos.RIGHT);
        gridForm.add(btnCancel, 1, 9);
        
        return gridForm;
    }

    private Button createSaveButton() {
        btnSave = new Button("Speichern");
        btnSave.getStyleClass().addAll("done-button");
        btnSave.setOnAction(Event -> {
                if(txtName.getText().isEmpty() || txtPrice.getText().isEmpty() || !isPriceValid()
                        || comboCategory.getSelectionModel().getSelectedItem() == null
                        || (chkLimited.isSelected() && (dateFrom.getValue() == null || dateTo.getValue() == null)))
                    return;
                
                addMenuMethod.invoke(new Menu(getName(), getPrice(), getCategory(), getFrom(), getTo()));
                primaryStage.close();                       
          });   
        
        return btnSave;
    }

    private Button createButtonCancel() {
        btnCancel = new Button("Abbrechen");
        btnCancel.getStyleClass().addAll("delete-button");
        GridPane.setHalignment(btnCancel, HPos.RIGHT);
        btnCancel.setOnAction(Event -> {
                primaryStage.close();
        });
        
        return btnCancel;
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
    
    private Text createTitle() {
        Text scenetitle = new Text("Hauptspeise hinzufügen");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(scenetitle, HPos.CENTER);
        return scenetitle;
    }

    private boolean isPriceValid() {
        try {
            getPrice();
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public void showAndWait() {
        primaryStage.showAndWait();
    }

    public void setAddMenuMethod(ActionWithParam<Menu> addMenuMethod) {
      this.addMenuMethod = addMenuMethod;
    }
}