package at.skert.swe.ue7.view;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.application.Application;
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
import at.skert.swe.ue7.view.page.MenuPlanManagementPage;
import at.skert.swe.ue7.view.page.OrderPage;
import at.skert.swe.ue7.view.page.UserManagementPage;
import at.skert.swe.ue7.viewmodel.OrderViewModel;

public class RemoteMainView extends Application {
  private IRemoteRepository repository;
  
  @Override
  public void start(Stage primaryStage) throws Exception {
    String hostPort = "localhost:1099";
    System.out.println("Lookup of rmi://" + hostPort + "/RepositoryService");
    repository = (IRemoteRepository) Naming.lookup("rmi://" + hostPort + "/RepositoryService");
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
    IRepository<Order> orderRepository = new GenericRemoteRepositoryConsumer<Order>(Order.class, repository);
    OrderViewModel viewModel = new OrderViewModel();
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
    UserManagementPage control = new UserManagementPage(new UserManagementInteractions(userRepository).getIntegratedViewModel());
    HBox.setHgrow(control, Priority.ALWAYS);
    VBox.setVgrow(control, Priority.ALWAYS);
    tab.setContent(control);
    return tab;
  }

  private Tab createMenuPlanManagementPage() throws MalformedURLException, RemoteException, NotBoundException {
    Tab tab = new Tab("Menüverwaltung");
    IRepository<MenuCategory> menuCategoryRepository = new GenericRemoteRepositoryConsumer<MenuCategory>(MenuCategory.class, repository);
    IRepository<Menu>  menuRepository = new GenericRemoteRepositoryConsumer<Menu>(Menu.class, repository);
    MenuPlanManagementPage control = new MenuPlanManagementPage(new MenuPlanManagementInteractions(menuRepository, menuCategoryRepository).getIntegratedViewModel());
    HBox.setHgrow(control, Priority.ALWAYS);
    VBox.setVgrow(control, Priority.ALWAYS);
    tab.setContent(control);
    return tab;
  }
  
  public static void main(String[] args) {
    launch();
  }
}
