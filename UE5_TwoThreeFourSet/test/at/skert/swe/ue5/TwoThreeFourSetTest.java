package at.skert.swe.ue5;

import static org.junit.Assert.assertTrue;

import java.util.Comparator;
import org.junit.Test;
import at.skert.swe.ue5.implementation.TwoThreeFourTreeSet;
import at.skert.swe.ue5.interfaces.SortedTreeSet;

public class TwoThreeFourSetTest extends SortedTreeSetTestBase {
  
  @Override
  protected <T> TwoThreeFourTreeSet<T> createSet(Comparator<T> comparator) {  
    return new TwoThreeFourTreeSet<T>(comparator);
  }

	@Override
	protected <T> TwoThreeFourTreeSet<T> callDefaultCtor(){
		return new TwoThreeFourTreeSet<T>();
	}
  
  @Test
  public void testHeight() {
    final int NELEMS = 10000;
    SortedTreeSet<Integer> set = createSet();

    for (int i=1; i<=NELEMS; i++) {
      set.add(i);
      int h = set.height();
      int n = set.size();
      assertTrue("height(set) <= ld(size(set))+1", h <= Math.log((double)n)/Math.log(2.0)+1);
    }
  }
}
