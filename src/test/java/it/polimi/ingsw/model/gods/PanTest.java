package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.actions.MoveAction;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.turnevents.TurnEventsManager;
import it.polimi.ingsw.model.workers.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class PanTest {
    private final Pan pan = new Pan();
    private final Player mockedPlayer = mock(Player.class);
    private final TurnEventsManager turnEventsManager = new TurnEventsManager(mockedPlayer);
    private final Turn mockedTurn = mock(Turn.class);
    private final Worker mockedWorker = mock(Worker.class);
    private final Cell cell00 = new Cell(0, 0);
    private final Cell cell01 = new Cell(0, 1);
    List<MoveAction> mockedMoveActions = new LinkedList<MoveAction>();

    @BeforeEach
    void beforeEachTest() {
        when(mockedPlayer.getGod()).thenReturn(pan);
        when(mockedTurn.getMoves()).thenReturn(mockedMoveActions);
        mockedMoveActions.clear();
    }

    @Test
    void neutralConditionDown0() {
        mockedMoveActions.add(new MoveAction(cell00, cell01, 2, 2, mockedWorker));

        turnEventsManager.processWinConditionEvents(mockedTurn);

        verify(mockedTurn, never()).setWinningTurn();
    }

    @Test
    void neutralConditionDown1() {
        mockedMoveActions.add(new MoveAction(cell00, cell01, 2, 1, mockedWorker));

        turnEventsManager.processWinConditionEvents(mockedTurn);

        verify(mockedTurn, never()).setWinningTurn();
    }

    @Test
    void winConditionDown2() {
        mockedMoveActions.add(new MoveAction(cell00, cell01, 3, 1, mockedWorker));

        turnEventsManager.processWinConditionEvents(mockedTurn);

        verify(mockedTurn).setWinningTurn();
    }

    @Test
    void winConditionDown3() {
        mockedMoveActions.add(new MoveAction(cell00, cell01, 3, 0, mockedWorker));

        turnEventsManager.processWinConditionEvents(mockedTurn);

        verify(mockedTurn).setWinningTurn();
    }

    @Test
    void neutralConditionUp2() {
        mockedMoveActions.add(new MoveAction(cell00, cell01, 0, 2, mockedWorker));

        turnEventsManager.processWinConditionEvents(mockedTurn);

        verify(mockedTurn, never()).setWinningTurn();
    }
}
