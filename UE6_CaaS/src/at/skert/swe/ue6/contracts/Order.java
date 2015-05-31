package at.skert.swe.ue6.contracts;

public class Order implements IEntity {
  private long id;
  private String caption;
  private String description;
  
  public Order(long id, String caption, String description){
    this.id = id;
    this.caption = caption;
    this.description = description;
  }
  
  public long getId(){
    return id;
  }
  
  public String getCaption(){
    return caption;
  }
  
  public String getDescription(){
    return description;
  }
}
