package it.polimi.ingsw.utils.structures;

import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;

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
        map.put("k2", "v3");
        Map<String, String> backward = map.getBackwardMap();
        Map<String, String> forward = map.getForwardMap();
        assertEquals(3, backward.size());
        assertEquals(2, forward.size());
        assertEquals("k1", backward.get("v1"));
        assertEquals("v1", forward.get("k1"));

        assertEquals(3, map.backwardEntrySet().size());
        assertEquals(2, map.forwardEntrySet().size());
        assertEquals(2, map.keySet().size());

        assertTrue(map.containsKey("k1"));
        assertTrue(map.containsValue("v1"));

        assertThrows(NullPointerException.class, () -> map.put(null, "a"));
        assertThrows(NullPointerException.class, () -> map.put("a", null));

        map.removeByValue("v3");
        assertEquals(1, map.size());
        assertEquals(2, map.values().size());

        map.removeByKey("k1");
        assertTrue(map.isEmpty());
    }
}
