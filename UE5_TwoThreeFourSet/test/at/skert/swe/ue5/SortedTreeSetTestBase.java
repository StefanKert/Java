package at.skert.swe.ue5;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

import org.junit.Test;

import at.skert.swe.ue5.interfaces.SortedSet;
import at.skert.swe.ue5.interfaces.SortedTreeSet;
import static org.junit.Assert.*;

public abstract class SortedTreeSetTestBase {

	protected abstract <T> SortedTreeSet<T> createSet(Comparator<T> comparator);

	protected <T> SortedTreeSet<T> createSet() {
		return createSet(null);
	}

	@Test
	public void testSizeSimple() {
		SortedSet<Integer> set = createSet();
		set.add(1);
		assertEquals(1, set.size());
		set.add(2);
		assertEquals(2, set.size());
		set.add(3);
		assertEquals(3, set.size());
		set.add(4);
		assertEquals(4, set.size());
	}

	@Test
	public void testAddSimple() {
		SortedSet<Integer> set = createSet();
		set.add(2);
		assertTrue(set.contains(2));
		set.add(1);
		assertTrue(set.contains(1));
		set.add(3);
		assertTrue(set.contains(3));
		assertTrue(set.contains(1));
		assertTrue(set.contains(2));
	}

	@Test
	public void testSize() throws Exception {
		final int NELEMS = 100;
		SortedSet<Integer> set = createSet();
		for (int i = 1; i <= NELEMS; i++) {
			set.add(i);
			assertEquals(i, set.size());
		}
	}

	@Test
	public void testIsEmpty() {
		SortedSet<Integer> set = createSet();
		assertTrue(set.isEmpty());
		set.add(1);
		assertFalse(set.isEmpty());
	}

	@Test
	public void testLinearAdd() {
		final int NELEMS = 10000;
		SortedSet<Integer> set = createSet();
		for (int i = 0; i < NELEMS; i++)
			set.add(i);

		for (int i = 0; i < NELEMS; i++)
			assertTrue(set.contains(i));

		for (int i = NELEMS; i < NELEMS + 100; i++)
			assertFalse(set.contains(i));
	}

	@Test
	public void testRandomAdd() {
		final int NELEMS = 100000;
		Random rand = new Random();
		SortedSet<Integer> set = createSet();

		int[] numbers = new int[NELEMS];
		for (int i = 0; i < numbers.length; i++)
			numbers[i] = rand.nextInt();

		for (int i = 0; i < NELEMS; i++)
			set.add(numbers[i]);

		for (int i = 0; i < NELEMS; i++)
			assertTrue(set.contains(numbers[i]));
	}

	@Test
	public void testSorted() {
		final int NELEMS = 1000;
		Random rand = new Random();
		SortedSet<Integer> set = createSet();

		for (int i = 1; i <= NELEMS; i++) {
			set.add(rand.nextInt());
			assertTrue(isSorted(set));
			assertEquals(i, set.size());
		}
	}

	@Test
	public void testAddMultipleSimple() {
		SortedSet<Integer> set = createSet();

		assertTrue(set.add(10));
		assertTrue(set.add(15));

		assertFalse(set.add(10));
		assertFalse(set.add(15));

		assertTrue(set.add(5));
		assertFalse(set.add(5));

		assertEquals(3, set.size());
	}

	@Test
	public void testAddMultiple() {
		final int NELEMS = 1000;

		SortedSet<Integer> set = createSet();
		for (int i = 0; i < NELEMS; i++)
			assertTrue(set.add(i));

		int size = set.size();

		for (int i = 0; i < NELEMS; i++)
			assertFalse(set.add(i));

		assertEquals(size, set.size());
	}

	@Test
	public void testAddMultipleStrings() {
		final int NELEMS = 1000;

		SortedSet<String> set = createSet();
		for (int i = 0; i < NELEMS; i++)
			assertTrue(set.add(new Integer(i).toString()));

		int size = set.size();

		for (int i = 0; i < NELEMS; i++)
			assertFalse(set.add(new Integer(i).toString()));

		assertEquals(size, set.size());
	}

	@Test
	public void testAddMultipleComplexObjects() {
		final int NELEMS = 1000;

		Comparator<ComplexTestObject> comparator = (firstValue, secondValue) -> {
			return new Integer(firstValue.getNumber()).compareTo(secondValue.getNumber());
		};
		
		SortedSet<ComplexTestObject> set = createSet(comparator);
		for (int i = 0; i < NELEMS; i++) {
			ComplexTestObject obj = new ComplexTestObject(i);
			assertTrue(set.add(obj));
		}

		int size = set.size();

		for (int i = 0; i < NELEMS; i++) {
			ComplexTestObject obj = new ComplexTestObject(i);
			assertFalse(set.add(obj));
		}

		assertEquals(size, set.size());
	}

	@Test
	public void testGetSimple() {
		SortedSet<Integer> set = createSet();

		assertNull(set.get(5));
		set.add(5);
		assertEquals(5, set.get(5).intValue());
		assertNull(set.get(99));
	}
	
	@Test
	public void testGetString() {
		SortedSet<String> set = createSet();

		assertNull(set.get("Test"));
		set.add("Test");
		assertEquals("Test", set.get("Test"));
		assertNull(set.get("Test2"));
	}
	
	@Test
	public void testGetComplexObject() {
		Comparator<ComplexTestObject> comparator = (firstValue, secondValue) -> {
			return new Integer(firstValue.getNumber()).compareTo(secondValue.getNumber());
		};
		
		ComplexTestObject objToInsert = new ComplexTestObject(5);
		
		SortedSet<ComplexTestObject> set = createSet(comparator);
		assertNull(set.get(objToInsert));
		set.add(objToInsert);
		assertEquals(objToInsert.getNumber(), set.get(objToInsert).getNumber());
		assertNull(set.get(new ComplexTestObject(99)));
	}

	@Test
	public void testGet() {
		final int NELEMS = 1000;

		SortedSet<Integer> set = createSet();
		for (int i = 0; i < NELEMS; i++)
			set.add(i);

		for (int i = 0; i < NELEMS; i++)
			assertEquals(i, set.get(i).intValue());
	}

	@Test
	public void testContainsSimple() {
		SortedSet<Integer> set = createSet();
		set.add(3);
		set.add(1);
		set.add(5);

		assertTrue(set.contains(1));
		assertTrue(set.contains(3));
		assertTrue(set.contains(5));

		assertFalse(set.contains(0));
		assertFalse(set.contains(2));
		assertFalse(set.contains(4));
		assertFalse(set.contains(6));
	}

	@Test
	public void testContains() {
		final int NELEMS = 1000;

		SortedSet<Integer> set = createSet();
		for (int i = 0; i < NELEMS; i++)
			set.add(i);

		for (int i = 0; i < NELEMS; i++)
			assertTrue(set.contains(i));
	}

	@Test
	public void testIteratorSimple() {
		SortedSet<Integer> set = createSet();
		set.add(10);
		set.add(5);
		set.add(15);

		Iterator<Integer> it = set.iterator();

		assertTrue(it.hasNext());
		assertEquals(new Integer(5), it.next());

		assertTrue(it.hasNext());
		assertEquals(new Integer(10), it.next());

		assertTrue(it.hasNext());
		assertEquals(new Integer(15), it.next());

		assertFalse(it.hasNext());
	}

	@Test
	public void testIterator() {
		final int NELEMS = 10000;
		SortedSet<Integer> set = createSet();

		for (int i = 1; i <= NELEMS; i++)
			set.add(i);

		Iterator<Integer> it = set.iterator();
		int prev = it.next();
		assertEquals(1, prev);

		while (it.hasNext()) {
			int curr = it.next();
			assertTrue(prev + 1 == curr);
			prev = curr;
		}
	}
	
	@Test
	public void testIteratorWithComplexObject() {
		final int NELEMS = 10000;
		Comparator<ComplexTestObject> comparator = (firstValue, secondValue) -> {
			return new Integer(firstValue.getNumber()).compareTo(secondValue.getNumber());
		};
		
		SortedSet<ComplexTestObject> set = createSet(comparator);

		for (int i = 1; i <= NELEMS; i++)
			set.add(new ComplexTestObject(i));

		Iterator<ComplexTestObject> it = set.iterator();
		int prev = it.next().getNumber();
		assertEquals(1, prev);

		while (it.hasNext()) {
			int curr =  it.next().getNumber();
			assertTrue(prev + 1 == curr);
			prev = curr;
		}
	}

	@Test(expected = NoSuchElementException.class)
	public void testIteratorException1() {
		SortedSet<Integer> set = createSet();
		Iterator<Integer> it = set.iterator();
		it.next();
	}

	@Test(expected = NoSuchElementException.class)
	public void testIteratorException2() {
		SortedSet<Integer> set = createSet();
		set.add(1);

		Iterator<Integer> it = set.iterator();
		assertNotNull(it.next());
		it.next();
	}

	@Test(expected = NoSuchElementException.class)
	public void testFirstWithEmptySet() {
		SortedSet<Integer> set = createSet();
		set.first();
	}

	@Test(expected = NoSuchElementException.class)
	public void testLastWithEmptySet() {
		SortedSet<Integer> set = createSet();
		set.last();
	}

	@Test
	public void testFirstLast() {
		final int NELEMS = 100;
		SortedSet<Integer> set = createSet();
		Integer min = Integer.MAX_VALUE;
		Integer max = Integer.MIN_VALUE;
		Random rand = new Random();

		for (int i = 1; i <= NELEMS; i++) {
			int r = rand.nextInt();
			set.add(r);
			min = Math.min(min, r);
			max = Math.max(max, r);
		}

		assertEquals(min, set.first());
		assertEquals(max, set.last());
	}

	@Test
	public void testConstructorWithComparator() {
		SortedSet<Integer> set1 = createSet();
		assertNull(set1.comparator());
		SortedSet<Integer> set2 = createSet((i1, i2) -> i1.compareTo(i2));
		assertNotNull(set2.comparator());
	}

	@Test
	public void testComparator() {
		final int NELEMS = 100;
		SortedSet<Integer> set = createSet((i1, i2) -> i2.compareTo(i1));
		Random rand = new Random();

		for (int i = 1; i <= NELEMS; i++)
			set.add(rand.nextInt());

		assertTrue(isSortedInComparatorOrder(set));
	}

	@Test
	public void testHeightForEmptySet() {
		SortedTreeSet<Integer> set = createSet((i1, i2) -> i2.compareTo(i1));
		assertEquals(0, set.height());
	}

	protected boolean isSorted(SortedSet<Integer> set) {
		Iterator<Integer> it = set.iterator();
		if (!it.hasNext())
			return true;

		int prev = it.next();
		while (it.hasNext()) {
			int curr = it.next();
			if (!(prev < curr))
				return false;
			prev = curr;
		}

		return true;
	}

	protected boolean isSortedInComparatorOrder(SortedSet<Integer> set) {
		Iterator<Integer> it = set.iterator();
		if (!it.hasNext())
			return true;

		int prev = it.next();
		while (it.hasNext()) {
			int curr = it.next();
			if (set.comparator().compare(prev, curr) >= 0)
				return false;
			prev = curr;
		}

		return true;
	}

}
