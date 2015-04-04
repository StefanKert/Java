package at.skert.swe.ue3;

import java.util.ArrayList;

public class Hamming {
	final static long two = 2;
	final static long three = 3;
	final static long five = 5;
	private long x2 = 2;
	private long x3 = 3;
	private long x5 = 5;
	private ArrayList<Long> hammingNumbers;
	
	public Hamming(){
		hammingNumbers = new ArrayList<Long>();
	}
	public long calculateSmoothNumber5(int n) {

		hammingNumbers.add((long) 1);
		int i = 0, j = 0, k = 0;
		int index = 1;
		while(index < n){
			hammingNumbers.add(getMinimum(x2, x3, x5));
			i = SetX2IfNeeded(index, i);
			j = SetX3IfNeeded(index, j);
			k = SetX5IfNeeded(index, k);
			index++;
		}
		return hammingNumbers.get(n - 1);
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
	
	public ArrayList<Long> calculateSmoothNumber5Range(int n) {
		ArrayList<Long> numbers = new ArrayList<Long>();
		long number;
		int i = 1;
		while (numbers.size() < n) {
			number = calculateSmoothNumber5(i);
			numbers.add(number);
			i++;
		}
		return numbers;
	}

	private long getMinimum(long x1, long x2, long x3) {
		return Math.min(x1, Math.min(x2, x3));
	}
}
