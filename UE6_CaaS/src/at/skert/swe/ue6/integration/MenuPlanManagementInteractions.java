package at.skert.swe.ue6.integration;

import at.skert.swe.ue6.contracts.IRepository;
import at.skert.swe.ue6.contracts.Menu;
import at.skert.swe.ue6.contracts.MenuCategory;
import at.skert.swe.ue6.data.MenuCategoryRepository;
import at.skert.swe.ue6.data.MenuRepository;
import at.skert.swe.ue6.view.utils.MessageBox;
import at.skert.swe.ue6.viewmodel.MenuPlanManagementViewModel;

public class MenuPlanManagementInteractions {
  MenuPlanManagementViewModel viewModel;
  IRepository<Menu> menuRepository;
  IRepository<MenuCategory> menuCategoryRepository;
  
  public MenuPlanManagementInteractions(){
    this.viewModel = new MenuPlanManagementViewModel();
    this.menuCategoryRepository = new MenuCategoryRepository();
    this.menuRepository = new MenuRepository(menuCategoryRepository);
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
    menuRepository.create(new Menu("Test", 12.05, category), () -> {
      viewModel.getMenuList().clear();
      viewModel.getMenuList().addAll(menuRepository.getAllByPredicate(x -> x.getCategory().getId() == viewModel.getSelectedMenuCategory().getId()));
    }, exception -> {
      //TODO: errorhandling
    });
  }
  public void deleteMenu(Menu menu) {
    MessageBox.showConfirmDialog("Achtung", "Wollen Sie das Menü " + menu.getName() + " wirklich löschen? ", () -> {
      menuRepository.delete(menu, () -> {
          viewModel.getMenuList().clear();
          viewModel.getMenuList().addAll(menuRepository.getAllByPredicate(x -> x.getCategory().getId() == viewModel.getSelectedMenuCategory().getId()));
        }, exception -> {
          MessageBox.showErrorDialog("Fehler", "Es ist ein Fehler beim Löschen des Menüs aufgetreten.", "Das zu löschende Menü wurde nicht in der Datenbank gefunden.", exception);
        });
    }, () -> {});
  }
  
  public void addMenuCategory(){
    //TODO: Dialog einbauen für das hinzufügen der Kategorie.
    MenuCategory category = new MenuCategory("Test");
    menuCategoryRepository.create(category, () -> {
      viewModel.getMenuCategoryList().clear();
      viewModel.getMenuCategoryList().addAll(menuCategoryRepository.getAll());
      viewModel.setSelectedMenuCategory(category);
    }, exception -> {
      //TODO: errorhandling
    });
  }
  public void deleteMenuCategory(MenuCategory category){
    MessageBox.showConfirmDialog("Achtung", "Wollen Sie die Kategorie " + category.getName() + " wirklich löschen? Dadurch werden auch alle Menüs dieser Kategorie gelöscht.", () -> {
        menuCategoryRepository.delete(category, () -> {
          viewModel.getMenuCategoryList().clear();
          viewModel.getMenuCategoryList().addAll(menuCategoryRepository.getAll());
        }, exception -> {
          MessageBox.showErrorDialog("Fehler", "Es ist ein Fehler beim Löschen der Kategorie aufgetreten.", "Die zu löschende Kategorie wurde nicht in der Datenbank gefunden.", exception);
        });
    }, () -> {});
  }
  
  public void loadMenuForCategory(MenuCategory category){
    viewModel.getMenuList().clear();
    viewModel.getMenuList().addAll(menuRepository.getAllByPredicate(x -> x.getCategory().getId() == category.getId()));
  }
}
