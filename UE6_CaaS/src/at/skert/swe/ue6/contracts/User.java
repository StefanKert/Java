package at.skert.swe.ue6.contracts;

public class User implements IEntity {
  private long id;
  private String userName;
  private String password;
  private boolean isLocked;
  
  public User(long id, String userName, String password){
    this.id = id;
    this.userName = userName;
    this.password = password;
    this.isLocked = false;
  }
  
  public long getId(){
    return id;
  }
  
  public String getUserName(){
    return userName;
  }
  
  public String getPassword(){
    return password;
  }
  
  public boolean getIsLocked(){
    return isLocked;
  }
  
  public void setIsLocked(boolean isLocked){
    this.isLocked = isLocked;
  }
}
