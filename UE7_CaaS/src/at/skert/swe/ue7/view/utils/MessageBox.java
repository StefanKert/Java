package at.skert.swe.ue7.view.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import at.skert.swe.ue7.contracts.Action;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class MessageBox {
  public static void showErrorDialog(String title, String headerText,
      String text, Exception exception) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(text);
    // Create expandable Exception.
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    exception.printStackTrace(pw);
    String exceptionText = sw.toString();

    Label label = new Label("Stacktrace:");

    TextArea textArea = new TextArea(exceptionText);
    textArea.setEditable(false);
    textArea.setWrapText(true);

    textArea.setMaxWidth(Double.MAX_VALUE);
    textArea.setMaxHeight(Double.MAX_VALUE);
    GridPane.setVgrow(textArea, Priority.ALWAYS);
    GridPane.setHgrow(textArea, Priority.ALWAYS);

    GridPane expContent = new GridPane();
    expContent.setMaxWidth(Double.MAX_VALUE);
    expContent.add(label, 0, 0);
    expContent.add(textArea, 0, 1);

    // Set expandable Exception into the dialog pane.
    alert.getDialogPane().setExpandableContent(expContent);

    alert.showAndWait();
  }

  public static void showErrorDialog(String title, String text){
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(text);
    alert.showAndWait();
  }
  
  public static void showConfirmDialog(String title, String text, Action onOk,
      Action onCancel) {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(text);

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
      onOk.invoke();
    } else {
      onCancel.invoke();
    }
  }
}
