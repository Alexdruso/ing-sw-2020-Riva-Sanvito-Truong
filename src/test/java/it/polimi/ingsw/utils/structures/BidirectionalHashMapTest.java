package it.polimi.ingsw.utils.structures;

import org.junit.jupiter.api.Test;

class BidirectionalHashMapTest {
    @Test
    void testLHashMap() {
        BidirectionalAbstractMapTestHarness testHarness = new BidirectionalAbstractMapTestHarness(new BidirectionalHashMap<>());
        testHarness.testBidirectionalAbstractMap();
    }
}