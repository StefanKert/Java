package at.skert.swe.ue6.data;

import at.skert.swe.ue6.contracts.MenuCategory;

public class MenuCategoryRepository  extends AbstractRepository<MenuCategory> {
  public MenuCategoryRepository(){
    create(new MenuCategory("Kategorie 1"), () -> {}, (exception) -> {});
    create(new MenuCategory("Kategorie 2"), () -> {}, (exception) -> {});
  }
}
