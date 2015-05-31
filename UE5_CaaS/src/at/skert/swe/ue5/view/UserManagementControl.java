package at.skert.swe.ue5.view;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class UserManagementControl extends AnchorPane {

  public static class HBoxCell extends HBox {
    Label label = new Label();
    Button editButton = new Button();
    Button lockButton = new Button();
    Button deleteButton = new Button();
    
    HBoxCell(String labelText, String buttonText) {
         super();
         editButton.setId("user-edit-button");
         lockButton.setId("user-lock-button");
         deleteButton.setId("user-delete-button");
         label.setText(labelText);
         label.setMaxWidth(Double.MAX_VALUE);
         HBox.setHgrow(label, Priority.ALWAYS);
         this.getChildren().addAll(label, editButton, lockButton, deleteButton);
    }
}
  
  public UserManagementControl() {
    super();
    List<HBoxCell> list = new ArrayList<>();
    for (int i = 0; i < 12; i++) {
         list.add(new HBoxCell("User" + i, "Button " + i));
    }

    ListView<HBoxCell> listView = new ListView<HBoxCell>();
    ObservableList<HBoxCell> myObservableList = FXCollections.observableList(list);
    listView.setItems(myObservableList);
    this.getChildren().add(listView);
  }
  
  
}
