package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (obj instanceof MyHashMap.Node) {
                Node o = (Node) obj;
                return (o.value.equals(this.value) && o.key.equals(this.key));
            }
            return false;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    private int size;
    private double loadFactor;

    /** Constructors */
    public MyHashMap() {
        this(16, 0.75);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, 0.75);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    @SuppressWarnings("unchecked")
    public MyHashMap(int initialSize, double maxLoad) {
        buckets = new Collection[initialSize];
        for (int i = 0 ; i < initialSize ; i ++) {
            buckets[i] = this.createBucket();
        }
        size = 0;
        loadFactor = maxLoad;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new ArrayList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    /** Removes all of the mappings from this map. */
    public void clear() {
        this.buckets = createTable(16);
        for (int i = 0 ; i < 16 ; i ++) {
            this.buckets[i] = createBucket();
        }
        size = 0;
    }
    /** Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        int index = getIndex(key, this.buckets.length);
        for (Node n: buckets[index]) {
            if (n.key.equals(key)) {
                return true;
            }
        }
        return false;
    }
    private int getIndex(K key, int TableSize) {
        int KeyHashCode = key.hashCode();
        return Math.floorMod(KeyHashCode, TableSize);
    }
    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        V returnValue = null;
        if (!containsKey(key)) {
            return null;
        }
        else {
            int index = getIndex(key, this.buckets.length);
            for (Node n: buckets[index]) {
                if (n.key.equals(key)) {
                    returnValue = n.value;
                }
            }
        }
        return returnValue;
    }
    /** Returns the number of key-value mappings in this map. */
    public int size() {
        return size;
    }
    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    public void put(K key, V value) {
        double Ratio = (double) this.size() / this.buckets.length;
        if (Ratio > this.loadFactor) {
            resize(2 * this.buckets.length);
        }
        if (!containsKey(key)) {
            Node node = new Node(key, value);
            int index = getIndex(key, buckets.length);
            buckets[index].add(node);
            size ++;
        }
        else {
            int index = getIndex(key, buckets.length);
            for (Node n: buckets[index]) {
                if (n.key.equals(key)) {
                    n.value = value;
                }
            }
        }
    }
    private void resize(int capacity) {
        Collection<Node>[] newBuckets = this.createTable(capacity);
        for (int i = 0 ; i < newBuckets.length; i ++) {
            newBuckets[i] = createBucket();
        }
        ReHashing(newBuckets);
        this.buckets = newBuckets;
    }
    private void ReHashing(Collection<Node>[] newBuckets) {
        for (Collection<Node> bucket: this.buckets) {
            for (Node node: bucket) {
                int index = getIndex(node.key, newBuckets.length);
                newBuckets[index].add(new Node(node.key, node.value));
            }
        }
    }
    /** Returns a Set view of the keys contained in this map. */
    public Set<K> keySet() {
        Set<K> returnSet = new HashSet<>(this.size());
        for (Collection<Node> bucket: buckets) {
            for (Node n: bucket) {
                returnSet.add(n.key);
            }
        }
        return returnSet;
    }
    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    public V remove(K key) {
        if (!containsKey(key)) {
            return null;
        }
        else {
            int index = getIndex(key,this.buckets.length);
            V value = get(key);
            //这里如果要使用new Node(key,value)作为参数的话，必须要重新Node的equals()方法
            buckets[index].remove(new Node(key, value));
            //否则就只能传入处于表中的那个Node的对象引用，而不能传入一个与那个Node有同样的K-V对的新对象引用
            size --;
            return value;
        }
    }
    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    public V remove(K key, V value) {
        if (!containsKey(key)) {
            return null;
        }
        else {
            V returnVal = null;
            if (value.equals(get(key))) {
                  returnVal = remove(key);
            }
            return returnVal;
        }
    }
    public Iterator<K> iterator() {
        return this.keySet().iterator();
    }
}
