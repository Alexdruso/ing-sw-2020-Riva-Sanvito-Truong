package it.polimi.ingsw.utils.structures;

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
 * This interface is based on the Map&lt;K, V&gt; interface, yet it does not extend it as it does not follow Liskov's
 * substitution rule. As a matter of fact, a BidirectionalMap requires that the values be unique,
 * a condition that does not have to hold true for a Map.
 *
 * The terms "Key" and "Value" are only used as convention, in order to maintain similarity with the standard Map
 * interface, but the data structure is, in fact, symmetrical and does not distinguish forward and backwards direction
 * if not for the name of the methods.
 *
 * For this bidirectional map, null values are not allowed neither as key, nor as values.
 *
 * @param <K> The type of the Key set
 * @param <V> The type of the Value set
 */
public interface BidirectionalMap<K, V> {
    /**
     * Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
     * @param key the key whose associated value is to be returned.
     * @return the value to which the specified key is mapped, or null if this map contains no mapping for the key.
     * @throws NullPointerException if key is null
     */
    V getValueFromKey(K key);

    /**
     * Returns the key to which the specified value is mapped, or null if this map contains no mapping for the value.
     * @param value the value whose associated key is to be returned.
     * @return the key to which the specified value is mapped, or null if this map contains no mapping for the key.
     * @throws NullPointerException if value is null
     */
    K getKeyFromValue(V value);

    /**
     * Returns true if this map contains a mapping for the specified key. More formally, returns true if and only if
     * this map contains a mapping for a key k such that (key==null ? k==null : key.equals(k)).
     * (There can be at most one such mapping.)
     * @param key key whose presence in this map is to be tested.
     * @return true if this map contains a mapping for the specified key, otherwise false.
     * @throws NullPointerException if key is null
     */
    boolean containsKey(K key);

    /**
     * Returns true if this map contains a mapping for the specified value. More formally, returns true if and only if
     * this map contains a mapping for a value v such that (value==null ? v==null : value.equals(v)).
     * (There can be at most one such mapping.)
     * @param value value whose presence in this map is to be tested.
     * @return true if this map contains a mapping for the specified value, otherwise false.
     * @throws NullPointerException if value is null
     */
    boolean containsValue(V value);

    /**
     * Returns true if this map contains no key-value mappings.
     * @return true if this map contains no key-value mappings, false otherwise.
     */
    boolean isEmpty();

    /**
     * Associates the specified value with the specified key in this map (optional operation).
     * If the map previously contained a mapping for the key, the old value is replaced by the specified value.
     * (A map m is said to contain a mapping for a key k if and only if m.containsKey(k) would return true.)
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @throws NullPointerException if key or value are null
     */
    void put(K key, V value);

    /**
     * Removes the mapping for a key from this map if it is present.
     * More formally, if this map contains a mapping from key k to value v such that
     * (key==null ? k==null : key.equals(k)), that mapping is removed. (The map can contain at most one such mapping.)
     *
     * The map will not contain a mapping for the specified key once the call returns.
     * @param key the key to be removed
     * @return the value associated to the removed key
     */
    V removeByKey(K key);

    /**
     * Removes the mapping for a value from this map if it is present.
     * More formally, if this map contains a mapping from key k to value v such that
     * (key==null ? k==null : key.equals(k)), that mapping is removed. (The map can contain at most one such mapping.)
     *
     * The map will not contain a mapping for the specified key once the call returns.
     * @param value the value to be removed
     * @return the key associated to the removed value
     */
    K removeByValue(V value);

    /**
     * Returns a Set view of the forward mappings contained in this map.
     * @return a set view of the forward mappings contained in this map.
     */
    Set<Map.Entry<K, V>> forwardEntrySet();

    /**
     * Returns a Set view of the backward mappings contained in this map.
     * @return a set view of the backward mappings contained in this map.
     */
    Set<Map.Entry<V, K>> backwardEntrySet();

    /**
     * Returns a Set view of the keys contained in this map.
     * @return a set view of the keys contained in this map
     */
    Set<K> keySet();

    /**
     * Returns a Set view of the values contained in this map.
     * @return a Set view of the values contained in this map
     */
    Set<V> values();

    /**
     * Returns the number of key-value mappings in this map.
     * If the map contains more than Integer.MAX_VALUE elements, returns INTEGER.MAX_VALUE.
     * @return the number of key-value mappings in this map.
     */
    int size();


    /**
     * Returns the hash code value for this map.
     * The hash code of a map is defined to be the sum of the hash codes of each entry in the map's entrySet() view.
     * This ensures that m1.equals(m2) implies that m1.hashCode()==m2.hashCode() for any two maps m1 and m2,
     * as required by the general contract of Object.hashCode().
     *
     * @return the hash code value for this map.
     */
    @Override
    int hashCode();

    /**
     * Returns a copy of this map that contains all and only the relations from the key set to the value set.
     *
     * @return a copy of this map that contains all and only the relations from the key set to the value set.
     */
    Map<K, V> getForwardMap();

    /**
     * Returns a copy of this map that contains all and only the relations from the value set to the key set.
     *
     * @return a copy of this map that contains all and only the relations from the value set to the key set.
     */
    Map<V, K> getBackwardMap();
}
