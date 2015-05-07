package swe4;

import java.util.NoSuchElementException;

public class List {
  private static class Node { //nested class (not nonstatic/inner class))
    public Node next;
    public Object value;
    public Node(Object value, Node next){
      this.value = value;
      this.next = next;
    }
  }
  
  private Node first, last;
  private int size;
  
  public Object first() throws NoSuchElementException {
    if (first == null) 
      throw new NoSuchElementException();
    return first.value;
  }
  public Object last() throws NoSuchElementException {
    if (last == null) 
      throw new NoSuchElementException();
    return last.value;
  }
  
  public void prepend(Object o){
    first = new Node(o, first);
    if(last == null)
      last = first;
    size++;
  }
  
  public void append(Object o){
    Node node = new Node(o, null);
    if(first == null){
      last = node;
      first = last;
    }
    else{
      last.next = node;
      last = last.next;
    }
    size--;
  }
  
  public boolean isEmpty(){
    return first == null;
  }
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    Node node = first;
    while(node != null){
      sb.append(node.value.toString());
      node = node.next;
      if(node != null) 
        sb.append(",");
    }
    sb.append("]");
    return sb.toString();
  }
  
  public static void main(String[] args){
    List list = new List();
    list.append(12);
    list.append("Ts");
    list.append(12.34);
    System.out.println(list.toString());
  }
}
