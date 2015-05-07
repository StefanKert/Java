package at.skert.swe.ue3.sorting.tests;

import java.util.Arrays;

import at.skert.swe.ue3.utility.*;
import at.skert.swe.ue3.sorting.HeapSort;
import at.skert.swe.ue3.sorting.QuickSort;

public class SortTests {
	public void executeAll(){
		this.assertQuickSortArrayIsSorted();
		System.out.println("\n\n");
		this.assertHeapSortArrayIsSorted();
		System.out.println("\n\n");
		assertCompareHeapAndQuickSortWith1000Elements();
		System.out.println("\n\n");
		this.assertCompareHeapAndQuickSortWithLoops(1000, 1000);
		System.out.println("\n\n");
		this.assertCompareHeapAndQuickSortWithLoops(1000, 10000);
		System.out.println("\n\n");
		this.assertCompareHeapAndQuickSortWithLoops(1000, 100000);
		System.out.println("\n\n");
		this.assertCompareHeapAndQuickSortWithLoops(1000, 1000000);
		System.out.println("\n\n");
		this.assertCompareHeapAndQuickSortWithLoops(1000, 10000000);
		System.out.println("\n\n");
	}
	
	public void assertHeapSortArrayIsSorted() {
		int[] randomArray = Utilities.generateRandomLongArray(10);
		HeapSort heap = new HeapSort();
		
		heap.sort(randomArray);
		
	    if(!Utilities.isArraySorted(randomArray))
	    	System.err.println("HeapSort failed. Array is not sorted.");
		System.out.println("HeapSort Array is sorted correctly.");
		System.out.println(Arrays.toString(randomArray));
	}
	
	public void assertQuickSortArrayIsSorted() {
		int[] randomArray = Utilities.generateRandomLongArray(10);
		QuickSort quick = new QuickSort();
		
		quick.sort(randomArray);
		
	    if(!Utilities.isArraySorted(randomArray))
	    	System.err.println("QuickSort failed. Array is not sorted.");
		System.out.println("QuickSort Array is sorted correctly.");
		System.out.println(Arrays.toString(randomArray));
	}

	public void assertCompareHeapAndQuickSortWith1000Elements() {
		final int ARRAY_SIZE = 1000;
		int[] randomHeapArray = Utilities.generateRandomLongArray(ARRAY_SIZE);
		int[] randomQuickArray = new int[ARRAY_SIZE];
		System.arraycopy(randomHeapArray, 0, randomQuickArray, 0, ARRAY_SIZE);
		HeapSort heap = new HeapSort();
		QuickSort quick = new QuickSort();
		
		long startTime = System.nanoTime();
		heap.sort(randomHeapArray);
		long endTime = System.nanoTime();
		long heapSortTimeNs = endTime - startTime;
		double heapSortTimeMS = Utilities.getMilliSecondsForNanoSeconds(heapSortTimeNs);
		
		startTime = System.nanoTime();
		quick.sort(randomQuickArray);
		endTime = System.nanoTime();
		long quickSortTimeNs = endTime - startTime;
		double quickSortTimeMS = Utilities.getMilliSecondsForNanoSeconds(quickSortTimeNs);
		
		System.out.println("Quicksort:");	
		System.out.println(ARRAY_SIZE + " Elemente: \t" + quickSortTimeMS + "ms \t " + quick.getSwapCounter() + " Swaps \t " + quick.getCompareCounter() + " Compares");
		System.out.println("Heapsort:");	
		System.out.println(ARRAY_SIZE + " Elemente: \t" + heapSortTimeMS + "ms \t " + heap.getSwapCounter() + " Swaps \t " + heap.getCompareCounter() + " Compares");
	}
	
	public void assertCompareHeapAndQuickSortWithLoops(int loops, int size) {
		long startTime = 0, endTime = 0, heapSortTimeNs = 0, quickSortTimeNs = 0;
		long quickSwapCounterSum = 0, quickCompareCounterSum = 0, heapSwapCounterSum = 0, heapCompareCounterSum = 0;
		double heapSortTimeMsSum = 0, quickSortTimeMsSum = 0;
		for(int i = 0; i < loops; i++){
			HeapSort heap = new HeapSort();
			QuickSort quick = new QuickSort();
			int[] randomHeapArray = Utilities.generateRandomLongArray(size);
			int[] randomQuickArray = new int[size];
			System.arraycopy(randomHeapArray, 0, randomQuickArray, 0, size);
			startTime = System.nanoTime();
			heap.sort(randomHeapArray);
			endTime = System.nanoTime();
			heapSortTimeNs = endTime - startTime;
			heapSortTimeMsSum += Utilities.getMilliSecondsForNanoSeconds(heapSortTimeNs);
			heapSwapCounterSum += heap.getSwapCounter();
			heapCompareCounterSum += heap.getCompareCounter();
			
			startTime = System.nanoTime();
			quick.sort(randomQuickArray);
			endTime = System.nanoTime();
			quickSortTimeNs = endTime - startTime;
			quickSortTimeMsSum += Utilities.getMilliSecondsForNanoSeconds(quickSortTimeNs);
			quickSwapCounterSum += quick.getSwapCounter();
			quickCompareCounterSum += quick.getCompareCounter();
		}
		
		
		System.out.println("Quicksort:");	
		System.out.println(size + "\t" + quickSortTimeMsSum / loops + "ms \t " + quickSwapCounterSum / loops + " \t " + quickCompareCounterSum / loops);
		System.out.println("Heapsort:");	
		System.out.println(size + "\t" + heapSortTimeMsSum / loops + "ms \t " + heapSwapCounterSum / loops + " \t " + heapCompareCounterSum / loops);
	}
}
