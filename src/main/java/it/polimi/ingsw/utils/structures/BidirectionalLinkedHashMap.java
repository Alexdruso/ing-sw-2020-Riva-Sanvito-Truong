package it.polimi.ingsw.utils.structures;

import java.util.HashMap;
import java.util.LinkedHashMap;
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
 * This implementation of the BidirectionalMap interface is backed by LinkedHashMap.
 *
 * @param <K> The type of the Key set
 * @param <V> The type of the Value set
 */
public class BidirectionalLinkedHashMap<K, V> extends BidirectionalAbstractMap<K,V> {

    public BidirectionalLinkedHashMap(){
        forwardMap = new LinkedHashMap<>();
        backwardMap = new LinkedHashMap<>();
    }

}
