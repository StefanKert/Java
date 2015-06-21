package at.skert.swe.ue7.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import at.skert.swe.ue7.contracts.data.User;

public class UserRepository extends AbstractRepository<User> {

  @Override
  protected String getGetAllStatement() {
    return "SELECT * FROM Users";
  }
  @Override
  protected String getInsertStatement() {
    return "INSERT INTO users (Username, Password, Firstname, Lastname, Activated) values (?, ?, ?, ?, ?)";
  }

  @Override
  protected Object[] getInsertArguments(User entity) {
    return new Object[] { entity.getUsername(), entity.getPassword(),
        entity.getFirstname(), entity.getLastname(), entity.getActivated() };
  }

  @Override
  protected String getUpdateStatement() {
    return "UPDATE Users SET Username = ?, Password = ?, Firstname = ?, Lastname = ?, Activated = ? WHERE Id = ?";
  }

  @Override
  protected Object[] getUpdateArguments(User entity) {
    return new Object[] { entity.getUsername(), entity.getPassword(),
        entity.getFirstname(), entity.getLastname(), entity.getActivated(),
        entity.getId() };
  }

  @Override
  protected String getDeleteStatement() {
    return "DELETE FROM Users WHERE Id = ?";
  }

  @Override
  protected Object[] getDeleteArguments(User entity) {
    return new Object[] { entity.getId() };
  }

  @Override
  protected String getGetByIdStatement() {
    return "SELECT * FROM Users WHERE Id = ?";
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
