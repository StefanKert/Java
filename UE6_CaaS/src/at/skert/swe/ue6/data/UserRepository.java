package at.skert.swe.ue6.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import at.skert.swe.ue6.contracts.Continuation;
import at.skert.swe.ue6.contracts.ErrorContinuation;
import at.skert.swe.ue6.contracts.IRepository;
import at.skert.swe.ue6.contracts.User;
import at.skert.swe.ue6.contracts.exceptions.EntityNotAddedException;

public class UserRepository  extends AbstractRepository<User> { 
  public UserRepository(){
    staticList = new ArrayList<User>();
    staticList.add(new User(1, "Stefan", "Kert"));
    staticList.add(new User(2, "Max", "Mustermann"));
  }
}
