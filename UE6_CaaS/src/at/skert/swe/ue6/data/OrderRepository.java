package at.skert.swe.ue6.data;

import java.time.LocalDate;
import java.time.LocalTime;

import at.skert.swe.ue6.contracts.IRepository;
import at.skert.swe.ue6.contracts.data.Menu;
import at.skert.swe.ue6.contracts.data.Order;
import at.skert.swe.ue6.contracts.data.User;

public class OrderRepository  extends AbstractRepository<Order> { 

  public OrderRepository(IRepository<Menu> menuRepository, IRepository<User> userRepository){
    for(Menu menu : menuRepository.getAll()) {
      for(User user : userRepository.getAll()) {
        create(new Order(menu, user, LocalDate.now(), LocalTime.now().minusHours(1)), () -> {}, (exception) -> {});
      }
    }
  }
}
