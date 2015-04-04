package at.skert.swe.ue3.hamming;

import java.util.ArrayList;


public class Hamming {
	final static long two = 2;
	final static long three = 3;
	final static long five = 5;
	private ArrayList<Long> hammingNumbers;
	
	interface IHammingBreakCondition {
		boolean CheckIfCurrentValueShouldBeAdded(long currentValue);
	}
	public Hamming(){
		hammingNumbers = new ArrayList<Long>();
	}
	
	public ArrayList<Long> calculateSmoothNumbersBy5UntilBoundary(int boundary){
		return calculateSmoothNumbersBy5(currentValue -> currentValue > boundary);
	}
	public ArrayList<Long> calculateFixedAmountOfSmoothNumbersBy5(int amount){
		return calculateSmoothNumbersBy5(nextValue -> hammingNumbers.size() >= amount);
	}
	public long calculateSmoothNumberBy5(int n){
		ArrayList<Long> list = calculateFixedAmountOfSmoothNumbersBy5(n);
		return list.get(list.size() - 1);
	}
	
	private ArrayList<Long> calculateSmoothNumbersBy5(IHammingBreakCondition breakCondition) {
		hammingNumbers.clear();
		hammingNumbers.add((long) 1);
		int i = 0, j = 0, k = 0;
		long x2 = two, x3 = three, x5 = five;
		int index = 1;
		while(true){
			long currentValue = Math.min(x2, Math.min(x3, x5));
			if(breakCondition.CheckIfCurrentValueShouldBeAdded(currentValue)){
				break;
			}
			hammingNumbers.add(currentValue);
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
	
	private long recalculateValue(long fixedValue, int listIndex){
		return fixedValue * hammingNumbers.get(listIndex);
	}
}