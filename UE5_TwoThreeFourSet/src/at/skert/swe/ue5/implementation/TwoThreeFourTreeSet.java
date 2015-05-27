package at.skert.swe.ue5.implementation;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import at.skert.swe.ue5.interfaces.SortedTreeSet;

public class TwoThreeFourTreeSet<T> implements SortedTreeSet<T> {
	private static class TwoThreeFourIterator<T> implements Iterator<T> {
		private Node<T> currentNode;
		private int currentIndex;
		
		public TwoThreeFourIterator(Node<T> root) {
			currentNode = root;
			currentIndex = 0;
			setToStartNode();
		}
		
		private void setToStartNode(){		
			while (currentNode != null && currentNode.hasChildren()) {
				currentNode = currentNode.getFirstChildNode();		
			}
		}

		@Override
		public boolean hasNext() {
			if(currentNode == null) 
				return false;			
			if(!currentNode.isLeaf() || !isCurrentIndexLastElementInNode()) 
				return true;
			Node<T> node = currentNode;
			while(node.hasParent()) {
				if(!isNodeLastChildNodeOfParent(node))
					return true;				
				node = node.getParent();
			}
			return false;			
		}

		private boolean isCurrentIndexLastElementInNode(){
			return currentIndex >= currentNode.countElements();
		}
		
		private boolean isNodeLastChildNodeOfParent(Node<T> node){
			return node == node.getParent().getLastChildNode();
		}
		

		@Override
		public T next() {
			if (!hasNext())
				throw new NoSuchElementException();
			
			if(areFurtherChildrenAvailableForCurrentNode()) {
				currentNode = currentNode.getChild(currentIndex);
				while (currentNode.hasChildren()) {
					currentNode = currentNode.getFirstChildNode();				
				}
				currentIndex = 0;
			}
			
			while(isCurrentIndexLastElementInNode()) {
				currentIndex = currentNode.getPositionInParent();
				currentNode = currentNode.getParent();
			}
			return currentNode.getElement(currentIndex++);
		}
		
		private boolean areFurtherChildrenAvailableForCurrentNode(){
			return currentIndex < currentNode.countChildren();
		}
	}

	private static class Node<T> {
		private final int max_elements = 3;
		private Comparator<T> comparator;
		private LinkedList<Node<T>> children;
		private LinkedList<T> elements;
		private Node<T> parent;
		
		private Node(Comparator<T> comparator){
			this.comparator = comparator;
			this.elements = new LinkedList<T>();
			this.children = new LinkedList<Node<T>>();
		}
		
		public int countElements() {
			return elements.size();
		}

		public int countChildren() {
			return children.size();
		}
		
		private boolean isFull(){
			return elements.size() == max_elements;
		}
		
		private boolean isLeaf(){
			return children.size() == 0;
		}
		
		private boolean isEmpty(){
			return elements.isEmpty();
		}
		
		private boolean hasChildren(){
			return !children.isEmpty();
		}
		
		private Node<T> getParent(){
			return parent;
		}
		
		private boolean hasParent(){
			return parent != null;
		}
		
		private void insertElement(T element){
			elements.add(element);
			elements.sort(comparator);
		}
		
		private int getPositionInParent() {
			if(parent.isEmpty())
				throw new NoSuchElementException();
			for (int i = 0; i < parent.countElements(); i++) {
				if(comparator.compare(getLastElement(), parent.getElement(i))  <0 )				
					return i;
			}
			return parent.countElements();			
		}
		
		private void addChild(Node<T> child) {		
			child.parent = this;
			children.add(child);
			children.sort((firstValue, secondValue) -> {
				return comparator.compare(firstValue.elements.getLast(), secondValue.elements.getFirst());
			});
		}
		
		private void removeChild(Node<T> child){
			children.remove(child);
		}
		
		private T getValue(T element) {
			for(T current : elements) {
				if(comparator.compare(current, element) == 0)
					return current;
			}
			return null;
		}
		
		private Node<T> getSuccessorNodeForElement(T element) {
			if(isEmpty() || !hasChildren())
				return null;

			for(int i = 0; i < countElements(); i++){
				if(comparator.compare(element, getElement(i)) < 0)
					return getChild(i);			
			}
			return getLastChildNode();
		}

		private T getFirstElement() {
			return elements.getFirst();
		}

		private T getMiddleElement() {
			return elements.get(1);
		}
		
		private T getLastElement() {
			return elements.getLast();
		}

		private Node<T> getFirstChildNode(){
			return children.getFirst();
		}
		
		private Node<T> getLastChildNode() {
			return children.getLast();
		}
		
		private Node<T> getChild(int i) {
			return children.get(i);
		}
		
		private T getElement(int i) {
			return elements.get(i);
		}
	}
	
	private Node<T> root;
	private int size;
	private Comparator<T> comparator;
	
	public TwoThreeFourTreeSet(Comparator<T> comparator){
		this.comparator = comparator;
	}
	
	public TwoThreeFourTreeSet(){
		this.comparator = null;
	}
	
	@Override
	public Iterator<T> iterator() {
		return new TwoThreeFourIterator<T>(root);
	}

	private int compareValues(T firstValue, T secondValue){
		if(comparator != null)
			return comparator.compare(firstValue, secondValue);
		else{
			DefaultComparator<T> defaultComperator = new DefaultComparator<T>();
			return defaultComperator.compare(firstValue, secondValue);
		}
	}
	
	@Override
	public boolean add(T element) {
		if(root == null){
			createRootNode(element);
			return true;
		}
		
		if(contains(element))
			return false;
		
		Node<T> node = root;
		while(true) {
			if(node.isFull()) {
				node = splitNode(node);
			} else if(node.isLeaf()){
				node.insertElement(element);
				size++;
				return true;
			} else {
				node = node.getSuccessorNodeForElement(element);
			}
		}
	}

	private void createRootNode(T element){
		Node<T> node = new Node<T>((firstValue, secondValue) -> compareValues(firstValue, secondValue));
		node.insertElement(element);
		root = node;
		size++;
	}
	
	private Node<T> splitNode(Node<T> node) {			
		Node<T> parentNode = node.getParent();
		if(parentNode == null) {
			parentNode = new Node<T>((firstValue, secondValue) -> compareValues(firstValue, secondValue));
			root = parentNode;
		}	
		
		parentNode.insertElement(node.getMiddleElement());		
		parentNode.addChild(createNewLeftNode(node));	
		parentNode.addChild(createNewRightNode(node));
		
		if(!parentNode.isLeaf())
			parentNode.removeChild(node);
		
		return parentNode;
	}

	private Node<T> createNewLeftNode(Node<T> node){
		Node<T> newNode = new Node<T>((firstValue, secondValue) -> compareValues(firstValue, secondValue));	
		newNode.insertElement(node.getFirstElement());
		if(!node.isLeaf()){
			newNode.addChild(node.getChild(0));
			newNode.addChild(node.getChild(1));
		}
		return newNode;
	}
	
	private Node<T> createNewRightNode(Node<T> node){
		Node<T> newNode = new Node<T>((firstValue, secondValue) -> compareValues(firstValue, secondValue));			
		newNode.insertElement(node.getLastElement());
		if(!node.isLeaf()){
			newNode.addChild(node.getChild(2));
			newNode.addChild(node.getChild(3));
		}
		return newNode;
	}
	
	@Override
	public boolean contains(T element) {
		return get(element) != null;
	}

	@Override
	public T get(T element) {
		Node<T> node = root;
		while(node != null) {
			T current = node.getValue(element);
			if(current != null)
				return current;
			node = node.getSuccessorNodeForElement(element);
		}		
		return null;
	}

	public boolean isEmpty(){
		return size == 0;
	}
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public T first() {
		if (isEmpty())
			throw new NoSuchElementException();
		
		Node<T> currentNode = root;
		while(!currentNode.isLeaf()) {
			currentNode = currentNode.getFirstChildNode();
		}
		return currentNode.getFirstElement();
	}

	@Override
	public T last() {
		if (isEmpty())
			throw new NoSuchElementException();
		
		Node<T> currentNode = root;
		while(!currentNode.isLeaf()) {
			currentNode = currentNode.getLastChildNode();
		}
		return currentNode.getLastElement();
	}

	@Override
	public Comparator<T> comparator() {
		return comparator;
	}
	
	@Override
	public int height() {
		int h = 0;
		Node<T> currentNode = root;
		while(currentNode != null && !currentNode.isLeaf()){
			h++;
			currentNode = currentNode.getFirstChildNode();
		}
		return h;
	}
}