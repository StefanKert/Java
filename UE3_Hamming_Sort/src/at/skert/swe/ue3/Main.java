package at.skert.swe.ue3;
import at.skert.swe.ue3.hamming.tests.HammingTests;
import at.skert.swe.ue3.sorting.tests.SortTests;

public class Main {

	public static void main(String[] args) {
		// Execute Hamming Tests.
		HammingTests hammingTests = new HammingTests();
		hammingTests.executeAll();
		
			
		//Executing SortTests
		SortTests sortTests = new SortTests();
		sortTests.executeAll();
	}

}
