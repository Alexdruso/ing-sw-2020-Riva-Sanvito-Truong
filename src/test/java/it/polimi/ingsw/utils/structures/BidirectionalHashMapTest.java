package it.polimi.ingsw.utils.structures;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BidirectionalHashMapTest {
    @Test
    void testLHashMap() {
        BidirectionalAbstractMapTestHarness testHarness = new BidirectionalAbstractMapTestHarness(new BidirectionalHashMap<>());
        testHarness.testBidirectionalAbstractMap();
    }
}