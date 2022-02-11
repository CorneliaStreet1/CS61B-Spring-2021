package bstmap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K> ,V> implements Map61B<K,V>, Iterable<K>{
    private Node root;
    private int size;
    private class Node {
        K key;
        V value;
        Node left;
        Node right;
        public Node(K key, V value , Node left , Node right) {
            this.key = key;
            this.value = value;
            this.right = right;
            this.left = left;
        }
    }
    /** Removes all of the mappings from this map. */
    public void clear() {
        root = null;
        size = 0;
    }
    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        if (root == null) {
            return false;
        }
        if (key == null) {
            return false;
        }
        if (key.equals(this.root.key)) {
            return true;
        }
        else {
            return this.containsKeyHelper(key, this.root);
        }
    }
    private boolean containsKeyHelper(K key , Node root) {
        if (key == null) {
            return false;
        }
        else if (root == null) {
            return false;
        }
        if (key.equals(root.key)) {
            return true;
        }
        else if (key.compareTo(root.key) > 0) {
            return this.containsKeyHelper(key, root.right);
        }
        else {
            return this.containsKeyHelper(key, root.left);
        }
    }
    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (!containsKey(key)) {
            return null;
        }
        else {
            return this.getHelper(key, this.root);
        }
    }
    //Assume this map contains the mapping for this key
    private V getHelper(K key, Node root) {
        if (root == null) {
            return null;
        }
        if (key.equals(root.key)) {
            return root.value;
        }
       else if (key.compareTo(root.key) > 0) {
            return this.getHelper(key, root.right);
        }
       else {
           return this.getHelper(key, root.left);
        }
    }
    /* Returns the number of key-value mappings in this map. */
    public int size() {
        return this.size;
    }
    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value) throws IllegalArgumentException{
        if (key == null) {
            throw new IllegalArgumentException("Keys can NOT be null value");
        }
        if (!containsKey(key)) {
            this.root = this.putHelper(key, value, this.root);
            }
        size ++;
        }
    //Assume key Not null
    private Node putHelper(K key, V value, Node root) {
        if (root == null) {
            return new Node(key, value, null, null);
        }
        if (key.compareTo(root.key) > 0) {
            root.right = putHelper(key, value, root.right);
        }
        else if (key.compareTo(root.key) < 0)
        {
            root.left = putHelper(key, value, root.left);
        }
        return root;
    }
    /* Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException. */
    public Set<K> keySet() {
        Set<K> returnSet = new HashSet<>();
        this.getKeys(returnSet, this.root);
        return returnSet;
    }
    private void getKeys(Set<K> set, Node root) {
        if (root == null) {
            return;
        }
        set.add(root.key);
        getKeys(set, root.left);
        getKeys(set, root.right);
    }
    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException. */
    public V remove(K key) {
        if (!containsKey(key)) {
            return null;
        }
        else {
            size --;
            return this.remove(key, this.root);
        }
    }
    private V remove(K key, Node root) {
        V returnItem = null;
        if (root == null) {
            return null;
        }
        if (root.key.equals(key)) {
            if (root.left == null && root.right == null) {
                Node ParentNode  = getParent(root);
                returnItem = root.value;
                if (ParentNode.right == root) {
                    ParentNode.right = null;
                }
                else if (ParentNode.left == root){
                    ParentNode.left = null;
                }
                //ParentNode == root
                else {
                    this.root = null;
                }
                return returnItem;
            }
            else if (root.left != null && root.right == null) {
                returnItem = root.value;
                Node ParentNode = getParent(root);
                if (ParentNode.left == root ) {
                    ParentNode.left = root.left;
                }
                else if (ParentNode.right == root){
                    ParentNode.right = root.left;
                }
                else {
                    this.root = root.left;
                }
                return returnItem;
            }
            else if (root.left == null && root.right != null) {
                returnItem = root.value;
                Node ParentNode = getParent(root);
                //Parent不是root本身
                if (ParentNode.left == root ) {
                    ParentNode.left = root.right;
                }
                else if (ParentNode.right == root){
                    ParentNode.right = root.right;
                }
                //Parent就是root本身
                else {
                    this.root = root.right;
                }

                return returnItem;
            }
            else {
                Node newRoot = getNewRoot(root);
                returnItem = root.value;
                root.key = newRoot.key;
                root.value = newRoot.value;
                if (newRoot.left != null && newRoot.right == null) {
                    newRoot.key = newRoot.left.key;
                    newRoot.value = newRoot.left.value;
                    newRoot.left = null;
                }
                else {
                    newRoot.key = newRoot.right.key;
                    newRoot.value = newRoot.right.value;
                    newRoot.right = null;
                }
                return returnItem;
            }
        }
        else if (key.compareTo(root.key) > 0 ) {
            return remove(key, root.right);
        }
        else {
            return remove(key, root.left);
        }
    }
    private Node getNewRoot(Node OldRoot) {
        Node returnItem = OldRoot.left;
        while (returnItem.right != null) {
            returnItem = returnItem.right;
        }
        return returnItem;
    }
    private Node getParent(Node child) {
        Node p = this.root;
        while (p.left != child && p.right != child && p != child) {
            if (child.key.compareTo(p.key) > 0) {
                p = p.right;
            }
            else {
                p = p.left;
            }
        }
        return p;
    }
    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 7. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    public V remove(K key, V value) {
        if (containsKey(key)) {
            if ((get(key)).equals(value)) {
                return remove(key);
            }
        }
        return null;
    }

    /**
     * @return return an iterator over the keys.
     */
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
