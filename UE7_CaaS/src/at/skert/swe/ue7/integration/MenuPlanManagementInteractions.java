package at.skert.swe.ue7.integration;

import at.skert.swe.ue7.contracts.data.IRepository;
import at.skert.swe.ue7.contracts.data.Menu;
import at.skert.swe.ue7.contracts.data.MenuCategory;
import at.skert.swe.ue7.view.dialog.AddMenuCategoryDialog;
import at.skert.swe.ue7.view.dialog.AddMenuDialog;
import at.skert.swe.ue7.view.utils.MessageBox;
import at.skert.swe.ue7.viewmodel.MenuPlanManagementViewModel;

public class MenuPlanManagementInteractions {
  private MenuPlanManagementViewModel viewModel;
  private IRepository<Menu> menuRepository;
  private IRepository<MenuCategory> menuCategoryRepository;

  public MenuPlanManagementInteractions(IRepository<Menu> menuRepository, IRepository<MenuCategory> menuCategoryRepository) {
    this.viewModel = new MenuPlanManagementViewModel();
    this.menuCategoryRepository = menuCategoryRepository;
    this.menuRepository = menuRepository;
  }

  public MenuPlanManagementViewModel getIntegratedViewModel() {
    viewModel.getMenuCategoryList().addAll(menuCategoryRepository.getAll());
    viewModel.setAddMenuMethod(category -> addMenu(category));
    viewModel.setDeleteMenuMethod(menu -> deleteMenu(menu));

    viewModel.setAddMenuCategoryMethod(() -> addMenuCategory());
    viewModel.setDeleteMenuCategoryMethod(category -> deleteMenuCategory(category));
    viewModel.setLoadMenuForCategoryMethod(category -> loadMenuForCategory(category));
    viewModel.setLoadMenuCategoriesMethod(() -> loadMenuCategories());
    return viewModel;
  }

  public void addMenu(MenuCategory category) {
    AddMenuDialog dialog = new AddMenuDialog(menuCategoryRepository.getAll());
    dialog
        .setAddMenuMethod(menu -> menuRepository.create(
            menu,
            () -> {
              refreshMenuList();
              dialog.close();
            },
            exception -> {
              MessageBox
                  .showErrorDialog(
                      "Fehler",
                      "Es ist ein Fehler beim Hinzufügen des Menüs aufgetreten.",
                      "Das zu speichernde Menu konnte nicht in der Datenbank gespeichert werden, da ein Fehler aufgetreten ist.",
                      exception);
            }));
    dialog.showAndWait();
  }

  public void deleteMenu(Menu menu) {
    MessageBox
        .showConfirmDialog(
            "Achtung",
            "Wollen Sie das Menü " + menu.getName() + " wirklich löschen? ",
            () -> {
              menuRepository
                  .delete(
                      menu,
                      () -> refreshMenuList(),
                      exception -> MessageBox
                          .showErrorDialog(
                              "Fehler",
                              "Es ist ein Fehler beim Löschen des Menüs aufgetreten.",
                              "Das zu löschende Menü wurde nicht in der Datenbank gefunden.",
                              exception));
            }, () -> {
            });
  }

  public void addMenuCategory() {
    AddMenuCategoryDialog dialog = new AddMenuCategoryDialog();
    dialog
        .setAddMenuCategoryMethod(menuCategory -> menuCategoryRepository.create(
            menuCategory,
            () -> {
              refreshMenuCategoryList();
              dialog.close();
            },
            exception -> {
              MessageBox
                  .showErrorDialog(
                      "Fehler",
                      "Es ist ein Fehler beim Hinzufügen der Kategorie aufgetreten.",
                      "Die zu speichernde Kategorie konnte nicht in der Datenbank gespeichert werden, da ein Fehler aufgetreten ist.",
                      exception);
            }));
    dialog.showAndWait();
  }

  public void deleteMenuCategory(MenuCategory category) {
    MessageBox
        .showConfirmDialog(
            "Achtung",
            "Wollen Sie die Kategorie "
                + category.getName()
                + " wirklich löschen? Dadurch werden auch alle Menüs dieser Kategorie gelöscht.",
            () -> {
              menuCategoryRepository.delete(
                  category,
                  () -> refreshMenuCategoryList(),
                  exception -> MessageBox
                      .showErrorDialog(
                          "Fehler",
                          "Es ist ein Fehler beim Löschen der Kategorie aufgetreten.",
                          "Die zu löschende Kategorie wurde nicht in der Datenbank gefunden.",
                          exception));
            }, () -> {
            });
  }

  public void loadMenuForCategory(MenuCategory category) {
    viewModel.getMenuList().clear();
    viewModel.getMenuList().addAll(menuRepository.getAllByPredicate(x -> x.getCategory().getId() == category.getId()));
  }
  
  public void loadMenuCategories(){
    viewModel.getMenuCategoryList().clear();
    viewModel.getMenuCategoryList().addAll(menuCategoryRepository.getAll());
  }

  public void refreshMenuList() {
    viewModel.refreshMenus();
  }

  public void refreshMenuCategoryList() {
    viewModel.refreshMenuCategories();
  }
}
