package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;
        private Node parent;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) return null;
        if (key.compareTo(p.key) == 0) return p.value;
        else if (key.compareTo(p.key) < 0) return getHelper(key, p.left);
        else return getHelper(key, p.right);
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size++;
            return new Node(key, value);
        }
        if (key.compareTo(p.key) < 0) {
            p.left = putHelper(key, value, p.left);
            p.left.parent = p;
        }
        else if (key.compareTo(p.key) > 0) {
            p.right = putHelper(key, value, p.right);
            p.right.parent = p;
        }
        else return p;
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> res = new HashSet<>();
        keySetHelper(res, root);
        return res;
    }

    /**
     * helper function for keySet(), basically traverse the tree
     * @param set
     */
    private void keySetHelper(Set<K> set, Node n) {
        if (n == null) return;
        set.add(n.key);
        keySetHelper(set, n.left);
        keySetHelper(set, n.right);
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        Node nodeToRemove = getNode(key, root);
        if (nodeToRemove == null) return null;
        size--;
        return removeNode(nodeToRemove);
    }

    private V removeNode(Node nodeToRemove) {
        if (nodeToRemove == null) return null;
        else if (isLeaf(nodeToRemove)) {
            removeLeaf(nodeToRemove);
            return nodeToRemove.value;
        }
        else if (hasOneChild(nodeToRemove)) {
            removeSingleParent(nodeToRemove);
            return nodeToRemove.value;
        }
        else {
            Node closestLeft = getRightMostNode(nodeToRemove.left);
            exchangeNode(nodeToRemove, closestLeft);
            return removeNode(closestLeft);
        }
    }

    private Node getNode(K key, Node n) {
        if (n == null) return null;
        if (key.compareTo(n.key) < 0) return getNode(key, n.left);
        else if (key.compareTo(n.key) > 0) return getNode(key, n.right);
        else return n;
    }

    private boolean isLeaf(Node n) {
        return n.left == null && n.right == null;
    }

    /**
     * Check whether a node has only left child or right child
     * @param n
     * @return
     */
    private boolean hasOneChild(Node n) {
        return (n.left != null && n.right == null) || (n.left == null && n.right != null);
    }

    private void removeLeaf(Node n) {
        if (n.parent == null) root = null;
        else if (n.parent.left == n) n.parent.left = null;
        else n.parent.right = null;
    }

    private void removeSingleParent(Node n) {
        if (n.left == null) {
            if (n.parent == null) root = root.right;
            else if (n.parent.left == n) n.parent.left = n.right;
            else n.parent.right = n.right;
        }
        else {
            if (n.parent == null) root = root.left;
            else if (n.parent.left == n) n.parent.left = n.left;
            else n.parent.right = n.left;
        }
    }

    private Node getRightMostNode(Node n) {
        if (n == null || n.right == null) return n;
        else return getRightMostNode(n.right);
    }

    private void exchangeNode(Node n1, Node n2) {
        K tempKey = n1.key;
        V tempValue = n1.value;
        n1.key = n2.key;
        n1.value = n2.value;
        n2.key = tempKey;
        n2.value = tempValue;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        Node node = getNode(key, root);
        if (node == null || value != node.value) return null;
        size--;
        return removeNode(node);
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
