package at.skert.swe.ue7.viewmodel;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import at.skert.swe.ue7.contracts.data.Order;

public class OrderViewModel {
  private final ObservableList<Order> orders = FXCollections
      .observableList(new ArrayList<Order>());

  public ObservableList<Order> getOrderList() {
    return orders;
  }
}