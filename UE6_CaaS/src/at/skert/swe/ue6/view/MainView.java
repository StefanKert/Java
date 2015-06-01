package at.skert.swe.ue6.view;

import at.skert.swe.ue6.contracts.IRepository;
import at.skert.swe.ue6.contracts.Menu;
import at.skert.swe.ue6.contracts.MenuCategory;
import at.skert.swe.ue6.data.MenuCategoryRepository;
import at.skert.swe.ue6.data.MenuRepository;
import at.skert.swe.ue6.data.UserRepository;
import at.skert.swe.ue6.integration.MenuPlanManagementInteractions;
import at.skert.swe.ue6.integration.UserManagementInteractions;
import at.skert.swe.ue6.viewmodel.MenuPlanManagementViewModel;
import at.skert.swe.ue6.viewmodel.UserManagementViewModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MainView extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    TabPane tabPane = new TabPane();
    Tab tab = new Tab();
    tab.setText("Benutzerverwaltung");
    tab.setContent(createUserManagementPage());
    
    Tab menuTab = new Tab();
    menuTab.setText("Menüverwaltung");
    menuTab.setContent(createMenuPlanManagementPage());
    
    tabPane.getTabs().addAll(tab, menuTab);
    Scene scene = new Scene(tabPane, 500, 500);
    scene.getStylesheets().add(getClass().getResource("css/main.css").toExternalForm());
    primaryStage.setScene(scene);
    primaryStage.setMinWidth(140);
    primaryStage.setMinHeight(160);
    primaryStage.setTitle("CaaS - Administration");
    primaryStage.show();
  }
  
  private Pane createUserManagementPage(){
    UserManagementPage control = new UserManagementPage(new UserManagementInteractions().getIntegratedViewModel());
    HBox.setHgrow(control, Priority.ALWAYS);
    VBox.setVgrow(control, Priority.ALWAYS);
    return control;
  }
  
  private Pane createMenuPlanManagementPage(){
    MenuPlanManagementPage control = new MenuPlanManagementPage(new MenuPlanManagementInteractions().getIntegratedViewModel());
    HBox.setHgrow(control, Priority.ALWAYS);
    VBox.setVgrow(control, Priority.ALWAYS);
    return control;
  }
  
  public static void main(String[] args){
    launch();
  }
}
