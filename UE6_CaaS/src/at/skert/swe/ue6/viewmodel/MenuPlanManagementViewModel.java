package at.skert.swe.ue6.viewmodel;

import java.util.ArrayList;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import at.skert.swe.ue6.contracts.Action;
import at.skert.swe.ue6.contracts.ActionWithParam;
import at.skert.swe.ue6.contracts.Menu;
import at.skert.swe.ue6.contracts.MenuCategory;

public class MenuPlanManagementViewModel {
  private final ObservableList<Menu> menuList = FXCollections.observableList(new ArrayList<Menu>()); 
  private final ObservableList<MenuCategory> categoryList = FXCollections.observableList(new ArrayList<MenuCategory>());
  private final ReadOnlyBooleanWrapper addMenuPossible = new ReadOnlyBooleanWrapper();
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
  
  public void setSelectedMenuCategory(MenuCategory selectedMenuCategory){
    this.selectedMenuCategory = selectedMenuCategory;
    menuList.clear();
    if(selectedMenuCategory != null){    
      addMenuPossible.set(true);
      loadMenuForCategoryMethod.invoke(selectedMenuCategory);
    }
    else{
      addMenuPossible.set(false);
    }
  }
  
  public MenuCategory getSelectedMenuCategory(){
    return selectedMenuCategory;
  }
  
  public ReadOnlyBooleanProperty isAddMenuPossibleProperty() {
    return addMenuPossible.getReadOnlyProperty();
  }
  
  public void setAddMenuCategoryMethod(Action addMenuCategoryMethod){
    this.addMenuCategoryMethod = addMenuCategoryMethod;
  }
  
  public Action getAddMenuCategoryMethod(){
    return addMenuCategoryMethod;
  }
  
  public void setAddMenuMethod(ActionWithParam<MenuCategory> addMenuForCategoryMethod){
    this.addMenuForCategoryMethod = addMenuForCategoryMethod;
  }
  
  public ActionWithParam<MenuCategory> getAddMenuMethod(){
    return addMenuForCategoryMethod;
  }
  
  public void setDeleteMenuCategoryMethod(ActionWithParam<MenuCategory> deleteMenuCategoryMethod){
    this.deleteMenuCategoryMethod = deleteMenuCategoryMethod;
  }
  
  public ActionWithParam<MenuCategory> getDeleteMenuCategoryMethod(){
    return deleteMenuCategoryMethod;
  }
  
  public void setDeleteMenuMethod(ActionWithParam<Menu> deleteMenuMethod){
    this.deleteMenuMethod = deleteMenuMethod;
  }
  
  public ActionWithParam<Menu> getDeleteMenuMethod(){
    return deleteMenuMethod;
  }
  
  public void setLoadMenuForCategoryMethod(ActionWithParam<MenuCategory> loadMenuForCategoryMethod){
    this.loadMenuForCategoryMethod = loadMenuForCategoryMethod;
  }
  
  public ActionWithParam<MenuCategory> getLoadMenuForCategoryMethod(){
    return loadMenuForCategoryMethod;
  }
}
