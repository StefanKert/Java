package at.skert.swe.ue7.contracts.data;

import java.io.Serializable;

public interface IEntity extends Serializable {
  long getId();

  void setId(long id);
}