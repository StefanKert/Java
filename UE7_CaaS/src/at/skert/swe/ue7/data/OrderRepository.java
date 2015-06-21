package at.skert.swe.ue7.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Random;

import at.skert.swe.ue7.contracts.data.IRepository;
import at.skert.swe.ue7.contracts.data.Menu;
import at.skert.swe.ue7.contracts.data.MenuCategory;
import at.skert.swe.ue7.contracts.data.Order;
import at.skert.swe.ue7.contracts.data.User;

public class OrderRepository extends AbstractRepository<Order> {
private IRepository<Menu> menuRepository;
  private IRepository<User> userRepository;
  
  public OrderRepository(IRepository<Menu> menuRepository, IRepository<User> userRepository) {
    this.menuRepository = menuRepository;
    this.userRepository = userRepository;
  }

  @Override
  protected String getGetAllStatement() {
    return "SELECT * FROM Orders";
  }

  @Override
  protected String getInsertStatement() {
    return "INSERT INTO Orders (MenuId, UserId, DateTime) VALUES (?, ?, ?)";
  }

  @Override
  protected Object[] getInsertArguments(Order entity) {
    return new Object[] { entity.getMenu().getId(), entity.getUser().getId(), entity.getDateTime() };
  }

  @Override
  protected String getUpdateStatement() {
    return "UPDATE Orders SET MenuId = ?, UserId = ?, DateTime = ? WHERE Id = ?";
  }

  @Override
  protected Object[] getUpdateArguments(Order entity) {
    return new Object[] {  entity.getMenu().getId(), entity.getUser().getId(), entity.getDateTime(), entity.getId() };
  }

  @Override
  protected String getDeleteStatement() {
    return "DELETE FROM Orders WHERE Id = ?";
  }

  @Override
  protected Object[] getDeleteArguments(Order entity) {
    return new Object[] { entity.getId() };
  }

  @Override
  protected String getGetByIdStatement() {
    return "SELECT * FROM Orders WHERE Id = ?";
  }

  private Menu tempMenu;
  private User tempUser;
  @Override
  protected Order getEntityForResultSetEntry(ResultSet resultSetEntry)
      throws SQLException {
    tempMenu = null;
    tempUser = null;
    menuRepository.getById(resultSetEntry.getInt("MenuId"),
        menu -> {
          tempMenu = menu;
        }, ex -> {
          System.out.println(ex.toString());
      });
   userRepository.getById(resultSetEntry.getInt("UserId"),
        user -> {
          tempUser = user;
        }, ex -> {
          System.out.println(ex.toString());
      });

    return new Order(resultSetEntry.getInt("Id"), tempMenu, tempUser, resultSetEntry.getTimestamp("DateTime").toLocalDateTime());
  }
}
