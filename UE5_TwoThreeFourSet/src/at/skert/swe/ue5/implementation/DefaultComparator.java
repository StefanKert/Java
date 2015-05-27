package at.skert.swe.ue5.implementation;

import java.util.Comparator;

public class DefaultComparator<T> implements Comparator<T> {
	@SuppressWarnings("unchecked")
	public int compare(T a, T b) {
		return ((Comparable<T>)a).compareTo(b);
	}
}
