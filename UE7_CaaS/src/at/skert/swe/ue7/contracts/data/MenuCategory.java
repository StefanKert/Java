package at.skert.swe.ue7.contracts.data;

public class MenuCategory implements IEntity {
  private static final long serialVersionUID = 1177261649901921052L;
  
  private long id;
  private String name;

  public MenuCategory(String name) {
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
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