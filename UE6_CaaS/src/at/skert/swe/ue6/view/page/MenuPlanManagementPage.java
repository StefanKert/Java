package at.skert.swe.ue6.view.page;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import at.skert.swe.ue6.contracts.Menu;
import at.skert.swe.ue6.contracts.MenuCategory;
import at.skert.swe.ue6.viewmodel.MenuPlanManagementViewModel;

public class MenuPlanManagementPage extends AnchorPane {
  private MenuPlanManagementViewModel viewModel;

  private class MenuCategoryPlanListCell extends ListCell<MenuCategory> {
    public MenuCategoryPlanListCell(){}

    @Override
    protected void updateItem(MenuCategory menuCategory, boolean bln) {
      super.updateItem(menuCategory, bln);     
      if (menuCategory != null)  {
        HBox box = new HBox();        
        Label label = new Label();   
        Button deleteButton = new Button();        
        deleteButton.setId("user-delete-button");
        deleteButton.setOnAction(event -> {
          viewModel.getDeleteMenuCategoryMethod().invoke(menuCategory);
        });
        label.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(label, Priority.ALWAYS);
        label.setText(menuCategory.getName() + " " + menuCategory.getId());
        box.getChildren().addAll(label, deleteButton);
        setGraphic(box);
      }
      else{
        setGraphic(null);
      }
    }
  }
  
  private class MenuPlanListCell extends ListCell<Menu> {
    public MenuPlanListCell(){}

    @Override
    protected void updateItem(Menu menu, boolean bln) {
      super.updateItem(menu, bln);     
      if (menu != null)  {
        HBox box = new HBox();        
        Label label = new Label();   
        Button deleteButton = new Button();        
        deleteButton.setId("user-delete-button");
        deleteButton.setOnAction(event -> {
          viewModel.getDeleteMenuMethod().invoke(menu);
        });
        label.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(label, Priority.ALWAYS);
        label.setText(menu.getName() + " " + menu.getId());
        box.getChildren().addAll(label, deleteButton);
        setGraphic(box);
      }
      else{
        setGraphic(null);
      }
    }
  }

  public MenuPlanManagementPage(MenuPlanManagementViewModel viewModel) {
    super();
    this.viewModel = viewModel;
    this.getChildren().addAll(createButtonBox(), createSplitContainer());
  }
  
  private SplitPane createSplitContainer(){
    SplitPane container = new SplitPane();
    VBox.setVgrow(container, Priority.ALWAYS);
    AnchorPane.setTopAnchor(container, 50.0);
    AnchorPane.setLeftAnchor(container, 10.0);
    AnchorPane.setRightAnchor(container, 10.0);
    container.getItems().addAll(createCategoryView(), createMenuView());
    return container;
  }
  
  private  ListView<MenuCategory> createCategoryView(){
    ListView<MenuCategory> categoryListView = new ListView<MenuCategory>();
    categoryListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      viewModel.setSelectedMenuCategory(newValue);
    });
    categoryListView.setCellFactory(p -> new MenuCategoryPlanListCell());
    categoryListView.setItems(viewModel.getMenuCategoryList());
    return categoryListView;
  }
  
  private ListView<Menu> createMenuView(){
    ListView<Menu> menuListView = new ListView<Menu>();
    menuListView.setCellFactory(p -> new MenuPlanListCell());
    menuListView.setItems(viewModel.getMenuList());
    return menuListView;
  }
  
  private HBox createButtonBox(){
    HBox box = new HBox();
    box.getChildren().addAll(createAddMenuCategoryButton(), createAddMenuButton());
    return box;
  }
  
  private Button createAddMenuCategoryButton(){
    Button addButton = new Button("Kategorie hinzufügen");
    addButton.setOnAction(event -> {
      viewModel.getAddMenuCategoryMethod().invoke();
    });    
    addButton.setId("user-add-button");
    AnchorPane.setTopAnchor(addButton, 10.0);
    AnchorPane.setRightAnchor(addButton, 10.0);
    return addButton;
  }
  
  private Button createAddMenuButton(){
    Button addButton = new Button("Menü hinzufügen");
    addButton.setOnAction(event -> {
      viewModel.getAddMenuMethod().invoke(viewModel.getSelectedMenuCategory());
    });    
    addButton.setId("user-add-button");
    addButton.visibleProperty().bind(viewModel.isAddMenuPossibleProperty());
    AnchorPane.setTopAnchor(addButton, 10.0);
    AnchorPane.setRightAnchor(addButton, 10.0);
    return addButton;
  }
}
