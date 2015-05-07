package at.skert.swe.ue3.hamming.tests;

import java.util.ArrayList;
import java.util.Arrays;

import at.skert.swe.ue3.hamming.Hamming;
import at.skert.swe.ue3.utility.Utilities;

public class HammingTests {
	public void executeAll(){
		HammingTests hammingTests = new HammingTests();
		hammingTests.testGenerate10000ThHammingNumber();
		System.out.println("\n\n");
		hammingTests.testGenerateFirst10HammingNumbers();
		System.out.println("\n\n");
		hammingTests.testGenerateHammingNumbersUntil12();
		System.out.println("\n\n");
	}
	
	public void testGenerate10000ThHammingNumber(){
		Hamming h = new Hamming();
		
		long startTime = System.nanoTime();
		long number = h.calculateSmoothNumberBy5(10000);
		long endTime = System.nanoTime();	
		double timeInSeconds = Utilities.getSecondsForNanoSeconds(endTime - startTime);
		
		System.out.println("The 10000. Hamming Number is " + number);
		System.out.println("It took " + timeInSeconds + "s to generate number.");	
	}
	
	public void testGenerateFirst10HammingNumbers(){
		Hamming h = new Hamming();
		
		long startTime = System.nanoTime();
		ArrayList<Long> numbers = h.calculateFixedAmountOfSmoothNumbersBy5(10);
		long endTime = System.nanoTime();
		double timeInMilliSeconds = Utilities.getMilliSecondsForNanoSeconds(endTime - startTime);
		
		System.out.println(Arrays.toString(numbers.toArray()));
		System.out.println("It took " + timeInMilliSeconds + "ms to generate number.");	
	}
	
	public void testGenerateHammingNumbersUntil12(){
		Hamming h = new Hamming();
		
		long startTime = System.nanoTime();
		ArrayList<Long> numbers = h.calculateSmoothNumbersBy5UntilBoundary(12);
		long endTime = System.nanoTime();
		
		double timeInMilliSeconds = Utilities.getMilliSecondsForNanoSeconds(endTime - startTime);		
		System.out.println(Arrays.toString(numbers.toArray()));
		System.out.println("It took " + timeInMilliSeconds + "ms to generate number.");	
	}
}