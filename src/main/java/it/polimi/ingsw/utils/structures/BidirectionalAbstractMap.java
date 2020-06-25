package it.polimi.ingsw.utils.structures;

import java.util.Map;
import java.util.Set;

public abstract class BidirectionalAbstractMap<K,V> implements BidirectionalMap<K,V>{
    Map<K,V> forwardMap;
    Map<V,K> backwardMap;

    /**
     * Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned.
     * @return the value to which the specified key is mapped, or null if this map contains no mapping for the key.
     * @throws NullPointerException if key is null
     */
    @Override
    public V getValueFromKey(K key) {
        return forwardMap.get(key);
    }

    /**
     * Returns the key to which the specified value is mapped, or null if this map contains no mapping for the value.
     *
     * @param value the value whose associated key is to be returned.
     * @return the key to which the specified value is mapped, or null if this map contains no mapping for the key.
     * @throws NullPointerException if value is null
     */
    @Override
    public K getKeyFromValue(V value) {
        return backwardMap.get(value);
    }

    /**
     * Returns true if this map contains a mapping for the specified key. More formally, returns true if and only if
     * this map contains a mapping for a key k such that (key==null ? k==null : key.equals(k)).
     * (There can be at most one such mapping.)
     *
     * @param key key whose presence in this map is to be tested.
     * @return true if this map contains a mapping for the specified key, otherwise false.
     * @throws NullPointerException if key is null
     */
    @Override
    public boolean containsKey(K key) {
        return forwardMap.containsKey(key);
    }

    /**
     * Returns true if this map contains a mapping for the specified value. More formally, returns true if and only if
     * this map contains a mapping for a value v such that (value==null ? v==null : value.equals(v)).
     * (There can be at most one such mapping.)
     *
     * @param value value whose presence in this map is to be tested.
     * @return true if this map contains a mapping for the specified value, otherwise false.
     * @throws NullPointerException if value is null
     */
    @Override
    public boolean containsValue(V value) {
        return forwardMap.containsValue(value);
    }

    /**
     * Returns true if this map contains no key-value mappings.
     *
     * @return true if this map contains no key-value mappings, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return forwardMap.isEmpty();
    }

    /**
     * Associates the specified value with the specified key in this map (optional operation).
     * If the map previously contained a mapping for the key, the old value is replaced by the specified value.
     * (A map m is said to contain a mapping for a key k if and only if m.containsKey(k) would return true.)
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @throws NullPointerException if key or value are null
     */
    @Override
    public void put(K key, V value) {
        if(key == null){
            throw new NullPointerException("Key is null");
        }
        if(value == null){
            throw new NullPointerException("Value is null");
        }
        forwardMap.put(key, value);
        backwardMap.put(value, key);
    }

    /**
     * Removes the mapping for a key from this map if it is present.
     * More formally, if this map contains a mapping from key k to value v such that
     * (key==null ? k==null : key.equals(k)), that mapping is removed. (The map can contain at most one such mapping.)
     * <p>
     * The map will not contain a mapping for the specified key once the call returns.
     *
     * @param key the key to be removed
     * @return the value associated to the removed key
     */
    @Override
    public V removeByKey(K key) {
        backwardMap.remove(forwardMap.get(key));
        return forwardMap.remove(key);
    }

    /**
     * Removes the mapping for a value from this map if it is present.
     * More formally, if this map contains a mapping from key k to value v such that
     * (key==null ? k==null : key.equals(k)), that mapping is removed. (The map can contain at most one such mapping.)
     * <p>
     * The map will not contain a mapping for the specified key once the call returns.
     *
     * @param value the value to be removed
     * @return the key associated to the temoved value
     */
    @Override
    public K removeByValue(V value) {
        forwardMap.remove(backwardMap.get(value));
        return backwardMap.remove(value);
    }

    /**
     * Returns a Set view of the forward mappings contained in this map.
     *
     * @return a set view of the forward mappings contained in this map.
     */
    @Override
    public Set<Map.Entry<K, V>> forwardEntrySet() {
        return forwardMap.entrySet();
    }

    /**
     * Returns a Set view of the backward mappings contained in this map.
     *
     * @return a set view of the backward mappings contained in this map.
     */
    @Override
    public Set<Map.Entry<V, K>> backwardEntrySet() {
        return backwardMap.entrySet();
    }

    /**
     * Returns a Set view of the keys contained in this map.
     *
     * @return a set view of the keys contained in this map
     */
    @Override
    public Set<K> keySet() {
        return forwardMap.keySet();
    }

    /**
     * Returns a Set view of the values contained in this map.
     *
     * @return a Set view of the values contained in this map
     */
    @Override
    public Set<V> values() {
        return backwardMap.keySet();
    }

    /**
     * Returns the number of key-value mappings in this map.
     * If the map contains more than Integer.MAX_VALUE elements, returns INTEGER.MAX_VALUE.
     *
     * @return the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return forwardMap.size();
    }
}
