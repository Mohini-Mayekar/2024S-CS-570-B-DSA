import java.util.ArrayList;

/**
 * @author Mohini Mayekar
 */

public class IDLList<E> {
	private class Node<E> {
		E data;
		Node<E> next;
		Node<E> prev;

		Node(E elem) throws IllegalArgumentException {
			try {
				if (elem == null) {
					throw new IllegalArgumentException("elem cannot be null");
				}
				this.data = elem;
				this.prev = null;
				this.next = null;
			} catch (IllegalArgumentException e) {
				System.out.println(e);
			}
		}

		Node(E elem, Node<E> prev, Node<E> next) throws IllegalArgumentException {
			try {
				if (elem == null) {
					throw new IllegalArgumentException("elem cannot be null");
				}
				this.data = elem;
				this.prev = prev;
				this.next = next;
			} catch (IllegalArgumentException e) {
				System.out.println(e);
			}
		}
	}

	private Node<E> head;
	private Node<E> tail;
	private int size;
	private ArrayList<Node<E>> indices;

	/**
	 * creates an empty double-linked list
	 */
	public IDLList() {
		head = null;
		tail = null;
		size = 0;
		indices = new ArrayList<>();
	}

	/**
	 * adds elem at position index (counting from wherever head is). It uses the
	 * index for fast access.
	 */
	public boolean add(int index, E elem) {
		try {
			if (index < 0 || index >= size) {
				System.out.println("Error: Could not add element at given index. Index out of bounds: " + index);
				throw new IndexOutOfBoundsException(index);
				// return false;
			}
			if (elem == null) {
				throw new IllegalArgumentException("elem cannot be null");
			}

			if (index == 0)
				return add(elem);

			Node<E> newNode = new Node<>(elem);
			Node<E> currNode = indices.get(index - 1);
			Node<E> nextNode = currNode.next;

			currNode.next = newNode;
			newNode.prev = currNode;
			newNode.next = nextNode;

			if (nextNode != null) {
				nextNode.prev = newNode;
			} else {
				tail = newNode;
			}

			indices.add(index, newNode);
			size++;
			return true;
		} catch (IndexOutOfBoundsException | IllegalArgumentException e) {
			System.out.println(e);
		}
		return false;
	}

	/**
	 * that adds elem at the head (i.e. it becomes the first element of the list)
	 */
	public boolean add(E elem) {
		try {
			if (elem == null) {
				throw new IllegalArgumentException("elem cannot be null");
			}
			Node<E> newNode = new Node<>(elem);
			if (head == null) {
				head = newNode;
				tail = newNode;
			} else {
				newNode.next = head;
				head.prev = newNode;
				head = newNode;
			}

			indices.add(0, newNode);
			size++;
			return true;
		} catch (IllegalArgumentException e) {
			System.out.println(e);
		}
		return false;
	}

	/**
	 * adds elem as the new last element of the list (i.e. at the tail)
	 */
	public boolean append(E elem) {
		try {
			if (elem == null) {
				throw new IllegalArgumentException("elem cannot be null");
			}
			Node<E> newNode = new Node<>(elem);
			if (tail == null) {
				head = newNode;
				tail = newNode;
			} else {
				newNode.prev = tail;
				tail.next = newNode;
				tail = newNode;
			}

			indices.add(newNode);
			size++;
			return true;
		} catch (IllegalArgumentException e) {
			System.out.println(e);
		}
		return false;
	}

	/**
	 * returns the object at position index from the head. It uses the index for
	 * fast access. Indexing starts from 0, thus get(0) returns the head element of
	 * the list.
	 */
	public E get(int index) {
		try {
			if (index < 0 || index >= size) {
				System.out.println("Error: Could not get element at given index. Index out of bounds: " + index);
				throw new IndexOutOfBoundsException(index);
				// return null;
			}
			return indices.get(index).data;
		} catch (IndexOutOfBoundsException e) {
			System.out.println(e);
		}
		return null;
	}

	/**
	 * returns the object at the head
	 */
	public E getHead() {
		return head != null ? head.data : null;
	}

	/**
	 * returns the object at the tail
	 */
	public E getLast() {
		return tail != null ? tail.data : null;
	}

	/**
	 * returns the list size
	 */
	public int size() {
		return size;
	}

	/**
	 * removes and returns the element at the head.
	 */
	public E remove() {
		if (head == null) {
			System.out.println("Error: Could not remove and return the element at the head. List is empty.");
			return null;
		}

		Node<E> removeNode = head;
		head = head.next;
		if (head != null) {
			head.prev = null;
		} else {
			tail = null;
		}

		indices.remove(removeNode);
		size--;
		return removeNode.data;
	}

	/**
	 * removes and returns the element at the tail
	 */
	public E removeLast() {
		if (tail == null) {
			System.out.println("Error: Could not remove and return the element at the tail. List is empty.");
			return null;
		}

		Node<E> removeNode = tail;
		tail = tail.prev;

		if (tail != null) {
			tail.next = null;
		} else {
			head = null;
		}
		indices.remove(removeNode);
		size--;
		return removeNode.data;
	}

	/**
	 * removes and returns the element at the index. Use the index for fast access.
	 */
	public E removeAt(int index) {
		Node<E> removeNode = null;
		try {
			if (index < 0 || index >= size) {
				System.out.println(
						"Error: Could not not remove and return the element at the given index. Index out of bounds: "
								+ index);
				throw new IndexOutOfBoundsException(index);
				// return null;
			}

			removeNode = indices.get(index);

			if (removeNode.prev != null) {
				removeNode.prev.next = removeNode.next;
			} else {
				head = removeNode.next;
			}

			if (removeNode.next != null) {
				removeNode.next.prev = removeNode.prev;
			} else {
				tail = removeNode.prev;
			}
			indices.remove(removeNode);
			size--;
			return removeNode.data;
		} catch (IndexOutOfBoundsException e) {
			System.out.println(e);
		}
		return null;
	}

	/**
	 * removes the first occurrence of elem in the list and returns true. Return
	 * false if elem was not in the list
	 */
	public boolean remove(E elem) {
		try {
			if (elem == null) {
				throw new IllegalArgumentException("elem cannot be null");
			}
			Node<E> currNode = head;
			while (currNode != null) {
				if (currNode.data.equals(elem)) {
					if (currNode.prev != null) {
						currNode.prev.next = currNode.next;
					} else {
						head = currNode.next;
					}

					if (currNode.next != null) {
						currNode.next.prev = currNode.prev;
					} else {
						tail = currNode.prev;
					}
					indices.remove(currNode);
					size--;
					return true;
				}
				currNode = currNode.next;
			}
		} catch (IllegalArgumentException e) {
			System.out.println(e);
		}
		return false;
	}

	/**
	 * presents a string representation of the list.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		Node<E> currNode = head;

		while (currNode != null) {
			sb.append(currNode.data);
			if (currNode.next != null) {
				sb.append(", ");
			}
			currNode = currNode.next;
		}

		sb.append("]");
		return sb.toString();
	}
}
