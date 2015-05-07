package swe4;

public class Stack1 {
  private Object[] data;
  private int top;

  public Stack1(int capacity) {
    data = new Object[capacity];
    top = -1;
  }

  public Stack1() {
    this(10);
  }

  public boolean isEmpty() {
    return top == -1;
  }

  public boolean isFull() {
    return top == data.length - 1;
  }

  public void push(Object o) throws StackException {
    if (isFull())
      throw new StackException("stack overflow");
    top++;
    data[top] = o;
  }

  public Object pop() throws StackException {
    if (isEmpty())
      throw new StackException("stack underflow");
    Object o = data[top];
    data[top] = null;
    top--;
    return o;
  }
  
  private static class MyClass{
    @Override
    public String toString() {
      return "MyClass";
    }
  }

  public static void main(String[] args) {
    Stack1 s = new Stack1();
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
