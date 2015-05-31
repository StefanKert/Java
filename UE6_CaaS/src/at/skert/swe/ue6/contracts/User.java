package at.skert.swe.ue6.contracts;

public class User implements IEntity {
  private long id;
  private String firstName;
  private String lastName;
  
  public User(long id, String firstName, String lastName){
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
  }
  
  public long getId(){
    return id;
  }
  
  public String getFirstName(){
    return firstName;
  }
  
  public String getLastName(){
    return lastName;
  }
}
