package it.polimi.ingsw.utils.structures;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BidirectionalAbstractMapTestHarness {
    private final BidirectionalAbstractMap<String, String> map;

    BidirectionalAbstractMapTestHarness(BidirectionalAbstractMap<String, String> map) {
        this.map = map;
    }

    void testBidirectionalAbstractMap() {
        map.put("k1", "v1");
        map.put("k2", "v2");
        map.put("k3", "v3");
        Map<String, String> backward = map.getBackwardMap();
        Map<String, String> forward = map.getForwardMap();
        assertEquals(3, backward.size());
        assertEquals(3, forward.size());
        assertEquals("k1", backward.get("v1"));
        assertEquals("v1", forward.get("k1"));

        assertEquals(3, map.backwardEntrySet().size());
        assertEquals(3, map.forwardEntrySet().size());
        assertEquals(3, map.keySet().size());

        assertTrue(map.containsKey("k1"));
        assertTrue(map.containsValue("v1"));

        assertThrows(NullPointerException.class, () -> map.put(null, "a"));
        assertThrows(NullPointerException.class, () -> map.put("a", null));

        map.removeByValue("v3");
        assertEquals(2, map.size());
        assertEquals(2, map.values().size());

        map.removeByKey("k1");
        map.removeByKey("k2");
        assertTrue(map.isEmpty());
    }
}
