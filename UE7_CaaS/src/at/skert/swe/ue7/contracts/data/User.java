package at.skert.swe.ue7.contracts.data;

public class User implements IEntity {
  private static final long serialVersionUID = 8187421389827214421L;
  
  private long id;
  private String username;
  private String password;
  private String firstname;
  private String lastname;
  private boolean activated;

  public User(String username, String password, String firstname,
      String lastname, boolean activated) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.username = username;
    this.password = password;
    this.activated = activated;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public boolean getActivated() {
    return activated;
  }

  public void setActivated(boolean activated) {
    this.activated = activated;
  }

  @Override
  public boolean equals(Object other) {
    User otherMenu = (other instanceof User) ? (User) other : null;
    if (otherMenu == null)
      return false;

    return otherMenu.getId() == getId();
  }

  @Override
  public String toString() {
    return firstname + " " + lastname;
  }
}
