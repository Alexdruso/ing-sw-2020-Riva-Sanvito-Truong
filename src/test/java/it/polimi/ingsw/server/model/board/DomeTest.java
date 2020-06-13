package it.polimi.ingsw.server.model.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DomeTest {
    private final Buildable dome = Component.DOME.getInstance();
    @Test
    public void blockIsTargetable(){
        assertFalse(dome.isTargetable());
    }
}
