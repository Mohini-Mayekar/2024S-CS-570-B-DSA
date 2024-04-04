
/**
 * @author Mohini Mayekar
 * */

import java.util.Random;
import java.util.Stack;

public class Treap<E extends Comparable<E>> {
	/**
	 * Inner Class
	 * 
	 * @param <E>
	 */
	private static class Node<E extends Comparable<E>> {
		public E data;
		public int priority;
		public Node<E> left;
		public Node<E> right;

		/**
		 * Inner class constructor
		 * 
		 * @param data
		 * @param priority
		 */
		public Node(E data, int priority) {
			if (data == null) {
				throw new IllegalArgumentException("Data cannot be null");
			}
			this.data = data;
			this.priority = priority;
			this.left = null;
			this.right = null;
		}

		/**
		 * Performs a right rotation on this node, where the current node becomes the
		 * right child of its left child.
		 * 
		 * @return new root node after right rotation
		 */
		public Node<E> rotateRight() {
			Node<E> newRoot = this.left;
			this.left = newRoot.right;
			newRoot.right = this;
			return newRoot;
		}

		/**
		 * Performs a left rotation on this node, where the current node becomes the
		 * left child of its right child.
		 * 
		 * @return new root node after left rotation
		 */
		public Node<E> rotateLeft() {
			Node<E> newRoot = this.right;
			this.right = newRoot.left;
			newRoot.left = this;
			return newRoot;
		}

	}

	private Random priorityGenerator;
	private Node<E> root;

	/**
	 * Outer class constructor
	 */
	public Treap() {
		this.priorityGenerator = new Random();
		this.root = null;
	}

	/**
	 * Outer class parameterized constructor
	 * 
	 * @param seed
	 */
	public Treap(long seed) {
		this.priorityGenerator = new Random(seed);
		this.root = null;
	}

	/**
	 * Inserts a new node with the given key into the treap with a randomly
	 * generated priority.
	 * 
	 * @param key to be inserted into the treap
	 * @return true -> if the insertion was successful, false -> if the key already
	 *         exists in the treap
	 */
//	public boolean add(E key) {
//		return add(key, priorityGenerator.nextInt());
//	}
	public boolean add(E key) {
		int priority = priorityGenerator.nextInt();
		// Check for duplicate priority
		if (findPriority(root, priority)) {
			System.out.println("Duplicate priority insertion detected!");
			return false;
		}			
		return add(key, priority);
	}

	/**
	 * Searches for a node with the given priority starting from the
	 * specified node in the treap.
	 *
	 * @param node     from which to start the search
	 * @param priority to search for in the treap
	 * @return true -> if a node with the given priority is found, false -> if the
	 *         priority was not found in the treap
	 */
	private boolean findPriority(Node<E> node, int priority) {
		if (node == null)
			return false;
		if (node.priority == priority)
			return true;
		boolean foundInLeftSubtree = findPriority(node.left, priority);
		boolean foundInRightSubtree = findPriority(node.right, priority);
		return foundInLeftSubtree || foundInRightSubtree;
	}

	/**
	 * Inserts a new node with the given key and priority into the treap.
	 * 
	 * @param key      to be inserted into the treap
	 * @param priority associated with the key
	 * @return true -> if the insertion was successful, false -> if the key already
	 *         exists in the treap
	 */
	public boolean add(E key, int priority) {
		if (root == null) {
			root = new Node<>(key, priority);
			return true;
		}
		if (findPriority(root, priority)) {
			System.out.println("Duplicate priority insertion detected!");
			return false;
		}

		Stack<Node<E>> path = new Stack<>();
		Node<E> current = root;

		while (current != null) {
			path.push(current);
			int compareResult = key.compareTo(current.data);
			if (compareResult == 0)
				return false;
			else if (compareResult < 0)
				current = current.left;
			else
				current = current.right;
		}

		Node<E> newNode = new Node<>(key, priority);
		current = path.peek();
		int compareResult = key.compareTo(current.data);
		if (compareResult < 0)
			current.left = newNode;
		else
			current.right = newNode;

		reheap(path, newNode);

		return true;
	}

	/**
	 * Reheaps the treap by performing rotations to maintain the heap property after
	 * a node insertion.
	 * 
	 * @param path    a stack containing the path from the newly inserted node to
	 *                the root of the tree
	 * @param newNode the newly inserted node into the treap
	 */
	private void reheap(Stack<Node<E>> path, Node<E> newNode) {
		while (!path.isEmpty()) {
			Node<E> parent = path.pop();
			if (newNode.priority > parent.priority) {
				if (newNode.data.compareTo(parent.data) < 0) {
					parent = parent.rotateRight();
				} else {
					parent = parent.rotateLeft();
				}
				if (!path.isEmpty()) {
					Node<E> grandParent = path.peek();
					if (newNode.data.compareTo(grandParent.data) < 0)
						grandParent.left = parent;
					else
						grandParent.right = parent;
				} else {
					root = parent;
				}
			} else {
				break;
			}
		}
	}

	/**
	 * Deletes a node with the given key from the treap.
	 * 
	 * @param key of the node to be deleted from the treap
	 * @return true -> if the deletion was successful, false -> if the key was not
	 *         found in the treap
	 */
	public boolean delete(E key) {
		if (root == null || !find(key))
			return false; // Key not found

		Stack<Node<E>> path = new Stack<>();
		Node<E> current = root;
		Node<E> parent = null;
		boolean isLeftChild = false;

		while (current != null && !current.data.equals(key)) {
			path.push(current);
			parent = current;
			if (key.compareTo(current.data) < 0) {
				current = current.left;
				isLeftChild = true;
			} else {
				current = current.right;
				isLeftChild = false;
			}
		}

		if (current.left == null && current.right == null) {
			if (current == root) {
				root = null;
			} else if (isLeftChild) {
				parent.left = null;
			} else {
				parent.right = null;
			}
		} else if (current.left == null) {
			if (current == root) {
				root = current.right;
			} else if (isLeftChild) {
				parent.left = current.right;
			} else {
				parent.right = current.right;
			}
		} else if (current.right == null) {
			if (current == root) {
				root = current.left;
			} else if (isLeftChild) {
				parent.left = current.left;
			} else {
				parent.right = current.left;
			}
		} else {
			Node<E> successor = findSuccessor(current);
			if (current == root) {
				root = successor;
			} else if (isLeftChild) {
				parent.left = successor;
			} else {
				parent.right = successor;
			}
			successor.left = current.left;
		}

		return true;
	}

	/**
	 * Finds the successor node of the given node in the treap.
	 * 
	 * @param node for which to find the successor
	 * @return successor node of the given node in the treap
	 */
	private Node<E> findSuccessor(Node<E> node) {
		Node<E> parent = node;
		Node<E> current = node.right;
		Node<E> successor = node;
		while (current != null) {
			parent = successor;
			successor = current;
			current = current.left;
		}
		if (successor != node.right) {
			parent.left = successor.right;
			successor.right = node.right;
		}
		return successor;
	}

	/**
	 * Finds a node with the given key in the treap
	 * 
	 * @param key of the node to be found in the treap
	 * @return true -> if it finds it, false-> if the key was not found in the treap
	 */
	public boolean find(E key) {
		return find(root, key);
	}

	/**
	 * Finds a node with the given key in the treap rooted at root
	 * 
	 * @param node searches for the key from this node.
	 * @param key  of the node to be found in the treap
	 * @return true -> if it finds it, false-> if the key was not found in the treap
	 */
	private boolean find(Node<E> node, E key) {
		if (node == null)
			return false;
		if (key.equals(node.data))
			return true;
		if (key.compareTo(node.data) < 0)
			return find(node.left, key);
		else
			return find(node.right, key);
	}

	@Override
	public String toString() {
		return toString(root, 0);
	}

	/**
	 * Generates a string representation of the subtree rooted at the specified node
	 * with the given indentation.
	 * 
	 * @param node
	 * @param indent
	 * @return string representation of the subtree rooted at the specified nodes
	 *         with indentation
	 */
	private String toString(Node<E> node, int indent) {
		if (node == null) {
			StringBuilder nullString = new StringBuilder();
			for (int i = 0; i < indent; i++) {
				nullString.append("  ");
			}
			nullString.append("null\n");
			return nullString.toString();
		}
		String leftStr = toString(node.left, indent + 1);
		String rightStr = toString(node.right, indent + 1);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < indent; i++) {
			sb.append("  ");
		}
		sb.append("(key =").append(node.data).append(" ,  priority =").append(node.priority).append(")\n");
		sb.append(leftStr);
		sb.append(rightStr);
		return sb.toString();
	}

	public static void main(String[] args) {
		Treap<Integer> treap = new Treap<Integer>();
		System.out.println(" Adding key with priority - add(4, 19): " + treap.add(4, 19));
		System.out.println(" Adding key with priority - add(2, 31): " + treap.add(2, 31));
		System.out.println(" Adding key with priority - add(6, 70): " + treap.add(6, 70));
		System.out.println(" Adding key with priority - add(1, 84): " + treap.add(1, 84));
		System.out.println(" Adding key with priority - add(3, 12): " + treap.add(3, 12));
		System.out.println(" Adding key with priority - add(5, 83): " + treap.add(5, 83));
		System.out.println(" Adding key with priority - add(7, 26): " + treap.add(7, 26));

		System.out.println(treap.toString());

		System.out.println("Find 6: " + treap.find(6));
		System.out.println("Delete 8: " + treap.delete(8));

		System.out.println(" Adding key with priority - add(8, 10): " + treap.add(8, 10));

		System.out.println(treap.toString());

		System.out.println("Delete 8: " + treap.delete(8));
		System.out.println(treap.toString());
		
		System.out.println(" Adding key with duplicate priority add(9, 31): " + treap.add(9, 31));

		System.out.println(treap.toString());
		
		System.out.println(" Adding key with priority - add(10): " + treap.add(10));
		
		System.out.println(treap.toString());
	}

}
