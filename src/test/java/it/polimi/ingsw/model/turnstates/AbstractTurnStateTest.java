package it.polimi.ingsw.model.turnstates;

import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.workers.Worker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class AbstractTurnStateTest {
    Turn mockTurn = mock(Turn.class);
    Worker mockWorker = mock(Worker.class);
    Cell mockCell = mock(Cell.class);
    AbstractTurnState mockAbstractTurnState = mock(AbstractTurnState.class, Mockito.CALLS_REAL_METHODS);

    @Test
    void startTurn() {
        try{
            this.mockAbstractTurnState.startTurn(this.mockTurn);
            fail();
        }
        catch (Exception e){
            assertEquals(e.getClass(), InvalidTurnStateException.class);
            assertTrue(true);
        }
    }

    @Test
    void canMoveTo() {
        assertFalse(this.mockAbstractTurnState.canMoveTo(this.mockWorker, this.mockCell, this.mockTurn));
    }

    @Test
    void moveTo() {
        try{
            this.mockAbstractTurnState.moveTo(this.mockWorker, this.mockCell, this.mockTurn);
            fail();
        }
        catch (Exception e){
            assertEquals(e.getClass(), InvalidTurnStateException.class);
            assertTrue(true);
        }
    }

    @Test
    void canBuildDomeIn() {
        assertFalse(this.mockAbstractTurnState.canBuildDomeIn(this.mockWorker, this.mockCell, this.mockTurn));
    }

    @Test
    void buildDomeIn() {
        try{
            this.mockAbstractTurnState.buildDomeIn(this.mockWorker, this.mockCell, this.mockTurn);
            fail();
        }
        catch (Exception e){
            assertEquals(e.getClass(), InvalidTurnStateException.class);
            assertTrue(true);
        }
    }

    @Test
    void canBuildBlockIn() {
        assertFalse(this.mockAbstractTurnState.canBuildBlockIn(this.mockWorker, this.mockCell, this.mockTurn));
    }

    @Test
    void buildBlockIn() {
        try{
            this.mockAbstractTurnState.buildBlockIn(this.mockWorker, this.mockCell, this.mockTurn);
            fail();
        }
        catch (Exception e){
            assertEquals(e.getClass(), InvalidTurnStateException.class);
            assertTrue(true);
        }
    }

    @Test
    void draw() {
        this.mockAbstractTurnState.draw(this.mockTurn);
        verify(this.mockTurn).triggerLosingTurn();
    }

    @Test
    void canEndTurn() {
        assertFalse(this.mockAbstractTurnState.canEndTurn(this.mockTurn));
    }

    @Test
    void endTurn() {
        try{
            this.mockAbstractTurnState.endTurn(this.mockTurn);
            fail();
        }
        catch (Exception e){
            assertEquals(e.getClass(), InvalidTurnStateException.class);
            assertTrue(true);
        }
    }
}