package at.skert.swe.ue3;

import at.skert.swe.ue3.hamming.tests.HammingTests;
import at.skert.swe.ue3.sorting.tests.SortTests;

public class Startup {

	public static void main(String[] args) {
		HammingTests hammingTests = new HammingTests();
		hammingTests.TestGenerateSpecificHammingNumber(10000);
		
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
