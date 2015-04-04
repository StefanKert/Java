package at.skert.swe.ue3;

import java.util.ArrayList;

public class Startup {

	public static void main(String[] args) {
		long expectedSize = 7;
		long startTime = System.nanoTime();
		ArrayList<Long> smootNumbers = new Hamming().calculateSmoothNumbersBy5((int)expectedSize);
		long smootNumber = smootNumbers.get((int) (smootNumbers.size() - 1));
		long endTime = System.nanoTime();
		System.out.println(smootNumber + " took " + ((endTime - startTime) / 1000000000) + " s to calculate");
	}
}
