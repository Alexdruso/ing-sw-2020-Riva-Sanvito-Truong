package it.polimi.ingsw.server.model.turnevents;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Turn;
import it.polimi.ingsw.server.model.gods.God;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.mockito.Mockito.*;


class TurnEventsManagerTest {
    private TurnEventsManager turnEventsManager;
    private Turn mockedTurn;
    private Player owner;
    private Player opponent1;
    private Player opponent2;
    private God ownerGod;
    private God opponent1God;
    private God opponent2God;

    @BeforeEach
    void initTurnEventsManager() {
        mockedTurn = mock(Turn.class);
        owner = mock(Player.class);
        ownerGod = new EmptyGod();
        when(owner.getGod()).thenReturn(ownerGod);
        opponent1 = mock(Player.class);
        opponent1God = new EmptyGod();
        when(opponent1.getGod()).thenReturn(opponent1God);
        opponent2 = mock(Player.class);
        opponent2God = new EmptyGod();
        when(opponent2.getGod()).thenReturn(opponent2God);
        turnEventsManager = new TurnEventsManager(owner);
        turnEventsManager.addTurnEventsFromOpponents(opponent1, opponent1God.getOpponentsTurnEvents());
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    void callTurnStartEvents(boolean shallRemoveOpponent1) {
        if (shallRemoveOpponent1) {
            turnEventsManager.removeTurnEventsSetByOpponent(opponent1);
        }
        turnEventsManager.processTurnStartEvents(mockedTurn);
        verify(ownerGod.getOwnerTurnEvents(), times(1)).onTurnStart(mockedTurn);
        verify(opponent1God.getOpponentsTurnEvents(), shallRemoveOpponent1 ? never() : times(1)).onTurnStart(mockedTurn);
        verify(opponent2God.getOpponentsTurnEvents(), never()).onTurnStart(mockedTurn);
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    void callBeforeMovementEvents(boolean shallRemoveOpponent1) {
        if (shallRemoveOpponent1) {
            turnEventsManager.removeTurnEventsSetByOpponent(opponent1);
        }
        turnEventsManager.processBeforeMovementEvents(mockedTurn);
        verify(ownerGod.getOwnerTurnEvents(), times(1)).onBeforeMovement(mockedTurn);
        verify(opponent1God.getOpponentsTurnEvents(), shallRemoveOpponent1 ? never() : times(1)).onBeforeMovement(mockedTurn);
        verify(opponent2God.getOpponentsTurnEvents(), never()).onBeforeMovement(mockedTurn);
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    void callAfterMovementEvents(boolean shallRemoveOpponent1) {
        if (shallRemoveOpponent1) {
            turnEventsManager.removeTurnEventsSetByOpponent(opponent1);
        }
        turnEventsManager.processAfterMovementEvents(mockedTurn);
        verify(ownerGod.getOwnerTurnEvents(), times(1)).onAfterMovement(mockedTurn);
        verify(opponent1God.getOpponentsTurnEvents(), shallRemoveOpponent1 ? never() : times(1)).onAfterMovement(mockedTurn);
        verify(opponent2God.getOpponentsTurnEvents(), never()).onAfterMovement(mockedTurn);
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    void callBeforeBuildEvents(boolean shallRemoveOpponent1) {
        if (shallRemoveOpponent1) {
            turnEventsManager.removeTurnEventsSetByOpponent(opponent1);
        }
        turnEventsManager.processBeforeBuildEvents(mockedTurn);
        verify(ownerGod.getOwnerTurnEvents(), times(1)).onBeforeBuild(mockedTurn);
        verify(opponent1God.getOpponentsTurnEvents(), shallRemoveOpponent1 ? never() : times(1)).onBeforeBuild(mockedTurn);
        verify(opponent2God.getOpponentsTurnEvents(), never()).onBeforeBuild(mockedTurn);
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    void callAfterBuildEvents(boolean shallRemoveOpponent1) {
        if (shallRemoveOpponent1) {
            turnEventsManager.removeTurnEventsSetByOpponent(opponent1);
        }
        turnEventsManager.processAfterBuildEvents(mockedTurn);
        verify(ownerGod.getOwnerTurnEvents(), times(1)).onAfterBuild(mockedTurn);
        verify(opponent1God.getOpponentsTurnEvents(), shallRemoveOpponent1 ? never() : times(1)).onAfterBuild(mockedTurn);
        verify(opponent2God.getOpponentsTurnEvents(), never()).onAfterBuild(mockedTurn);
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    void callTurnEndEvents(boolean shallRemoveOpponent1) {
        if (shallRemoveOpponent1) {
            turnEventsManager.removeTurnEventsSetByOpponent(opponent1);
        }
        turnEventsManager.processTurnEndEvents(mockedTurn);
        verify(ownerGod.getOwnerTurnEvents(), times(1)).onTurnEnd(mockedTurn);
        verify(opponent1God.getOpponentsTurnEvents(), shallRemoveOpponent1 ? never() : times(1)).onTurnEnd(mockedTurn);
        verify(opponent2God.getOpponentsTurnEvents(), never()).onTurnEnd(mockedTurn);
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    void callWinConditions(boolean shallRemoveOpponent1) {
        if (shallRemoveOpponent1) {
            turnEventsManager.removeTurnEventsSetByOpponent(opponent1);
        }
        turnEventsManager.processWinConditionEvents(mockedTurn);
        verify(ownerGod.getOwnerTurnEvents(), times(1)).computeWinCondition(mockedTurn);
        verify(opponent1God.getOpponentsTurnEvents(), shallRemoveOpponent1 ? never() : times(1)).computeWinCondition(mockedTurn);
        verify(opponent2God.getOpponentsTurnEvents(), never()).computeWinCondition(mockedTurn);
    }

}

class EmptyGod implements God {
    private TurnEvents ownerTurnEvents;
    private TurnEvents opponentsTurnEvents;

    public EmptyGod() {
        ownerTurnEvents = spy(TurnEvents.class);
        opponentsTurnEvents = spy(TurnEvents.class);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public TurnEvents getOwnerTurnEvents() {
        return ownerTurnEvents;
    }

    @Override
    public TurnEvents getOpponentsTurnEvents() {
        return opponentsTurnEvents;
    }
}
