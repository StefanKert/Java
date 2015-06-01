package at.skert.swe.ue6.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import at.skert.swe.ue6.contracts.Continuation;
import at.skert.swe.ue6.contracts.ErrorContinuation;
import at.skert.swe.ue6.contracts.IRepository;
import at.skert.swe.ue6.contracts.Menu;
import at.skert.swe.ue6.contracts.MenuCategory;
import at.skert.swe.ue6.contracts.User;
import at.skert.swe.ue6.contracts.exceptions.EntityNotAddedException;

public class MenuRepository  extends AbstractRepository<Menu> {
  public MenuRepository(){
    staticList = new ArrayList<Menu>();
    staticList.add(new Menu("Testmenu1", 12.08, new MenuCategory(1, "Kategorie 1"), null, null));
    staticList.add(new Menu("Testmenu2", 12.08, new MenuCategory(2, "Kategorie 1"), null, null));
  }
}
