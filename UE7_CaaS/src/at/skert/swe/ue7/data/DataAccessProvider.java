package at.skert.swe.ue7.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import at.skert.swe.ue7.contracts.Action;
import at.skert.swe.ue7.contracts.ActionWithParam;

public class DataAccessProvider {
  private Connection conn = null;
  private String connectionString;
  private String username;
  private String password;

  public DataAccessProvider(String connectionString, String username, String password)  {
    try { // We need this for windows devices maybe we can remove this on linux
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      System.out.println(e.toString());
    }
    this.connectionString = connectionString;
    this.username = username;
    this.password = password;
  }

  private void establishConnection() throws SQLException {
    if(conn == null)
      conn = DriverManager.getConnection(connectionString, username, password);
    else{
      if(conn.isClosed())
        conn = DriverManager.getConnection(connectionString, username, password);
    }
  }

  private void closeConnection() throws SQLException {
    if (conn != null && !conn.isClosed())
      conn.close();
  }

  public void ExecuteQuery(String query) {
    try {
      establishConnection();
      Statement stmt = conn.createStatement();
      stmt.execute(query);
      stmt.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  public void ExecuteUpdate(String query, ActionWithParam<ResultSet> onSuccess, ActionWithParam<Exception> onFailed, Object... args){
    try {
      establishConnection();
      PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      for (int i = 0; i < args.length; i++)
        statement.setObject(i + 1, args[i]);
      statement.executeUpdate();
      ResultSet set = statement.getGeneratedKeys();
      onSuccess.invoke(set);
      set.close();
      statement.close();
      closeConnection();
    } catch (SQLException e) {
      onFailed.invoke(e);
    }
  }
  
  public void ExecuteUpdate(String query, Action onSuccess, ActionWithParam<Exception> onFailed, Object... args){
    try {
      establishConnection();
      PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      for (int i = 0; i < args.length; i++)
        statement.setObject(i + 1, args[i]);
      statement.executeUpdate();
      ResultSet set = statement.getGeneratedKeys();
      onSuccess.invoke();
      set.close();
      statement.close();
      closeConnection();
    } catch (SQLException e) {
      onFailed.invoke(e);
    }
  }

  public void ExecuteQuery(String query, ActionWithParam<ResultSet> onSuccess, ActionWithParam<Exception> onFailed, Object... args) {
    try {
      establishConnection();
      PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      for (int i = 0; i < args.length; i++)
        statement.setObject(i + 1, args[i]);
      System.out.println(statement.toString());
      ResultSet set = statement.executeQuery();
      onSuccess.invoke(set);
      set.close();
      statement.close();
      closeConnection();
    } catch (SQLException e) {
      onFailed.invoke(e);
    }
  }
}
