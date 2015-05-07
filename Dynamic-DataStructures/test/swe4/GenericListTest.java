package swe4;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GenericListTest {
  GenericList<Integer> list;
  
  @Before
  public void setUp() throws Exception {
    list = new GenericList<Integer>();
  }

  @After
  public void tearDown() throws Exception {
    list = null;
  }

  @Test
  public void testFirst() {
    list.prepend(1);
    assertEquals(new Integer(1), list.first());
  }
  
  @Test(expected=NoSuchElementException.class)
  public void testFirstException() {
    list.first();
  }
  
  @Test
  public void testSize() {
    assertEquals(0, list.size());
    list.append(1);
    assertEquals(1, list.size());
  }

  @Test
  public void testPrepend() {
    list.prepend(3);
    list.prepend(2);
    list.prepend(1);
    Iterator<Integer> it = list.iterator();
    assertEquals(new Integer(1), it.next());
    assertEquals(new Integer(2), it.next());
    assertEquals(new Integer(3), it.next());
  }

  @Test
  public void testAppend() {
    list.append(1);
    list.append(2);
    list.append(3);
    Iterator<Integer> it = list.iterator();
    assertEquals(new Integer(1), it.next());
    assertEquals(new Integer(2), it.next());
    assertEquals(new Integer(3), it.next());
  }

  @Test
  public void testIsEmpty() {
    assertTrue(list.isEmpty());
    list.append(1);
    assertFalse(list.isEmpty());
  }

  @Test
  public void testToString() {
    list.append(1);
    list.append(2);
    list.append(3);
    list.append(4);
    assertEquals("[1,2,3,4]", list.toString());
  }
}
