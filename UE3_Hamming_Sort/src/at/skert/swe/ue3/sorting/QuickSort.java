package at.skert.swe.ue3.sorting;

public class QuickSort extends IntSortBase {
	public void sort(int[] values) {
		if (!isSortingNeeded(values)) 
			return;
		quicksort(values, 0, values.length - 1);
	}

	private void quicksort(int[] values, int low, int high) {
		int i = low, j = high;
		int pivot = values[low + (high - low) / 2]; // Get centered element.

		//arrange elements in array
		while (lessOrEqual(i, j)) {
			while (less(values[i], pivot)) {
				i++;
			}
			while (less(pivot, values[j])) {
				j--;
			}
			if (lessOrEqual(i, j)) {
				swap(values, i, j);
				i++;
				j--;
			}
		}
		//recursive call for the left side
		if (less(low, j)){
			quicksort(values, low, j);
		}
		//recursive call for the right side
		if (less(i, high)){
			quicksort(values, i, high);
		}
	}	
}