package at.skert.swe.ue5.implementation;

import at.skert.swe.ue5.interfaces.SortedTreeSet;

public class Main {

	public static void main(String[] args) {
		final int NELEMS = 10000;
		SortedTreeSet<Integer> set = new TwoThreeFourTreeSet<Integer>();
		for (int i = 0; i < NELEMS; i++)
			set.add(i);

		System.out.println("Es sind " + set.size() + " Elemente im Baum und der Baum ist " + set.height() + " Knoten hoch.");
	}

}
