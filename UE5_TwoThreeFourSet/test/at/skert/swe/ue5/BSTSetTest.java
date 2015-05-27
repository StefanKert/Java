package at.skert.swe.ue5;

import static org.junit.Assert.*;

import java.util.Comparator;

import org.junit.Test;

import at.skert.swe.ue5.implementation.BSTSet;
import at.skert.swe.ue5.interfaces.SortedTreeSet;

public class BSTSetTest extends SortedTreeSetTestBase {

	@Override
	protected <T> BSTSet<T> createSet(Comparator<T> comparator) {
		return new BSTSet<T>(comparator);
	}

	@Override
	protected <T> BSTSet<T> callDefaultCtor(){
		return new BSTSet<T>();
	}
	
	@Test
	public void testHeight() {
		final int[] values = new int[] {1, 2, 3, 0, 5};
		SortedTreeSet<Integer> set = createSet();

		for (int i = 1; i <= values.length - 1; i++) {
			set.add(values[i]);
		}
		assertEquals(3, set.height());
	}
}