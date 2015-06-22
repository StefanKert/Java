package at.skert.swe.ue7.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import at.skert.swe.ue7.contracts.data.MenuCategory;

public class MenuCategoryRepository extends AbstractRepository<MenuCategory> {

  @Override
  protected String getTableName(){
    return "MenuCategories";
  }

  @Override
  protected String getInsertStatement() {
    return String.format("INSERT INTO %s (Name) values (?)", getTableName());
  }

  @Override
  protected Object[] getInsertArguments(MenuCategory entity) {
    return new Object[] { entity.getName() };
  }

  @Override
  protected String getUpdateStatement() {
    return String.format("UPDATE %s SET Name = ? WHERE Id = ?", getTableName());
  }

  @Override
  protected Object[] getUpdateArguments(MenuCategory entity) {
    return new Object[] { entity.getName(), entity.getId() };
  }

  @Override
  protected String getGetByIdStatement() {
    return "SELECT * FROM MenuCategories WHERE Id = ?";
  }

  @Override
  protected MenuCategory getEntityForResultSetEntry(ResultSet resultSetEntry)
      throws SQLException {
    return new MenuCategory(resultSetEntry.getInt("Id"),
        resultSetEntry.getString("Name"));
  }
}
