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
}
