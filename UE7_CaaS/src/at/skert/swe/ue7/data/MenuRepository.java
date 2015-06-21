package at.skert.swe.ue7.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import at.skert.swe.ue7.contracts.Action;
import at.skert.swe.ue7.contracts.ActionWithParam;
import at.skert.swe.ue7.contracts.data.IRepository;
import at.skert.swe.ue7.contracts.data.Menu;
import at.skert.swe.ue7.contracts.data.MenuCategory;

public class MenuRepository extends AbstractRepository<Menu> {
  private IRepository<MenuCategory> menuCategoryRepository;

  public MenuRepository(IRepository<MenuCategory> menuCategoryRepository) {
    this.menuCategoryRepository = menuCategoryRepository;
  }

  @Override
  protected String getGetAllStatement() {
    return "SELECT * FROM Menus";
  }

  @Override
  protected String getInsertStatement() {
    return "INSERT INTO Menus (Name, Price, MenuCategoryId, BeginDate, EndDate) VALUES (?, ?, ?, ?, ?)";
  }

  @Override
  protected Object[] getInsertArguments(Menu entity) {
    return new Object[] { entity.getName(), entity.getPrice(),
        entity.getCategory().getId(), entity.getBegin(), entity.getEnd() };
  }

  @Override
  protected String getUpdateStatement() {
    return "UPDATE Menus SET Name = ?, Price = ?, MenuCategoryId = ?, BeginDate = ?, EndDate = ? WHERE Id = ?";
  }

  @Override
  protected Object[] getUpdateArguments(Menu entity) {
    return new Object[] { entity.getName(), entity.getPrice(),
        entity.getCategory().getId(), entity.getBegin(), entity.getEnd(),
        entity.getId() };
  }

  @Override
  protected String getDeleteStatement() {
    return "DELETE FROM Menus WHERE Id = ?";
  }

  @Override
  protected Object[] getDeleteArguments(Menu entity) {
    return new Object[] { entity.getId() };
  }

  @Override
  protected String getGetByIdStatement() {
    return "SELECT * FROM Menus WHERE Id = ?";
  }

  private MenuCategory tempMenuCategory;
  
  @Override
  protected Menu getEntityForResultSetEntry(ResultSet resultSetEntry)
      throws SQLException {
    tempMenuCategory = null;
    menuCategoryRepository.getById(resultSetEntry.getInt("MenuCategoryId"),
        menuCategory -> {
          tempMenuCategory = menuCategory;
        }, ex -> {
          System.out.println(ex.toString());
      });

    if(tempMenuCategory == null)
      System.out.println("Temp menucategory for " + resultSetEntry.getInt("MenuCategoryId") + " is null.");
    System.out.println(tempMenuCategory.getName());
    
    Date beginDate = resultSetEntry.getDate("BeginDate");
    Date endDate = resultSetEntry.getDate("EndDate");
    
    if(beginDate == null || endDate == null)
      return new Menu(resultSetEntry.getInt("Id"), resultSetEntry.getString("Name"), resultSetEntry.getDouble("Price"), tempMenuCategory);
    else
      return new Menu(resultSetEntry.getInt("Id"),
          resultSetEntry.getString("Name"), resultSetEntry.getDouble("Price"),
          tempMenuCategory, resultSetEntry.getDate("BeginDate").toLocalDate(),
          resultSetEntry.getDate("EndDate").toLocalDate());
  }
}
