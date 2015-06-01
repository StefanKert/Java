package at.skert.swe.ue6.contracts;

public class Order implements IEntity {
  private long id;
  private String caption;
  private String description;
  
  public Order(String caption, String description){
    this.caption = caption;
    this.description = description;
  }
  
  public long getId(){
    return id;
  }
  public void setId(long id) {
    this.id = id;
  }
  public String getCaption(){
    return caption;
  }
  
  public String getDescription(){
    return description;
  }
}
