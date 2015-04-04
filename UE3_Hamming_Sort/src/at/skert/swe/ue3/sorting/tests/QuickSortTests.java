package at.skert.swe.ue3.sorting.tests;

import at.skert.swe.ue3.utility.*;
import at.skert.swe.ue3.sorting.QuickSort;

public class QuickSortTests {
	public void AssertQuickSortArrayIsSorted() throws Exception{
		int[] randomArray = Utilities.generateRandomLongArray(100);
		QuickSort quick = new QuickSort();
		quick.sort(randomArray);
	    for (int i = 0; i < randomArray.length - 1; i++) {
	        if (randomArray[i] > randomArray[i + 1]) {
	        	throw new Exception("Array is not sorted valid");
	        }
	    }
		System.out.println("QuickSort Array is sorted correctly.");
	}
}
