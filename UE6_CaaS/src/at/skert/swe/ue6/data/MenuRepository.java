package at.skert.swe.ue6.data;
import at.skert.swe.ue6.contracts.IRepository;
import at.skert.swe.ue6.contracts.Menu;
import at.skert.swe.ue6.contracts.MenuCategory;

public class MenuRepository  extends AbstractRepository<Menu> {
  public MenuRepository(IRepository<MenuCategory> menuCategoryRepository){
    for(MenuCategory category : menuCategoryRepository.getAll()) {
      create(new Menu("Testmenu1", 12.08, category), () -> {}, (exception) -> {});
      create(new Menu("Testmenu2", 12.08, category), () -> {}, (exception) -> {});
    }
  }
}
