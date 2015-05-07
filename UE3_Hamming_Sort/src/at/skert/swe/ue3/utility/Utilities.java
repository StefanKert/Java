package at.skert.swe.ue3.utility;

import java.util.Random;

public class Utilities {
	public static void swapArrayElements(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	
	public static int[] generateRandomLongArray(int size){
		int[] generatedArray = new int[size];
		Random rand = new Random();
		for(int i = 0; i < size; i++){
			generatedArray[i] = rand.nextInt();
		}
		return generatedArray;
	}
	
	public static boolean isArraySorted(int[] array){
	    for (int i = 0; i < array.length - 1; i++) {
	        if (array[i] > array[i + 1]) {
	        	return false;
	        }
	    }
	    return true;
	}
	
	public static double getSecondsForNanoSeconds(long nanoSeconds){
		return nanoSeconds / 1000000000.00; // The digits after the point are for keeping really small values in double
	}
	public static double getMilliSecondsForNanoSeconds(long nanoSeconds){
		return nanoSeconds / 1000000.00; // The digits after the point are for keeping really small values in double
	}
	
	public static long getMinimum(long firstVal, long secondVal, long thirdVal) {
		return Math.min(firstVal, Math.min(secondVal, thirdVal));
	}
}
