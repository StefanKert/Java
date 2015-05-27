package at.skert.swe.ue5.implementation;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

import at.skert.swe.ue5.interfaces.SortedTreeSet;

public class BSTSet<T> implements SortedTreeSet<T> {
	private static class BSTIterator<T> implements Iterator<T> {

		private Stack<Node<T>> unvisitedParents = new Stack<>();

		public BSTIterator(Node<T> root) {
			Node<T> next = root;
			while (next != null) {
				unvisitedParents.push(next);
				next = next.left;
			}
		}

		@Override
		public boolean hasNext() {
			return !unvisitedParents.empty();
		}

		@Override
		public T next() {
			if (!hasNext())
				throw new NoSuchElementException();
			Node<T> current = unvisitedParents.pop();
			Node<T> next = current.right;
			while (next != null) {
				unvisitedParents.push(next);
				next = next.left;
			}
			return current.value;
		}
	}

	private static class Node<T> {
		private T value;
		private Node<T> left, right;

		Node(T value, Node<T> left, Node<T> right) {
			this.value = value;
			this.left = left;
			this.right = right;
		}
	}

	private Node<T> root;
	private int size;
	private Comparator<T> comparator;

	public BSTSet(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	public BSTSet() {
		this.comparator = null;
	}

	@Override
	public Iterator<T> iterator() {
		return new BSTIterator<T>(root);
	}

	private int compareValues(T firstValue, T secondValue) {
		if (comparator != null) {
			return comparator.compare(firstValue, secondValue);
		} else {
			DefaultComparator<T> defaultComperator = new DefaultComparator<T>();
			return defaultComperator.compare(firstValue, secondValue);
		}
	}

	private Node<T> addRecursive(Node<T> parent, T element) {
		if (parent == null) {
			size++;
			return new Node<T>(element, null, null);
		}
		if (compareValues(element, parent.value) < 0) {
			parent.left = addRecursive(parent.left, element);
		} else {
			parent.right = addRecursive(parent.right, element);
		}
		return parent;
	}

	@Override
	public boolean add(T element) {
		if (contains(element)) {
			return false;
		}
		root = addRecursive(root, element);
		return true;
	}

	@Override
	public boolean contains(T element) {
		return get(element) != null;
	}

	@Override
	public T get(T element) {
		Node<T> n = root;
		while (n != null) {
			int cmp = compareValues(n.value, element);
			if (cmp == 0)
				return n.value;
			else if (cmp > 0)
				n = n.left;
			else
				n = n.right;
		}
		return null;
	}

	@Override
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public T first() {
		Node<T> n = root;
		if (isEmpty())
			throw new NoSuchElementException();
		while (true) {
			if (n.left == null)
				return n.value;

			n = n.left;
		}
	}

	@Override
	public T last() {
		Node<T> n = root;
		if (isEmpty())
			throw new NoSuchElementException();
		while (true) {
			if (n.right == null)
				return n.value;

			n = n.right;
		}
	}

	@Override
	public Comparator<T> comparator() {
		return comparator;
	}

	@Override
	public int height() {
		return height(root);
	}

	private int height(Node<T> node) {
		if (node == null)
			return 0;
		else {
			int left = height(node.left);
			int right = height(node.right);
			return 1 + Math.max(left, right);
		}
	}
}
