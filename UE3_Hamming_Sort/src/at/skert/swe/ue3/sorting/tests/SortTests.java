package at.skert.swe.ue3.sorting.tests;

import at.skert.swe.ue3.utility.*;
import at.skert.swe.ue3.sorting.HeapSort;
import at.skert.swe.ue3.sorting.QuickSort;

public class SortTests {
	public void AssertHeapSortArrayIsSorted() throws Exception{
		int[] randomArray = Utilities.generateRandomLongArray(100);
		HeapSort heap = new HeapSort();
		heap.sort(randomArray);
	    for (int i = 0; i < randomArray.length - 1; i++) {
	        if (randomArray[i] > randomArray[i + 1]) {
	        	throw new Exception("Array is not sorted valid");
	        }
	    }
		System.out.println("HeapSort Array is sorted correctly.");
	}
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
	public void AssertCompareHeapAndQuickSort() throws Exception{
		int[] randomArray = Utilities.generateRandomLongArray(100);
		HeapSort heap = new HeapSort();
		heap.sort(randomArray);
		QuickSort quick = new QuickSort();
		quick.sort(randomArray);
		System.out.println("SwapCounter: ");
		System.out.println("QuickSort: " + quick.SwapCounter + " || HeapSort: " + heap.SwapCounter);
		System.out.println("CompareCounter: ");
		System.out.println("QuickSort: " + quick.CompareCounter + " || HeapSort: " + heap.CompareCounter);
	}
}
