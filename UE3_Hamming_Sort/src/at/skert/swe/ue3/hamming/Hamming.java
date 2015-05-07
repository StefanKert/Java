package at.skert.swe.ue3.hamming;

import java.util.ArrayList;

import at.skert.swe.ue3.utility.Utilities;


public class Hamming {
	final static long two = 2;
	final static long three = 3;
	final static long five = 5;
	private ArrayList<Long> hammingNumbers;
	
	/**
	 * Defines BreakCondition for calculating Smooth Numbers
	 */
	interface IHammingBreakCondition {
		boolean CheckIfCurrentValueShouldBeAdded(long currentValue);
	}
	
	public Hamming(){
		hammingNumbers = new ArrayList<Long>();
	}
	
	/**
	 * Calculates the 5 smooth numbers until fixed boundary
	 */
	public ArrayList<Long> calculateSmoothNumbersBy5UntilBoundary(int boundary){
		return calculateSmoothNumbersBy5(currentValue -> currentValue <= boundary);
	}
	
	/**
	 * Calculates the fixed amount of 5 smooth numbers
	 */
	public ArrayList<Long> calculateFixedAmountOfSmoothNumbersBy5(int amount){
		return calculateSmoothNumbersBy5(nextValue -> hammingNumbers.size() < amount);
	}
	
	/**
	 * Calculates the n-th 5 smooth number
	 */
	public long calculateSmoothNumberBy5(int n){
		ArrayList<Long> list = calculateFixedAmountOfSmoothNumbersBy5(n);
		return list.get(list.size() - 1);
	}
	
	/**
	 * Calculates all 5 Smooth Numbers until the given condition returns false for the current value
	 */
	private ArrayList<Long> calculateSmoothNumbersBy5(IHammingBreakCondition breakCondition) {
		hammingNumbers.clear();
		hammingNumbers.add((long) 1); //Always add 1 because 1 is the first hamming Number
		int i = 0, j = 0, k = 0;
		long x2 = two, x3 = three, x5 = five;
		int index = 1;
		while(true){
			long currentValue = Utilities.getMinimum(x2, x3, x5);
			if(!breakCondition.CheckIfCurrentValueShouldBeAdded(currentValue)){
				break;
			}
			hammingNumbers.add(currentValue);
			//Recalculate values and increase index if needed
			if (hammingNumbers.get(index) == x2)
				x2 = recalculateValue(two, ++i);
			if (hammingNumbers.get(index) == x3)
				x3 = recalculateValue(three, ++j);
			if (hammingNumbers.get(index) == x5)
				x5 = recalculateValue(five, ++k);
			index++;
		}
		return hammingNumbers;
	}
	
	/**
	 * Returns the next Hamming Number for value in list.
	 */
	private long recalculateValue(long fixedValue, int listIndex){
		return fixedValue * hammingNumbers.get(listIndex);
	}
}