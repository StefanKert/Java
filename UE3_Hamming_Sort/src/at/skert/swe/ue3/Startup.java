package at.skert.swe.ue3;

import java.math.BigInteger;
import java.util.ArrayList;

public class Startup {

	public static void main(String[] args) {
		System.out.println("Testen");

		final int five = 5;
		final int four = 4;
		final int three = 3;

		int x = 0;
		int y = 0;
		int z = 0;
		long expectedSize = 10000;
		long startTime = System.nanoTime();
		long smootNumber = new Hamming().calculateSmoothNumber5((int)expectedSize);
		long endTime = System.nanoTime();
		System.out.println(smootNumber + " took " + ((endTime - startTime) / 1000000000) + " s to calculate");
	}
}
