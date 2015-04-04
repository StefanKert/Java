package at.skert.swe.ue3;

import java.util.ArrayList;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.Predicate;


public class Hamming {
	final static long two = 2;
	final static long three = 3;
	final static long five = 5;
	private long x2 = 2;
	private long x3 = 3;
	private long x5 = 5;
	private ArrayList<Long> hammingNumbers;
	interface IHammingBreakCondition {
		boolean CheckIfCurrentValueShouldBeAdded(long currentValue);
	}
	public Hamming(){
		hammingNumbers = new ArrayList<Long>();
	}
	public ArrayList<Long> calculateSmoothNumbersBy5UntilBoundary(int boundary){
		return calculateSmoothNumbersBy5(nextValue -> nextValue > boundary);
	}
	public ArrayList<Long> calculateFixedAmountOfSmoothNumbersBy5(int n){
		return calculateSmoothNumbersBy5(nextValue -> hammingNumbers.size() > n);
	}
	private ArrayList<Long> calculateSmoothNumbersBy5(IHammingBreakCondition breakCondition) {

		hammingNumbers.add((long) 1);
		int i = 0, j = 0, k = 0;
		int index = 1;
		while(true){
			long minValue = Math.min(x2, Math.min(x3, x5));
			if(breakCondition.CheckIfCurrentValueShouldBeAdded(minValue)){
				break;
			}
			hammingNumbers.add(minValue);
			i = SetX2IfNeeded(index, i);
			j = SetX3IfNeeded(index, j);
			k = SetX5IfNeeded(index, k);
			index++;
		}
		return hammingNumbers;
	}
	
	private int SetX2IfNeeded(int index,  int i){
		if (hammingNumbers.get(index) == x2)
			x2 = two * hammingNumbers.get(++i);
		return i;
	}
	private int SetX3IfNeeded(int index,  int j){
		if (hammingNumbers.get(index) == x3)
			x3 = three * hammingNumbers.get(++j);
		return j;
	}
	private int SetX5IfNeeded(int index,  int k){
		if (hammingNumbers.get(index) == x5)
			x5 = five * hammingNumbers.get(++k);
		return k;
	}

}
