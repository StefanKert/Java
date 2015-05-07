package swe4;

import java.util.*;

public class Stack2 {
  @SuppressWarnings({"rawtypes", "unchecked"})
  private java.util.List data;

  public Stack2() {
    data = new ArrayList();
  }


  public boolean isEmpty() {
    return data.isEmpty();
  }

  public boolean isFull() {
    return false;
  }

  public void push(Object o) {
    data.add(o);
  }

  public Object pop() throws StackException {
    if (isEmpty())
      throw new StackException("stack underflow");
    return data.remove(data.size() - 1);
  }
  
  private static class MyClass{
    @Override
    public String toString() {
      return "MyClass";
    }
  }

  public static void main(String[] args) {
    Stack2 s = new Stack2();
    try {
      s.push("1");
      s.push(new Double(1.22));
      s.push(2); // auto boxing
      s.push(new MyClass());

      while (!s.isEmpty()) {
        System.out.println(s.pop());
      }

      s.pop();
    } catch (StackException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      System.out.println(e);
    }
  }

}
