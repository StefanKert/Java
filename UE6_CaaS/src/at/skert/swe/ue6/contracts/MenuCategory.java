package at.skert.swe.ue6.contracts;

public class MenuCategory implements IEntity{
  private long id;
  private String name;
  
  public MenuCategory(String name) {
      this.name = name;
  }
  
  public MenuCategory(long id, String name) {
      this(name);
      this.id = id;
  }
  
  public long getId() {
      return id;
  }
  public void setId(int id) {
      this.id = id;
  }
  public String getName() {
      return name;
  }
  public void setName(String name) {
      this.name = name;
  }
  
  @Override
  public boolean equals(Object other) {
      MenuCategory otherCategory = (other instanceof MenuCategory) ? (MenuCategory) other : null;
      if (otherCategory == null)
          return false;
      
      return otherCategory.getId() == getId();
  }

  @Override
  public String toString() {
      return name;
  }
}