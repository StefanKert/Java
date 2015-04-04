package at.skert.swe.ue3.sorting;

import at.skert.swe.ue3.utility.Utilities;

public class QuickSort {
	private int[] numbers;
	private int number;

	public void sort(int[] values) {
		if (values == null || values.length == 0) {
			return;
		}
		numbers = values;
		number = values.length;
		quicksort(0, number - 1);
	}

	private void quicksort(int low, int high) {
		int i = low, j = high;
		int pivot = numbers[low + (high - low) / 2];

		while (i <= j) {
			while (numbers[i] < pivot) {
				i++;
			}
			while (numbers[j] > pivot) {
				j--;
			}
			if (i <= j) {
				Utilities.swapArrayElements(numbers, i, j);
				i++;
				j--;
			}
		}
		if (low < j)
			quicksort(low, j);
		if (i < high)
			quicksort(i, high);
	}
}
