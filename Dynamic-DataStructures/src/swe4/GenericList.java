package swe4;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class GenericList<T> implements Iterable<T> {
  public static class GenericListIterator<T> implements Iterator<T> {
    private Node<T> next;

    public GenericListIterator(Node<T> first) {
      next = first;
    }

    @Override
    public boolean hasNext() {
      return next != null;
    }

    @Override
    public T next() {
      if (next == null)
        throw new NoSuchElementException();
      T o = next.value;
      next = next.next;
      return o;
    }

  }

  private static class Node<T> { // nested class (not nonstatic/inner class))
    public Node<T> next;
    public T value;

    public Node(T value, Node<T> next) {
      this.value = value;
      this.next = next;
    }
  }

  private Node<T> first, last;
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

  public void prepend(T o) {
    first = new Node<T>(o, first);
    if (last == null)
      last = first;
    size++;
  }

  public void append(T o) {
    Node<T> node = new Node<T>(o, null);
    if (first == null) {
      last = node;
      first = last;
    } else {
      last.next = node;
      last = last.next;
    }
    size++;
  }

  public boolean isEmpty() {
    return first == null;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    Node<T> node = first;
    while (node != null) {
      sb.append(node.value.toString());
      node = node.next;
      if (node != null)
        sb.append(",");
    }
    sb.append("]");
    return sb.toString();
  }

  public static void main(String[] args) {
    GenericList<Integer> list = new GenericList<Integer>();
    list.append(12);
    
    Iterator<Integer> it = list.iterator();
    while(it.hasNext())
      System.out.println(it.next());
    System.out.println(list.toString());
  }
  
  public int size(){
    return size;
  }

  @Override
  public Iterator<T> iterator() {
    return new GenericListIterator<T>(first);
  }
}
