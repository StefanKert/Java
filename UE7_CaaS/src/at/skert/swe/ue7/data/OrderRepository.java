package at.skert.swe.ue7.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import at.skert.swe.ue7.contracts.data.IRepository;
import at.skert.swe.ue7.contracts.data.Menu;
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
  protected String getTableName(){
    return "Orders";
  }

  @Override
  protected String getInsertStatement() {
    return String.format("INSERT INTO %s (MenuId, UserId, DateTime) VALUES (?, ?, ?)", getTableName());
  }

  @Override
  protected Object[] getInsertArguments(Order entity) {
    return new Object[] { entity.getMenu().getId(), entity.getUser().getId(), entity.getDateTime() };
  }

  @Override
  protected String getUpdateStatement() {
    return String.format("UPDATE %s SET MenuId = ?, UserId = ?, DateTime = ? WHERE Id = ?", getTableName());
  }

  @Override
  protected Object[] getUpdateArguments(Order entity) {
    return new Object[] {  entity.getMenu().getId(), entity.getUser().getId(), entity.getDateTime(), entity.getId() };
  }
  
  @Override
  protected String getGetByIdStatement() {
    return String.format("SELECT * FROM %s WHERE Id = ?", getTableName());
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
