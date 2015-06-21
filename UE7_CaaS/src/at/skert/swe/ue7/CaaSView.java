package at.skert.swe.ue7;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import at.skert.swe.ue7.contracts.data.IRemoteRepository;
import at.skert.swe.ue7.contracts.data.IRepository;
import at.skert.swe.ue7.contracts.data.Menu;
import at.skert.swe.ue7.contracts.data.MenuCategory;
import at.skert.swe.ue7.contracts.data.Order;
import at.skert.swe.ue7.contracts.data.User;
import at.skert.swe.ue7.data.remote.GenericRemoteRepositoryConsumer;
import at.skert.swe.ue7.integration.MenuPlanManagementInteractions;
import at.skert.swe.ue7.integration.UserManagementInteractions;
import at.skert.swe.ue7.view.UpdateLogic;
import at.skert.swe.ue7.view.page.MenuPlanManagementPage;
import at.skert.swe.ue7.view.page.OrderPage;
import at.skert.swe.ue7.view.page.UserManagementPage;
import at.skert.swe.ue7.viewmodel.OrderViewModel;


public class CaaSView extends Application {
  private IRemoteRepository repository;
  private UpdateLogic logic;
  
  public static void main(String[] args) {
    launch(args);
  }
  
  @Override
  public void start(Stage primaryStage) throws Exception {
    repository = (IRemoteRepository) Naming.lookup("rmi://localhost:1099/RepositoryService");
    logic = new UpdateLogic();
    UnicastRemoteObject.exportObject(logic, 0);
    repository.registerConsumer(logic);
    TabPane tabPane = new TabPane();
    tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    tabPane.getTabs().addAll(createOrderTab(), createUserManagementTab(), createMenuPlanManagementPage());
    Scene scene = new Scene(tabPane, 600, 600);
    scene.getStylesheets().add(
        getClass().getResource("view/css/main.css").toExternalForm());
    primaryStage.setScene(scene);
    primaryStage.setMinWidth(600);
    primaryStage.setMinHeight(600);
    primaryStage.setTitle("CaaS - Administration");
    primaryStage.show();
  }
  
  private Tab createOrderTab() {
    Tab tab = new Tab("Bestellungen");
    IRepository<Order> orderRepository = new GenericRemoteRepositoryConsumer<Order>(Order.class, repository);
    OrderViewModel viewModel = new OrderViewModel();
    logic.setOrdersUpdatedMethod(() -> {
      Platform.runLater(() -> {
        viewModel.getOrderList().clear();
        viewModel.getOrderList().addAll(orderRepository.getAll());
      });
    });
    viewModel.getOrderList().addAll(orderRepository.getAllByPredicate(o -> o.getDateTime().format(DateTimeFormatter.BASIC_ISO_DATE).equals(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE))));
    OrderPage control = new OrderPage(viewModel);
    HBox.setHgrow(control, Priority.ALWAYS);
    VBox.setVgrow(control, Priority.ALWAYS);
    tab.setContent(control);
    return tab;
  }

  private Tab createUserManagementTab() {
    Tab tab = new Tab("Benutzerverwaltung");
    IRepository<User> userRepository = new GenericRemoteRepositoryConsumer<User>(User.class, repository);
    UserManagementInteractions interactions = new UserManagementInteractions(userRepository);
    logic.setUsersUpdatedMethod(() -> {
      Platform.runLater(() -> interactions.refreshUsers()); 
     });
    UserManagementPage control = new UserManagementPage(interactions.getIntegratedViewModel());
    HBox.setHgrow(control, Priority.ALWAYS);
    VBox.setVgrow(control, Priority.ALWAYS);
    tab.setContent(control);
    return tab;
  }

  private Tab createMenuPlanManagementPage() throws MalformedURLException, RemoteException, NotBoundException {
    Tab tab = new Tab("Menüverwaltung");
    IRepository<MenuCategory> menuCategoryRepository = new GenericRemoteRepositoryConsumer<MenuCategory>(MenuCategory.class, repository);
    IRepository<Menu>  menuRepository = new GenericRemoteRepositoryConsumer<Menu>(Menu.class, repository);
    MenuPlanManagementInteractions interactions = new MenuPlanManagementInteractions(menuRepository, menuCategoryRepository);
    logic.setMenuCategoriesUpdatedMethod(() -> {
      Platform.runLater(() -> interactions.refreshMenuCategoryList()); 
     });
    logic.setMenusUpdatedMethod(() -> {
     Platform.runLater(() -> interactions.refreshMenuList()); 
    });
    MenuPlanManagementPage control = new MenuPlanManagementPage(interactions.getIntegratedViewModel());
    HBox.setHgrow(control, Priority.ALWAYS);
    VBox.setVgrow(control, Priority.ALWAYS);
    tab.setContent(control);
    return tab;
  }
}
