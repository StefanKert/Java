package tests;

import java.util.Random;

import queues.Heap;

public class PQueueTest {
	public static void main(String[] args){
		Heap<Integer> h = new Heap<Integer>();
		h.enqueue(1);
		System.out.println(h);
		h.enqueue(2);
		System.out.println(h);
		
		Random r = new Random();
		for(int i = 0; i < 10; i++){
			h.enqueue(r.nextInt(100));
		}
		
		System.out.println(h);
		
		while(!h.isEmpty()){
			System.out.println(h.dequeue());
		}
	}
}