package at.skert.swe.ue3;

import java.util.ArrayList;
import java.util.Random;

import at.skert.swe.ue3.hamming.Hamming;
import at.skert.swe.ue3.sorting.HeapSort;
import at.skert.swe.ue3.sorting.QuickSort;
import at.skert.swe.ue3.sorting.tests.HeapSortTests;
import at.skert.swe.ue3.sorting.tests.QuickSortTests;

public class Startup {

	public static void main(String[] args) {
		long expectedSize = 10000;
		long startTime = System.nanoTime();
		ArrayList<Long> smootNumbers = new Hamming().calculateFixedAmountOfSmoothNumbersBy5((int)expectedSize);
		long smootNumber = smootNumbers.get((int) (smootNumbers.size() - 1));
		long endTime = System.nanoTime();
		System.out.println(smootNumber + " took " + ((endTime - startTime) / 1000000000) + " s to calculate");

		HeapSortTests hTests = new HeapSortTests();
		try {
			hTests.AssertQuickSortArrayIsSorted();
		} catch (Exception e) {
			System.err.println("HeapSort failed: " + e.toString());
			e.printStackTrace();
		}
		
		QuickSortTests qTests = new QuickSortTests();
		try {
			qTests.AssertQuickSortArrayIsSorted();
		} catch (Exception e) {
			System.err.println("QuickSort failed: " + e.toString());
			e.printStackTrace();
		}
	}

}
