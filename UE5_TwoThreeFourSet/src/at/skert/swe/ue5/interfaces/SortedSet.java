package at.skert.swe.ue5.interfaces;

import java.util.Comparator;
import java.util.Iterator;

public interface SortedSet<T> extends Iterable<T> {
	boolean add(T elem);
	T get(T elem); 
	boolean contains(T elem); 
	int size(); 
	T first(); 
	T last(); 
	boolean isEmpty();
	Comparator<T> comparator(); 
	Iterator<T> iterator();
}
