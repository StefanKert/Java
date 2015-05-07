package at.skert.swe.ue3.sorting;

import at.skert.swe.ue3.utility.Utilities;

public abstract class IntSortBase {
    private long _swapCounter = 0;
    private long _compareCounter = 0;
	
    protected boolean isSortingNeeded(int[] arr){
		if (arr == null || arr.length < 2) {
			return false;
		}
		return true;
    }
    
	protected boolean less(int firstValue, int secondValue) {
		_compareCounter++;
		return firstValue < secondValue;
	}
	protected boolean lessOrEqual(int firstValue, int secondValue) {
		_compareCounter++;
		return firstValue <= secondValue;
	}
	protected void swap(int[] arr, int i, int j){
		_swapCounter++;
		Utilities.swapArrayElements(arr, i, j);
	}
	
	public long getCompareCounter() {
		return _compareCounter;
	}	

	public long getSwapCounter() {
		return _swapCounter;
	}
}
