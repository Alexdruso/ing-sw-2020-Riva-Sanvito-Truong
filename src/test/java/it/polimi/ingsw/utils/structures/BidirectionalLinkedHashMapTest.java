package it.polimi.ingsw.utils.structures;

import org.junit.jupiter.api.Test;

class BidirectionalLinkedHashMapTest {
    @Test
    void testLinkedHashMap() {
        BidirectionalAbstractMapTestHarness testHarness = new BidirectionalAbstractMapTestHarness(new BidirectionalLinkedHashMap<>());
        testHarness.testBidirectionalAbstractMap();
    }

}