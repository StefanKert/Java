package at.skert.swe.ue6.contracts.data;


import java.time.LocalDate;
import java.time.LocalTime;

import at.skert.swe.ue6.contracts.IEntity;

public class Order implements IEntity{
    private long id;
    private Menu menu;
    private User user;
    private LocalDate date;
    private LocalTime time;
    
    public Order(Menu menu, User user, LocalDate date, LocalTime time) {
        this.menu = menu;
        this.user = user;
        this.date = date;
        this.time = time;
    }
    
    public Menu getMenu() {
        return menu;
    }
    public void setMenu(Menu menu) {
        this.menu = menu;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public LocalTime getTime() {
        return time;
    }
    public void setTime(LocalTime time) {
        this.time = time;
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