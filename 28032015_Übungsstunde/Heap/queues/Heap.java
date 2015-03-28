package queues;

import java.util.ArrayList;

public class Heap<T extends Comparable<T>>{
	private ArrayList<T> _values;
	
	public Heap(){
		_values = new ArrayList<T>();
	}
	
	private boolean less(T a, T b){
		return a.compareTo(b) < 0;
	}
	
	public boolean isEmpty(){
		return _values.isEmpty();
	}
	
	public void enqueue(T x){
		_values.add(x);
		upHeap();
	}
	
	public T peek(){
		if(isEmpty())
			return null;
		return _values.get(0);
	}
	
	public T dequeue() throws IllegalStateException{
		if(isEmpty())
			throw new IllegalStateException("cannot dequeue from empty heap");
		T top = peek();
		_values.set(0, _values.get(_values.size() - 1));
		_values.remove(_values.size() - 1);
		if(!isEmpty())
			downHeap();
		return top;
	}
	
	private static int parent(int i){
		return (i-1)/2; 
	}
	private static int left(int i){
		return i*2+1;
	}
	private static int right(int i){
		return i*2+2;
	}
	
	private void upHeap(){
		int i = _values.size() - 1;
		T x = _values.get(i);
		while(i != 0 && less(_values.get(parent(i)), x)){
			_values.set(i, _values.get(parent(i)));
			i = parent(i);
		}
		_values.set(i, x);
	}
	
	private void downHeap(){
		int i = 0;
		T x = _values.get(i);
		while(left(i) < _values.size()){
			int j = left(i);
			if(right(i) < _values.size() && less(_values.get(left(i)), _values.get(right(i))))
				j = right(i);
			if(!less(x, _values.get(j)))
				break;
			_values.set(i, _values.get(j));
			i = j;
		}
		_values.set(i, x);
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("heap = [");
		for(int i = 0; i < _values.size(); i++){
			if(i > 0) sb.append(' ');
			sb.append(_values.get(i));
		}
		sb.append(']');
		return sb.toString();
	}
}