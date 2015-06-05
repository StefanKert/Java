package at.skert.swe.ue6.contracts.data;

import java.time.LocalDate;

public class Menu implements IEntity {
  private long id;
  private String name;
  private double price;
  private MenuCategory category;
  private LocalDate beginDate, endDate;

  public Menu(String name, double price, MenuCategory category) {
    this.name = name;
    this.price = price;
    this.category = category;
  }

  public Menu(String name, double price, MenuCategory category,
      LocalDate begin, LocalDate end) {
    this(name, price, category);
    this.beginDate = begin;
    this.endDate = end;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public MenuCategory getCategory() {
    return category;
  }

  public void setCategory(MenuCategory category) {
    this.category = category;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public LocalDate getBegin() {
    return beginDate;
  }

  public void setBegin(LocalDate begin) {
    this.beginDate = begin;
  }

  public LocalDate getEnd() {
    return endDate;
  }

  public void setEnd(LocalDate end) {
    this.endDate = end;
  }

  @Override
  public boolean equals(Object other) {
    Menu otherMenu = (other instanceof Menu) ? (Menu) other : null;
    if (otherMenu == null)
      return false;

    return otherMenu.getId() == getId();
  }

  @Override
  public String toString() {
    return name;
  }
}