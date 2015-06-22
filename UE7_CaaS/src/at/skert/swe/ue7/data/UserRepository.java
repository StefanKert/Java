package at.skert.swe.ue7.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import at.skert.swe.ue7.contracts.data.User;

public class UserRepository extends AbstractRepository<User> {

  @Override
  protected String getTableName(){
    return "Users";
  }
  
  @Override
  protected String getInsertStatement() {
    return String.format("INSERT INTO %s (Username, Password, Firstname, Lastname, Activated) values (?, ?, ?, ?, ?)", getTableName());
  }

  @Override
  protected Object[] getInsertArguments(User entity) {
    return new Object[] { entity.getUsername(), entity.getPassword(),
        entity.getFirstname(), entity.getLastname(), entity.getActivated() };
  }

  @Override
  protected String getUpdateStatement() {
    return String.format("UPDATE %s SET Username = ?, Password = ?, Firstname = ?, Lastname = ?, Activated = ? WHERE Id = ?", getTableName());
  }

  @Override
  protected Object[] getUpdateArguments(User entity) {
    return new Object[] { entity.getUsername(), entity.getPassword(),
        entity.getFirstname(), entity.getLastname(), entity.getActivated(),
        entity.getId() };
  }

  @Override
  protected String getGetByIdStatement() {
    return String.format("SELECT * FROM %s WHERE Id = ?", getTableName());
  }

  @Override
  protected User getEntityForResultSetEntry(ResultSet resultSetEntry) throws SQLException {
  return new User(resultSetEntry.getInt("Id"),
      resultSetEntry.getString("Username"),
      resultSetEntry.getString("Password"),
      resultSetEntry.getString("Firstname"),
      resultSetEntry.getString("Lastname"),
      resultSetEntry.getBoolean("Activated"));
  }
}
