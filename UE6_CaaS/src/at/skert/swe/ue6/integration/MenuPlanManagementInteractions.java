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
    //TODO: Dialog einbauen f�r das hinzuf�gen des Men�s.
    menuRepository.create(new Menu("Test", 12.05, category), () -> {
      viewModel.getMenuList().clear();
      viewModel.getMenuList().addAll(menuRepository.getAllByPredicate(x -> x.getCategory().getId() == viewModel.getSelectedMenuCategory().getId()));
    }, exception -> {
      //TODO: errorhandling
    });
  }
  public void deleteMenu(Menu menu) {
    MessageBox.showConfirmDialog("Achtung", "Wollen Sie das Men� " + menu.getName() + " wirklich l�schen? ", () -> {
      menuRepository.delete(menu, () -> {
          viewModel.getMenuList().clear();
          viewModel.getMenuList().addAll(menuRepository.getAllByPredicate(x -> x.getCategory().getId() == viewModel.getSelectedMenuCategory().getId()));
        }, exception -> {
          MessageBox.showErrorDialog("Fehler", "Es ist ein Fehler beim L�schen des Men�s aufgetreten.", "Das zu l�schende Men� wurde nicht in der Datenbank gefunden.", exception);
        });
    }, () -> {});
  }
  
  public void addMenuCategory(){
    //TODO: Dialog einbauen f�r das hinzuf�gen der Kategorie.
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
    MessageBox.showConfirmDialog("Achtung", "Wollen Sie die Kategorie " + category.getName() + " wirklich l�schen? Dadurch werden auch alle Men�s dieser Kategorie gel�scht.", () -> {
        menuCategoryRepository.delete(category, () -> {
          viewModel.getMenuCategoryList().clear();
          viewModel.getMenuCategoryList().addAll(menuCategoryRepository.getAll());
        }, exception -> {
          MessageBox.showErrorDialog("Fehler", "Es ist ein Fehler beim L�schen der Kategorie aufgetreten.", "Die zu l�schende Kategorie wurde nicht in der Datenbank gefunden.", exception);
        });
    }, () -> {});
  }
  
  public void loadMenuForCategory(MenuCategory category){
    viewModel.getMenuList().clear();
    viewModel.getMenuList().addAll(menuRepository.getAllByPredicate(x -> x.getCategory().getId() == category.getId()));
  }
}
