package at.skert.swe.ue7.contracts.data;

import java.time.LocalDateTime;

public class Order implements IEntity {
  private static final long serialVersionUID = -8736609480145455630L;
  
  private long id;
  private Menu menu;
  private User user;
  private LocalDateTime dateTime;
  
  public Order(Menu menu, User user, LocalDateTime dateTime) {
    this.menu = menu;
    this.user = user;
    this.dateTime = dateTime;
  }

  public Menu getMenu() {
    return menu;
  }

  public void setMenu(Menu menu) {
    this.menu = menu;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public boolean equals(Object other) {
    Order otherOrder = (other instanceof Order) ? (Order) other : null;
    if (otherOrder == null)
      return false;

    return otherOrder.getId() == getId();
  }
}