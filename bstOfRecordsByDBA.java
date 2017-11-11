package proj1;

import java.util.LinkedList;
import java.util.List;

/**
 * This class creates binary search trees to store DbaList of inspection records
 * based on the order of their names. Records of the same name are stored
 * in the same DbaList. There are methods needed for managing the data field.
 * @author Zhouyang Li
 * @author Joanna Klukowska
 *
 */

public class bstOfRecordsByDBA {

	private Node root;
	
	/**
	 * This class contains nodes of the binary search tree of DbaList.
	 * @author Zhouyang Li
	 *
	 */
	public class Node implements Comparable<Node>{
		public DbaList data;
		public Node left;
		public Node right;
		
		/**
		 * Set the data of the DbaList as the given data.
		 * @param data the data the node supposed to store.
		 * @author Zhouyang Li
		 */
		
		public Node(DbaList data) {
			this.data = data;
		}
		
		/**
		 * Set the node with the given data, left node and right data.
		 * @param data the data the node supposed to store.
		 * @param left the left node of the node.
		 * @param right the right node of the node.
		 * @author Zhouyang Li
		 */
		public Node(DbaList data, Node left, Node right) {
			this.data = data;
			this.left = left;
			this.right = right;
		}

		/**
		 * Compare this node with another node based on the dba.
		 * @author Zhouyang Li
		 */
		public int compareTo(Node other) {
			return this.data.getDba().compareTo(other.data.getDba());
		}
		
	}
	
	/**
	 * Construct an empty bstOfRecordsByDBA, the binary search tree of DbaList.
	 * @author Zhouyang Li
	 */
	public bstOfRecordsByDBA() {
		root = null;
	}
	
	/**
	 * A wrapper method to get to the get method that doing the actual job.
	 * @param dba the dba of the DbaList that the method supposed to get.
	 * @return the DbaList of the given dba returned from the get method 
	 * doing the actual job.
	 * @author Zhouyang Li
	 */
	public DbaList get(String dba) {
		return get(dba, root);
	}
	
	/**
	 * Traverses the bstOfRecordsByDBA to get the DbaList of the given dba.
	 * @param dba the dba of the DbaList that is supposed to be gotten.
	 * @param n the uppermost node of the tree/subtree.
	 * @return the DbaList with the given dba, or null if this DbaList doesn't exist.
	 * @author Zhouyang Li
	 */
	private DbaList get(String dba, Node n) {
	
			Node current = n;
			if (current == null){
				return null;
			}
			else if (current.data.getDba().equalsIgnoreCase(dba)) {
				return current.data;
			}
			else if (current.data.getDba().compareToIgnoreCase(dba) < 0) {
				return get(dba, current.right);
			}
			else if (current.data.getDba().compareToIgnoreCase(dba) > 0) {
				return get(dba, current.left);
			}
			else 
				return null;
		}
	
	/**
	 * A wrapper method to get to the add method that does the actual job.
	 * @param r the record that is supposed to be added to the proper DbaList
	 * inside the bstOfRecordsByDBA.
	 * @author Zhouyang Li
	 */
	public void add(Record r) {
		this.add(r, root, null);
	}
	
	/**
	 * Traverses the bstOfRecordsByDBA to add the record to the proper DbaList 
	 * inside the bstOfRecordsByDBA.
	 * @param r the record that is supposed to be added to the bstOfRecordsByDBA.
	 * @param n the uppermost node of the tree/subtree.
	 * @param parent a node whose left node or right node is the uppermost node of the 
	 * tree/subtree.
	 * @author Zhouyang Li
	 */
	private void add(Record r, Node n, Node parent) {
		Node current = n;
		String key = r.getDba();
		if (current == null && parent == null) {
			DbaList newList = new DbaList(r);
			Node newNode = new Node(newList);
			root = newNode;
		}
		else if (current == null) {
			
			DbaList newList = new DbaList(r);
			Node newNode = new Node(newList);
			if (newNode.compareTo(parent) < 0) {
				parent.left = newNode;
			}
			else {
				parent.right = newNode;
			}
		}
	
		else if (current.data.getDba().equalsIgnoreCase(key)) {
			current.data.add(r);
		}
		else if (current.data.getDba().compareToIgnoreCase(key) < 0) {
			this.add(r, current.right, current);
		}
		else if (current.data.getDba().compareToIgnoreCase(key) > 0) {
			this.add(r, current.left, current);
		}
	}
	
	/**
	 * A wrapper method that get to the remove method that does the actual job.
	 * @param dba the dba of the DbaList inside the node that is supposed to be 
	 * removed from the tree.
	 * @return the DbaList returned by the actual remove method that is removed
	 * form the tree, or null id the target DbaList doesn't exist.
	 * @author Zhouyang Li
	 */
	public DbaList remove(String dba) {
		return remove(dba, root, null);
	}
	
	/**
	 * Remove the node containing the DbaList of the given dba from the tree and 
	 * return the removed DbaList.
	 * @param dba the dba of the DbaList contained by the node that is to be removed.
	 * @param n the uppermost node of the tree/subtree.
	 * @param parent a node whose left node or right node is the uppermost node of the 
	 * tree/subtree.
	 * @return the DbaList that is removed from the tree, or null if the DbaList of the
	 * given dba doesn't exist.
	 * @author Zhouyang Li
	 */
	public DbaList remove(String dba, Node n, Node parent) {
		Node current = n;
		if (current == null)
			return null;
		else if (current.data.getDba().equalsIgnoreCase(dba)) {
			DbaList target = current.data;
			
			if (current.left == null && current.right == null){
				if (parent == null) {
					root = null;
				}
				else if (parent.left == current){
					parent.left = null;
				}
				else {
					parent.right = null;
				}
			}
			else if(current.left == null){
				if (parent == null) {
					root = current.right;
				}
				else if (parent.left == current) {
					parent.left = current.right;
				}
				else {
					parent.right = current.right;
				}		
			}
			else if (current.right == null) {
				if (parent == null) {
					root = current.left;
				}
				else if (parent.left == current) {
					parent.left = current.left;
				}
				else {
					parent.right = current.left;
				}		
			}
			else{
				DbaList pred = this.getPredecessor(current);
				current.data = pred;
				current.left.data = this.remove(pred.getDba(), current.left, current);
			}
			return target;
		}
		else if (current.data.getDba().compareToIgnoreCase(dba) < 0) {
			return remove(dba, current.right, current);
		}
		else if (current.data.getDba().compareToIgnoreCase(dba) > 0) {
			return remove(dba, current.left, current);
		}
		return null;
	}
	/**
	 * Get the right most node of the left branch of node n.
	 * @param n the node for whom the predecessor to be found.
	 * @return the DbaList of the right most node of the left branch of node n;
	 * or null if the left branch doesn't exist.
	 */
	public DbaList getPredecessor(Node n) {
		Node current = null;
		if (n.left != null) {
			current  = n.left;
			while (current.right != null) {
				current = current.right;
			}
			return current.data;
		}
		else
			return null;
	}
}

