package it.polimi.ingsw.model.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DomeTest {
    private final Dome dome = new Dome();
    @Test
    public void blockIsTargetable(){
        assertEquals(dome.isTargetable(), false);
    }
}
