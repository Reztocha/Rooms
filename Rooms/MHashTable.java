/**
 * MHashTable.java
 * A Hashtable class for storing Player instances. Hashes should be stored as Strings, and are generated from a combination of 
 * the player's name and timestamp, thus allowing for two players to have the same name.
 * 
 * @author Christian Daher
 * @param <K> key
 * @param <V> value
 */

public class MHashTable<K, V>{
	
    /**
     * A simple Node data class containing generic fields for keys and values
     * @author Christian Daher
     *
     * @param <K> key
     * @param <V> value
     */
    protected static class Node<K, V> {
        public int key;
        public V value;
        
        public Node(K k, V v) {
            key = k.hashCode();
            value = v;
        }
    }
    
    private Node<K, V>[] master;
    private int CAPACITY = 101;
    
    /**
     * Initializes the master array with the default capacity
     */
    public MHashTable(){
        master = new Node[CAPACITY];
    }
    
    /**
     * Initializes the master array with an externally defined capacity
     * @param c capacity
     */
    public MHashTable(int c) {
    	CAPACITY = c + 1;
    	master = new Node[CAPACITY];
    }
    
    /**
     * Adds a value/key pair to the MHashTable
     * Handles collisions through linear probing
     * @param k key
     * @param v value
     */
    public void put(K k, V v) {
        int i = k.hashCode() % CAPACITY;
        boolean f = true;
        while (f) {
            if (master[i] == null) {
                f = false;
                master[i] = new Node<K, V>(k, v);
            }
            i++;
        }
    }
    
    /**
     * Takes a key and returns the corresponding value
     * @param k key
     * @return value
     */
    public V get(K k) {
        V v = null;
        Node<K, V> temp;
        int i = k.hashCode() % CAPACITY;
        boolean f = true;
        while (f && (i < CAPACITY - 1)) {
            temp = master[i];
            if (temp.key == k.hashCode()) {
                f = false;
                    v = (V) temp.value;
            }
            i++;
        }
        return v;
    }

    /**
     * Takes a key, sets the corresponding value to null, and returns the value
     * @param k key
     * @return value that has just been removed
     */
    public V remove(K k) {
    	V temp = get(k);
        int i = k.hashCode() % CAPACITY;
        master[k.hashCode() % CAPACITY] = null;
    	return temp;
    }
    
}