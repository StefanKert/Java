package at.skert.swe.ue6.view.page;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import at.skert.swe.ue6.contracts.data.Menu;
import at.skert.swe.ue6.contracts.data.MenuCategory;
import at.skert.swe.ue6.view.utils.GenericListCellWithButtons;
import at.skert.swe.ue6.viewmodel.MenuPlanManagementViewModel;

public class MenuPlanManagementPage extends AnchorPane {
  private MenuPlanManagementViewModel viewModel;

  public MenuPlanManagementPage(MenuPlanManagementViewModel viewModel) {
    super();
    this.viewModel = viewModel;
    this.getChildren().addAll(createSplitContainer());
  }
  
  private SplitPane createSplitContainer(){
    SplitPane container = new SplitPane();
    VBox.setVgrow(container, Priority.ALWAYS);
    AnchorPane.setTopAnchor(container, 10.0);
    AnchorPane.setLeftAnchor(container, 10.0);
    AnchorPane.setRightAnchor(container, 10.0);
    container.getItems().addAll(createCategoryView(), createMenuView());
    return container;
  }
  
  private  Pane createCategoryView(){
    VBox pane = new VBox();
    pane.getChildren().addAll(createTitleHeadBox("Kategorie", createAddMenuCategoryButton()), createMenuCategoryListView());
    return pane;
  }
  
  private ListView<MenuCategory> createMenuCategoryListView(){
    ListView<MenuCategory> categoryListView = new ListView<MenuCategory>();
    categoryListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      viewModel.setSelectedMenuCategory(newValue);
    });
    categoryListView.setCellFactory(p -> { 
      GenericListCellWithButtons<MenuCategory> cell = new GenericListCellWithButtons<MenuCategory>(false, true);
      cell.setDeleteMethod(menuCategory -> viewModel.deleteMenuCategory(menuCategory));
      return cell;
    });
    categoryListView.setItems(viewModel.getMenuCategoryList());
    return categoryListView;
  }
  
  private Button createAddMenuCategoryButton(){
    Button addButton = new Button();
    addButton.setOnAction(event -> viewModel.addMenuCategory());    
    addButton.getStyleClass().addAll("image-button", "add-button");
    return addButton;
  }
  
  private Pane createMenuView(){
    VBox pane = new VBox();
    pane.visibleProperty().bind(viewModel.isMenuCategorySelectedProperty());
    pane.getChildren().addAll(createTitleHeadBox("Menü", createAddMenuButton()), createMenuListView());
    return pane;
  }
  
  private ListView<Menu> createMenuListView(){
    ListView<Menu> menuListView = new ListView<Menu>();
    menuListView.setCellFactory(p -> {
      GenericListCellWithButtons<Menu> cell = new GenericListCellWithButtons<Menu>(false, true);
      cell.setDeleteMethod(menu -> viewModel.deleteMenu(menu));
      return cell;
    });
    menuListView.setItems(viewModel.getMenuList());
    return menuListView;
  }
  
  private Button createAddMenuButton(){
    Button addButton = new Button();
    addButton.setOnAction(event -> viewModel.addMenuForCategory());    
    addButton.getStyleClass().addAll("image-button", "add-button");
    return addButton;
  }

  private HBox createTitleHeadBox(String title, Button addButton){
    Label titleLabel = new Label(title);
    titleLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
    titleLabel.setMaxWidth(Double.MAX_VALUE);
    HBox.setHgrow(titleLabel, Priority.ALWAYS);
    HBox.setHgrow(addButton, Priority.ALWAYS);
    HBox titlePane = new HBox(titleLabel, addButton);
    titlePane.setPadding(new Insets(10)); 
    return titlePane;
  }
}
