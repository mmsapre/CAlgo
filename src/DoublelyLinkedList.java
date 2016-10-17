import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class DoublelyLinkedList<T> {

	public class ListNode<T> {
		protected T value;
		protected ListNode<T> prev;
		protected ListNode<T> next;

		public ListNode(T value) {
			this.value = value;
		}

		public ListNode<T> getPrev() {
			return prev;
		}

		public ListNode<T> getNext() {
			return next;
		}

		public T getValue() {
			return value;
		}

		public void setValue(T value) {
			this.value = value;
		}
	}

	protected int size;
	protected ListNode<T> tail;
	protected ListNode<T> head;

	public ListNode<T> add(T value) {
		ListNode<T> node = new ListNode<T>(value);
		if (size++ == 0) {
			tail = node;
		} else {
			node.prev = head;
			head.next = node;
		}

		head = node;

		return node;
	}

	public ListNode<T> enqueue(T value) {
		ListNode<T> node = new ListNode<T>(value);
		if (size++ == 0) {
			head = node;
		} else {
			node.next = tail;
			tail.prev = node;
		}

		tail = node;

		return node;
	}

	public void add(ListNode<T> node) {
		node.prev = head;
		node.next = null;

		if (size++ == 0) {
			tail = node;
		} else {
			head.next = node;
		}

		head = node;
	}

	public void remove(ListNode<T> node) {
		if (node == tail) {
			tail = node.next;
		} else {
			node.prev.next = node.next;
		}

		if (node == head) {
			head = node.prev;
		} else {
			node.next.prev = node.prev;
		}
		size--;
	}

	public int size() {
		return size;
	}

	public T last() {
		return head == null ? null : head.getValue();
	}

	public Iterator<T> iterator() {
		return new DoubleLinkedListIterator(this);
	}

	public ListNode<T> head() {
		return head;
	}

	public ListNode<T> tail() {
		return tail;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	protected class DoubleLinkedListIterator implements Iterator<T> {

		protected DoublelyLinkedList<T> list;
		protected ListNode<T> itr;
		protected int length;

		public DoubleLinkedListIterator(DoublelyLinkedList<T> list) {
			this.length = list.size;
			this.list = list;
			this.itr = list.tail;
		}

		@Override
		public boolean hasNext() {
			return itr != null;
		}

		@Override
		public T next() {
			if (length != list.size) {
				throw new ConcurrentModificationException();
			}
			T next = itr.value;
			itr = itr.next;
			return next;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

}
