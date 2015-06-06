package at.skert.swe.ue7.view.utils;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import at.skert.swe.ue7.contracts.ActionWithParam;

public class GenericListCellWithButtons<T> extends ListCell<T> {
  protected HBox box = new HBox();
  protected Label label = new Label();
  protected Button editButton = new Button();
  protected Button deleteButton = new Button();
  protected ActionWithParam<T> editMethod;
  protected ActionWithParam<T> deleteMethod;

  public GenericListCellWithButtons(boolean showEdit, boolean showDelete) {
    editButton.getStyleClass().addAll("image-button", "edit-button");
    if (!showEdit)
      editButton.setVisible(false);
    deleteButton.getStyleClass().addAll("image-button", "delete-button");
    if (!showDelete)
      deleteButton.setVisible(false);
    label.setMaxWidth(Double.MAX_VALUE);
    HBox.setHgrow(label, Priority.ALWAYS);
    VBox.setVgrow(label, Priority.ALWAYS);
  }

  @Override
  protected void updateItem(T entity, boolean bln) {
    super.updateItem(entity, bln);
    if (entity != null) {
      label.setText(entity.toString());
      if (editMethod != null)
        editButton.setOnAction(event -> editMethod.invoke(entity));
      if (deleteMethod != null)
        deleteButton.setOnAction(event -> deleteMethod.invoke(entity));
      box.getChildren().clear();
      box.getChildren().addAll(label, editButton, deleteButton);
      setGraphic(box);
    } else {
      setGraphic(null);
    }
  }

  public void setEditMethod(ActionWithParam<T> editMethod) {
    this.editMethod = editMethod;
  }

  public void setDeleteMethod(ActionWithParam<T> deleteMethod) {
    this.deleteMethod = deleteMethod;
  }
}
