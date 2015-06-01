package at.skert.swe.ue6.data;

import at.skert.swe.ue6.contracts.User;

public class UserRepository  extends AbstractRepository<User> { 
  public UserRepository(){
    create(new User("User1", "Megapassword", "Stefan", "Kert", true), () -> {}, (exception) -> {});
    create(new User("User2", "Megapassword2", "Max", "Mustermann", true), () -> {}, (exception) -> {});
  }
}
