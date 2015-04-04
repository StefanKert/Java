package at.skert.swe.ue3.sorting;

import at.skert.swe.ue3.utility.Utilities;

public class HeapSort {
    private int total;
    public int SwapCounter;
    public int CompareCounter;
    
    public void sort(int[] arr)
    {
        total = arr.length - 1;

        for (int i = total / 2; i >= 0; i--)
            heapify(arr, i);

        for (int i = total; i > 0; i--) {
            Utilities.swapArrayElements(arr, 0, i);
        	SwapCounter++;
            total--;
            heapify(arr, 0);
        }
    }

    private void heapify(int[] arr, int i)
    {
        int lft = i * 2;
        int rgt = lft + 1;
        int grt = i;

        if (lft <= total && arr[lft] > arr[grt]){
        	CompareCounter++;
        	grt = lft;
        }
        if (rgt <= total && arr[rgt] > arr[grt]){
        	CompareCounter++;
        	grt = rgt;
        }
        if (grt != i) {
        	Utilities.swapArrayElements(arr, i, grt);
        	SwapCounter++;
            heapify(arr, grt);
        }
    }
}
