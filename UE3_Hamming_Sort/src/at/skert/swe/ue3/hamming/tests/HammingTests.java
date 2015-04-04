package at.skert.swe.ue3.hamming.tests;

import at.skert.swe.ue3.hamming.Hamming;

public class HammingTests {
	public void TestGenerateSpecificHammingNumber(int n){
		Hamming h = new Hamming();
		long startTime = System.nanoTime();
		long number = h.calculateSmoothNumberBy5(n);
		long endTime = System.nanoTime();
		double timeInSeconds = (endTime - startTime) / 1000000000.0; // The digits after the point are for keeping really small values in double
		System.out.println("The " + n + ". Hamming Number is " + number);
		System.out.println("It took " + timeInSeconds + "s to generate number.");	
	}
}