package at.skert.swe.ue3;

import java.util.ArrayList;

import at.skert.swe.ue3.hamming.Hamming;
import at.skert.swe.ue3.sorting.tests.SortTests;

public class Startup {

	public static void main(String[] args) {
		long expectedSize = 10000;
		long startTime = System.nanoTime();
		ArrayList<Long> smootNumbers = new Hamming().calculateFixedAmountOfSmoothNumbersBy5((int)expectedSize);
		long smootNumber = smootNumbers.get((int) (smootNumbers.size() - 1));
		long endTime = System.nanoTime();
		System.out.println(smootNumber + " took " + ((endTime - startTime) / 1000000000) + " s to calculate");

		SortTests sortTests = new SortTests();
		try {
			sortTests.AssertQuickSortArrayIsSorted();
		} catch (Exception e) {
			System.err.println("HeapSort failed: " + e.toString());
			e.printStackTrace();
		}
		
		try {
			sortTests.AssertQuickSortArrayIsSorted();
		} catch (Exception e) {
			System.err.println("QuickSort failed: " + e.toString());
			e.printStackTrace();
		}
		System.out.println("\n\n");
		try {
			sortTests.AssertCompareHeapAndQuickSort(1000);
			System.out.println("\n\n");
			sortTests.AssertCompareHeapAndQuickSort(10000);
			System.out.println("\n\n");
			sortTests.AssertCompareHeapAndQuickSort(100000);
			System.out.println("\n\n");
			sortTests.AssertCompareHeapAndQuickSort(1000000);
			System.out.println("\n\n");
			sortTests.AssertCompareHeapAndQuickSort(10000000);
			System.out.println("\n\n");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
