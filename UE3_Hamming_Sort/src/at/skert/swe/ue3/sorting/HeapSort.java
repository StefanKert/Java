package at.skert.swe.ue3.sorting;

public class HeapSort extends IntSortBase{
    private int total;
    
    public void sort(int[] values)
    {
		if (!isSortingNeeded(values)) 
			return;
		
        total = values.length - 1;

        // Create Binary Tree in Array
        for (int i = total / 2; i >= 0; i--)
            heapify(values, i);

        // Sink elements in the tree
        for (int i = total; i > 0; i--) {
            swap(values, 0, i);
        	total--;
            heapify(values, 0);
        }
    }


    private void heapify(int[] values, int i)
    {
        int left = i * 2;
        int right = left + 1;
        int grt = i;

        if (lessOrEqual(left, total) && less(values[grt], values[left])){
        	grt = left;
        }
        if (lessOrEqual(right, total) && less(values[grt], values[right])){
        	grt = right;
        }
        if (grt != i) {
        	swap(values, i, grt);
            heapify(values, grt);
        }
    }
}
