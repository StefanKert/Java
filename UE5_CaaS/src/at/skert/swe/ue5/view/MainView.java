package at.skert.swe.ue5.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MainView extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    TabPane tabPane = new TabPane();
    Tab tab = new Tab();
    tab.setText("new tab");
    UserManagementControl control = new UserManagementControl();
    tab.setContent(control);
    tabPane.getTabs().add(tab);
    Scene scene = new Scene(tabPane, 500, 500);
    scene.getStylesheets().add(getClass().getResource("css/main.css").toExternalForm());
    primaryStage.setScene(scene);
    primaryStage.setMinWidth(140);
    primaryStage.setMinHeight(160);
    primaryStage.setTitle("CaaS - Administration");
    primaryStage.show();
  }
  
  public static void main(String[] args){
    launch();
  }
}
