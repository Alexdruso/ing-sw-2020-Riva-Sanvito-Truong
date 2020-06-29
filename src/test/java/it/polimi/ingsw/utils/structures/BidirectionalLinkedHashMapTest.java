package it.polimi.ingsw.utils.structures;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BidirectionalLinkedHashMapTest {
    @Test
    void testLinkedHashMap() {
        BidirectionalAbstractMapTestHarness testHarness = new BidirectionalAbstractMapTestHarness(new BidirectionalLinkedHashMap<>());
        testHarness.testBidirectionalAbstractMap();
    }

}