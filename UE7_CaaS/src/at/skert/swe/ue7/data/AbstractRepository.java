package at.skert.swe.ue7.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import at.skert.swe.ue7.contracts.Action;
import at.skert.swe.ue7.contracts.ActionWithParam;
import at.skert.swe.ue7.contracts.data.IEntity;
import at.skert.swe.ue7.contracts.data.IRepository;
import at.skert.swe.ue7.contracts.data.User;
import at.skert.swe.ue7.contracts.exceptions.EntityNotAddedException;

public abstract class AbstractRepository<T extends IEntity> implements
    IRepository<T> {
  protected DataAccessProvider dataAccessProvider;

  private static final String CONNECTION_STRING = "jdbc:mysql://localhost/CaaS";
  private static final String USERNAME = "root";
  private static final String PASSWORD = "shtzbkhz4vz";

  public AbstractRepository() {
    dataAccessProvider = new DataAccessProvider(CONNECTION_STRING, USERNAME, PASSWORD);
  }

  @Override
  public List<T> getAll() {
    List<T> entities = new ArrayList<T>();
    dataAccessProvider.ExecuteQuery(getGetAllStatement(), resultSet -> {
      try {
        while (resultSet.next()) {
          entities.add(getEntityForResultSetEntry(resultSet));
        }
      } catch (Exception e) {
        System.err.println(e); // Log error to console output and swallow it.
                               // Return empty list instead
      }
    }, e -> {
      System.err.println(e); // Log error to console output and swallow it.
                             // Return empty list instead
      });
    return entities;
  }

  protected abstract String getGetAllStatement();

  @Override
  public List<T> getAllByPredicate(Predicate<? super T> predicate) {
    return getAll().stream().filter(predicate).collect(Collectors.toList());
  }

  @Override
  public void create(T entity, Action onSuccess,
      ActionWithParam<Exception> onError) {
    if (entity.getId() != -1)
      onError.invoke(new Exception("Object can't be inserted twice."));
    dataAccessProvider.ExecuteUpdate(getInsertStatement(), (resultSet) -> {
      try {
        fillEntityWithKey(entity, resultSet);
      } catch (Exception ex) {
        onError.invoke(ex);
      }
    }, onError, getInsertArguments(entity));
  }

  protected abstract String getInsertStatement();

  protected abstract Object[] getInsertArguments(T entity);

  @Override
  public void update(T entity, Action onSuccess,
      ActionWithParam<Exception> onError) {
    if (entity.getId() == -1)
      onError.invoke(new Exception("User not added yet can't be updated."));
    dataAccessProvider.ExecuteUpdate(getUpdateStatement(), onSuccess, onError,
        getUpdateArguments(entity));
  }

  protected abstract String getUpdateStatement();

  protected abstract Object[] getUpdateArguments(T entity);

  @Override
  public void delete(T entity, Action onSuccess,
      ActionWithParam<Exception> onError) {
    if (entity.getId() == -1)
      onError.invoke(new Exception("Entity not added yet can't be updated."));
    dataAccessProvider.ExecuteUpdate(getDeleteStatement(), onSuccess, onError,
        getDeleteArguments(entity));
  }

  protected abstract String getDeleteStatement();

  protected abstract Object[] getDeleteArguments(T entity);

  @Override
  public void getById(long id, ActionWithParam<T> continueWith,
      ActionWithParam<Exception> onError) {
    dataAccessProvider.ExecuteQuery(getGetByIdStatement(), resultSet -> {
      try {
        if(resultSet.next())
          continueWith.invoke(getEntityForResultSetEntry(resultSet));
        else
          onError.invoke(new Exception("No Result available for Id " + id));
      } catch (Exception e) {
        onError.invoke(e);
      }
    }, onError, id);
  }

  protected abstract String getGetByIdStatement();

  protected abstract T getEntityForResultSetEntry(ResultSet resultSetEntry)
      throws SQLException;

  private void fillEntityWithKey(T entity, ResultSet resultSet)
      throws Exception {
    if (resultSet != null && resultSet.next())
      entity.setId(resultSet.getInt(1));
    else
      throw new Exception("Auto generated keys not supported.");
  }
}
