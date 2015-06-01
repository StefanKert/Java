package at.skert.swe.ue6.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import at.skert.swe.ue6.contracts.Continuation;
import at.skert.swe.ue6.contracts.ErrorContinuation;
import at.skert.swe.ue6.contracts.IRepository;
import at.skert.swe.ue6.contracts.Menu;
import at.skert.swe.ue6.contracts.MenuCategory;
import at.skert.swe.ue6.contracts.User;
import at.skert.swe.ue6.contracts.exceptions.EntityNotAddedException;

public class MenuCategoryRepository  extends AbstractRepository<MenuCategory> {
  public MenuCategoryRepository(){
    staticList = new ArrayList<MenuCategory>();
    staticList.add(new MenuCategory(1, "Kategorie 1"));
    staticList.add(new MenuCategory(2, "Kategorie 2"));
  }
}
