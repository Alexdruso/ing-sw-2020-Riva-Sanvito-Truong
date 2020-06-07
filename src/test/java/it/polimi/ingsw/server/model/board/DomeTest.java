package it.polimi.ingsw.server.model.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DomeTest {
    private final Buildable dome = Component.DOME.getInstance();
    @Test
    public void blockIsTargetable(){
        assertEquals(dome.isTargetable(), false);
    }
}
