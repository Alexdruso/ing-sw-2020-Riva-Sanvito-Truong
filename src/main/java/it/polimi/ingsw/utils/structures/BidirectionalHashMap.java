package it.polimi.ingsw.utils.structures;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * An object that represents a bijection between elements of type K belonging to one set (the Key set) and elements of
 * type V belonging to another set (the Value set)
 *
 * The relation from the Key set to the Value set is, by convention, called the Forward relation. On the other hand,
 * the relation from the Value set to the Key set is, by convention, called the Backward relation.
 *
 * This means that for each element of the Key set there is only one corresponding element in the Value set and
 * vice-versa.
 *
 * This interface is based on the Map<K, V> interface, yet it does not extend it as it does not follow Liskov's
 * substitution rule. As a matter of fact, a BidirectionalMap requires that the values be unique,
 * a condition that does not have to hold true for a Map.
 *
 * The terms "Key" and "Value" are only used as convention, in order to maintain similarity with the standard Map
 * interface, but the data structure is, in fact, symmetrical and does not distinguish forward and backwards direction
 * if not for the name of the methods.
 *
 * For this bidirectional map, null values are not allowed neither as key, nor as values.
 *
 * This implementation of the BidirectionalMap interface is backed by HashMap.
 *
 * @param <K> The type of the Key set
 * @param <V> The type of the Value set
 */
public class BidirectionalHashMap<K, V> implements BidirectionalMap<K, V>{

    private HashMap<K, V> forwardMap = new HashMap<>();
    private HashMap<V, K> backwardMap = new HashMap<>();

    /**
     * Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned.
     * @return the value to which the specified key is mapped, or null if this map contains no mapping for the key.
     */
    @Override
    public V getValueFromKey(K key) throws NullPointerException {
        return forwardMap.get(key);
    }

    /**
     * Returns the key to which the specified value is mapped, or null if this map contains no mapping for the value.
     *
     * @param value the value whose associated key is to be returned.
     * @return the key to which the specified value is mapped, or null if this map contains no mapping for the key.
     */
    @Override
    public K getKeyFromValue(V value) throws NullPointerException {
        return backwardMap.get(value);
    }

    /**
     * Returns true if this map contains a mapping for the specified key. More formally, returns true if and only if
     * this map contains a mapping for a key k such that (key==null ? k==null : key.equals(k)).
     * (There can be at most one such mapping.)
     *
     * @param key key whose presence in this map is to be tested.
     * @return true if this map contains a mapping for the specified key, otherwise false.
     */
    @Override
    public boolean containsKey(K key) throws NullPointerException {
        return forwardMap.containsKey(key);
    }

    /**
     * Returns true if this map contains a mapping for the specified value. More formally, returns true if and only if
     * this map contains a mapping for a value v such that (value==null ? v==null : value.equals(v)).
     * (There can be at most one such mapping.)
     *
     * @param value value whose presence in this map is to be tested.
     * @return true if this map contains a mapping for the specified value, otherwise false.
     */
    @Override
    public boolean containsValue(V value) throws NullPointerException {
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
     */
    @Override
    public void put(K key, V value) throws NullPointerException {
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
     * @param key
     * @return
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
     * @param value
     * @return
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

    /**
     * Returns a copy of this map that contains all and only the relations from the key set to the value set.
     *
     * @return a copy of this map that contains all and only the relations from the key set to the value set.
     */
    @Override
    public Map<K, V> getForwardMap() {
        return new HashMap<>(forwardMap);
    }

    /**
     * Returns a copy of this map that contains all and only the relations from the value set to the key set.
     *
     * @return a copy of this map that contains all and only the relations from the value set to the key set.
     */
    @Override
    public Map<V, K> getBackwardMap() {
        return new HashMap<>(backwardMap);
    }
}
