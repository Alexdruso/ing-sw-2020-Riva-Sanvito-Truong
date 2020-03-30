package it.polimi.ingsw.model.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ComponentTest {

    @Test
    public void componentInstantiation(){
        Buildable build = Component.BLOCK.getInstance();
        assertTrue(build instanceof Block);
        build = Component.DOME.getInstance();
        assertTrue(build instanceof Dome);
    }
}
