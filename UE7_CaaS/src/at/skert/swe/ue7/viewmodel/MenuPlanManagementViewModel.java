package at.skert.swe.ue7.viewmodel;

import java.util.ArrayList;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import at.skert.swe.ue7.contracts.Action;
import at.skert.swe.ue7.contracts.ActionWithParam;
import at.skert.swe.ue7.contracts.data.Menu;
import at.skert.swe.ue7.contracts.data.MenuCategory;

public class MenuPlanManagementViewModel {
  private final ObservableList<Menu> menuList = FXCollections
      .observableList(new ArrayList<Menu>());
  private final ObservableList<MenuCategory> categoryList = FXCollections
      .observableList(new ArrayList<MenuCategory>());
  private final ReadOnlyBooleanWrapper isMenuCategorySelected = new ReadOnlyBooleanWrapper();
  private MenuCategory selectedMenuCategory;
  private ActionWithParam<MenuCategory> loadMenuForCategoryMethod;
  private ActionWithParam<MenuCategory> addMenuForCategoryMethod;
  private ActionWithParam<MenuCategory> deleteMenuCategoryMethod;
  private ActionWithParam<Menu> deleteMenuMethod;
  private Action addMenuCategoryMethod;

  public ObservableList<Menu> getMenuList() {
    return menuList;
  }

  public ObservableList<MenuCategory> getMenuCategoryList() {
    return categoryList;
  }

  public void setSelectedMenuCategory(MenuCategory selectedMenuCategory) {
    this.selectedMenuCategory = selectedMenuCategory;
    menuList.clear();
    if (selectedMenuCategory != null) {
      isMenuCategorySelected.set(true);
      loadMenuForCategoryMethod.invoke(selectedMenuCategory);
    } else {
      isMenuCategorySelected.set(false);
    }
  }

  public MenuCategory getSelectedMenuCategory() {
    return selectedMenuCategory;
  }

  public ReadOnlyBooleanProperty isMenuCategorySelectedProperty() {
    return isMenuCategorySelected.getReadOnlyProperty();
  }

  public void addMenuCategory() {
    addMenuCategoryMethod.invoke();
  }

  public void deleteMenuCategory(MenuCategory category) {
    deleteMenuCategoryMethod.invoke(category);
  }

  public void addMenuForCategory() {
    addMenuForCategoryMethod.invoke(selectedMenuCategory);
  }

  public void deleteMenu(Menu menu) {
    deleteMenuMethod.invoke(menu);
  }

  public void setAddMenuCategoryMethod(Action addMenuCategoryMethod) {
    this.addMenuCategoryMethod = addMenuCategoryMethod;
  }

  public void setAddMenuMethod(
      ActionWithParam<MenuCategory> addMenuForCategoryMethod) {
    this.addMenuForCategoryMethod = addMenuForCategoryMethod;
  }

  public void setDeleteMenuCategoryMethod(
      ActionWithParam<MenuCategory> deleteMenuCategoryMethod) {
    this.deleteMenuCategoryMethod = deleteMenuCategoryMethod;
  }

  public void setDeleteMenuMethod(ActionWithParam<Menu> deleteMenuMethod) {
    this.deleteMenuMethod = deleteMenuMethod;
  }

  public void setLoadMenuForCategoryMethod(
      ActionWithParam<MenuCategory> loadMenuForCategoryMethod) {
    this.loadMenuForCategoryMethod = loadMenuForCategoryMethod;
  }
}