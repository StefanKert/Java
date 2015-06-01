package at.skert.swe.ue6.integration;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import at.skert.swe.ue6.contracts.IRepository;
import at.skert.swe.ue6.contracts.Menu;
import at.skert.swe.ue6.contracts.MenuCategory;
import at.skert.swe.ue6.contracts.exceptions.EntityNotAddedException;
import at.skert.swe.ue6.data.MenuCategoryRepository;
import at.skert.swe.ue6.data.MenuRepository;
import at.skert.swe.ue6.viewmodel.MenuPlanManagementViewModel;

public class MenuPlanManagementInteractions {
  MenuPlanManagementViewModel viewModel;
  IRepository<Menu> menuRepository;
  IRepository<MenuCategory> menuCategoryRepository;
  
  public MenuPlanManagementInteractions(){
    this.viewModel = new MenuPlanManagementViewModel();
    this.menuRepository = new MenuRepository();
    this.menuCategoryRepository = new MenuCategoryRepository();
  }
  
  public MenuPlanManagementViewModel getIntegratedViewModel() {
    viewModel.getMenuCategoryList().addAll(menuCategoryRepository.getAll());
    viewModel.setAddMenuMethod(category -> addMenu(category));
    viewModel.setDeleteMenuMethod(menu -> deleteMenu(menu));
    
    viewModel.setAddMenuCategoryMethod(() -> addMenuCategory());
    viewModel.setDeleteMenuCategoryMethod(category -> deleteMenuCategory(category));
    
    viewModel.setLoadMenuForCategoryMethod(category -> loadMenuForCategory(category));
    return viewModel;
  }
  
  public void addMenu(MenuCategory category){
    //TODO: Dialog einbauen für das hinzufügen des Menüs.
    menuRepository.create(new Menu(1, "Test", 12.05, category, null, null));
    viewModel.getMenuList().clear();
    viewModel.getMenuList().addAll(menuRepository.getAllByPredicate(x -> x.getCategory().getId() == viewModel.getSelectedMenuCategory().getId()));
  }
  public void deleteMenu(Menu menu) {
    try {
      menuRepository.delete(menu);
      viewModel.getMenuList().clear();
      viewModel.getMenuList().addAll(menuRepository.getAllByPredicate(x -> x.getCategory().getId() == viewModel.getSelectedMenuCategory().getId()));
    } catch (EntityNotAddedException e) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Fehler");
      alert.setHeaderText("Es ist ein Fehler beim Löschen des Menüs aufgetreten.");
      alert.setContentText("Das zu löschende Menü wurde nicht in der Datenbank gefunden.");
      alert.showAndWait();
      e.printStackTrace();
    }
  }
  
  public void addMenuCategory(){
    //TODO: Dialog einbauen für das hinzufügen der Kategorie.
    MenuCategory category = new MenuCategory(1, "Test");
    menuCategoryRepository.create(category);
    viewModel.getMenuCategoryList().clear();
    viewModel.getMenuCategoryList().addAll(menuCategoryRepository.getAll());
    viewModel.setSelectedMenuCategory(category);
  }
  public void deleteMenuCategory(MenuCategory category){
    try {
      menuCategoryRepository.delete(category);
      viewModel.getMenuCategoryList().clear();
      viewModel.getMenuCategoryList().addAll(menuCategoryRepository.getAll());
    } catch (EntityNotAddedException e) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Fehler");
      alert.setHeaderText("Es ist ein Fehler beim Löschen der Kategorie aufgetreten.");
      alert.setContentText("Die zu löschende Kategorie wurde nicht in der Datenbank gefunden.");
      alert.showAndWait();
      e.printStackTrace();
    }
  }
  
  public void loadMenuForCategory(MenuCategory category){
    viewModel.getMenuList().clear();
    viewModel.getMenuList().addAll(menuRepository.getAllByPredicate(x -> x.getCategory().getId() == category.getId()));
  }
}
