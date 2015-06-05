package at.skert.swe.ue6.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import at.skert.swe.ue6.data.MenuCategoryRepository;
import at.skert.swe.ue6.data.MenuRepository;
import at.skert.swe.ue6.data.OrderRepository;
import at.skert.swe.ue6.data.UserRepository;
import at.skert.swe.ue6.integration.MenuPlanManagementInteractions;
import at.skert.swe.ue6.integration.UserManagementInteractions;
import at.skert.swe.ue6.view.page.MenuPlanManagementPage;
import at.skert.swe.ue6.view.page.OrderPage;
import at.skert.swe.ue6.view.page.UserManagementPage;
import at.skert.swe.ue6.viewmodel.OrderViewModel;

public class MainView extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    TabPane tabPane = new TabPane();
    tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    tabPane.getTabs().addAll(createOrderTab(), createUserManagementTab(),
        createMenuPlanManagementPage());
    Scene scene = new Scene(tabPane, 600, 600);
    scene.getStylesheets().add(
        getClass().getResource("css/main.css").toExternalForm());
    primaryStage.setScene(scene);
    primaryStage.setMinWidth(600);
    primaryStage.setMinHeight(600);
    primaryStage.setTitle("CaaS - Administration");
    primaryStage.show();
  }

  private Tab createOrderTab() {
    Tab tab = new Tab("Bestellungen");
    OrderRepository orderRepository = new OrderRepository(new MenuRepository(
        new MenuCategoryRepository()), new UserRepository());
    OrderViewModel viewModel = new OrderViewModel();
    viewModel.getOrderList().addAll(
        orderRepository.getAll().stream()
            .filter(o -> o.getDateTime().format(DateTimeFormatter.BASIC_ISO_DATE).equals(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE)))
            .collect(Collectors.toList()));
    OrderPage control = new OrderPage(viewModel);
    HBox.setHgrow(control, Priority.ALWAYS);
    VBox.setVgrow(control, Priority.ALWAYS);
    tab.setContent(control);
    return tab;
  }

  private Tab createUserManagementTab() {
    Tab tab = new Tab("Benutzerverwaltung");
    UserManagementPage control = new UserManagementPage(
        new UserManagementInteractions().getIntegratedViewModel());
    HBox.setHgrow(control, Priority.ALWAYS);
    VBox.setVgrow(control, Priority.ALWAYS);
    tab.setContent(control);
    return tab;
  }

  private Tab createMenuPlanManagementPage() {
    Tab tab = new Tab("Menüverwaltung");
    MenuPlanManagementPage control = new MenuPlanManagementPage(
        new MenuPlanManagementInteractions().getIntegratedViewModel());
    HBox.setHgrow(control, Priority.ALWAYS);
    VBox.setVgrow(control, Priority.ALWAYS);
    tab.setContent(control);
    return tab;
  }

  public static void main(String[] args) {
    launch();
  }
}
