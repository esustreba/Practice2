package ua.nure.strebkov.Practice2;

import java.util.Arrays;
import java.util.Iterator;

public class MyListImpl implements MyList, ListIterable {

	private Object[] arr = {};

	/**
	 * appends the specified element to the end of this list.
	 */
	@Override
	public void add(Object e) {
		Object[] arr2 = new Object[arr.length + 1];
		System.arraycopy(arr, 0, arr2, 0, arr.length);
		arr2[arr2.length - 1] = e;
		arr = arr2;
	}

	/**
	 * removes all of the elements from this list.
	 */
	@Override
	public void clear() {
		arr = new Object[] {};
	}

	/**
	 * removes the first occurrence of the specified element from this list.
	 */
	@Override
	public boolean remove(Object o) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].equals(o)) {
				Object[] arr2 = new Object[arr.length - 1];
				System.arraycopy(arr, 0, arr2, 0, i);
				System.arraycopy(arr, i + 1, arr2, i, arr2.length - i);
				arr = arr2;
				return true;
			}
		}
		return false;
	}

	/**
	 * returns an array containing all of the elements in this list in proper
	 * sequence.
	 */
	@Override
	public Object[] toArray() {
		Object[] array = new Object[arr.length];
		for (int i = 0; i < arr.length; i++) {
			array[i] = i;
		}
		return array; // Copy array!!!
	}

	/**
	 * returns the number of elements in this list.
	 */
	@Override
	public int size() {
		return toArray().length;
	}

	/**
	 * returns true if this list contains the specified element.
	 */
	@Override
	public boolean contains(Object o) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].equals(o)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * returns true if this list contains all of the elements of the specified list.
	 */

	@Override
	public boolean containsAll(MyList c) {
		boolean flag = false;
		for (int j = 0; j < c.toArray().length; j++) {
			flag = false;
			for (int i = 0; i < toArray().length; i++) {
				if (toArray()[i] == c.toArray()[j]) {
					flag = true;

				}
			}
			if (!flag) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Override toString() with java.util.Arrays.
	 */
	@Override
	public String toString() {
		return Arrays.toString(arr);
	}

	public Iterator<Object> iterator() {
		return new IteratorImpl();
	}

	private class IteratorImpl implements Iterator<Object> {
		private boolean flag = true;

		public boolean isFlag() {
			return flag;
		}

		public void setFlag(boolean flag) {
			this.flag = flag;
		}

		public int getPointer() {
			return pointer;
		}

		public void setPointer(int pointer) {
			this.pointer = pointer;
		}

		public void setNextOrPrevious(boolean nextOrPrevious) {
			this.nextOrPrevious = nextOrPrevious;
		}

		private int pointer = -1;
		private boolean nextOrPrevious = false;

		public boolean isNextOrPrevious() {
			return nextOrPrevious;
		}

		/**
		 * returns true if the iteration has more elements
		 */
		public boolean hasNext() {
			if (pointer < toArray().length - 1) {
				return true;
			}
			return false;
		}

		/**
		 * returns the next element in the iteration
		 */
		public Object next() {
			flag = false;
			nextOrPrevious = false;
			return toArray()[++pointer];
		}

		/**
		 * removes from the underlying collection the last element returned by this
		 * iterator
		 */
		public void remove() {
			if (isFlag()) {
				throw new IllegalStateException();
			}
			int i = 0;
			if (!isNextOrPrevious()) {
				i = pointer--;
			} else {
				i = ++pointer;
			}
			Object[] arr2 = new Object[arr.length - 1];
			System.arraycopy(arr, 0, arr2, 0, i);
			System.arraycopy(arr, i + 1, arr2, i, arr2.length - i);
			arr = arr2;
			flag = true;
			if (isNextOrPrevious()) {
				--pointer;
			}

		}
	}

	private class ListIteratorImpl extends IteratorImpl implements ListIterator {

		@Override
		public boolean hasPrevious() {
			if (getPointer() >= 0) {
				return true;
			}
			return false;
		}

		@Override
		public Object previous() {
			setFlag(false);
			setNextOrPrevious(true);
			setPointer(getPointer() - 1);
			return toArray()[getPointer() + 1];
		}

		@Override
		public void set(Object e) {
			if (isFlag()) {
				throw new IllegalStateException();
			}
			if (isNextOrPrevious()) {
				toArray()[getPointer() + 1] = e;
			}
			if (!isNextOrPrevious()) {
				toArray()[getPointer()] = e;
			}
			setFlag(true);
		}

		@Override
		public void remove() {
			if (isFlag()) {
				throw new IllegalStateException();
			}
			super.remove();
		}
	}

	@Override
	public ListIterator listIterator() {

		return new ListIteratorImpl();
	}
}
