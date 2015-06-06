package at.skert.swe.ue7.data;

import at.skert.swe.ue7.contracts.data.MenuCategory;

public class MenuCategoryRepository extends AbstractRepository<MenuCategory> {
  public MenuCategoryRepository() {
    create(new MenuCategory("Kategorie 1"), () -> {}, (exception) -> {});
    create(new MenuCategory("Kategorie 2"), () -> {}, (exception) -> {});
  }
}
