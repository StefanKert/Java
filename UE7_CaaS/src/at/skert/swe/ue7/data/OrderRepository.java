package at.skert.swe.ue7.data;

import java.time.LocalDateTime;
import java.util.Random;

import at.skert.swe.ue7.contracts.data.IRepository;
import at.skert.swe.ue7.contracts.data.Menu;
import at.skert.swe.ue7.contracts.data.Order;
import at.skert.swe.ue7.contracts.data.User;

public class OrderRepository extends AbstractRepository<Order> {

  public OrderRepository(IRepository<Menu> menuRepository,
      IRepository<User> userRepository) {
    Random random = new Random();
    for (Menu menu : menuRepository.getAll()) {
      for (User user : userRepository.getAll()) {
        create(new Order(menu, user, LocalDateTime.now().minusHours(random.nextInt(LocalDateTime.now().getHour()))), () -> {}, (exception) -> {});
      }
    }
  }
}
