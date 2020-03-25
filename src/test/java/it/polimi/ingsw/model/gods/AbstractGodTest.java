package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.turnevents.TurnEvents;
import org.junit.Test;

import static org.junit.Assert.*;


public class AbstractGodTest {
    private final TurnEvents emptyTurnEventsInstance = new TurnEvents();
    private final AbstractGod abstractGodInstance = new AbstractGod() {
        @Override
        public String getName() {
            return null;
        }
    };

    @Test
    public void getOwnerTurnEvents() {
        assertSame(abstractGodInstance.getOwnerTurnEvents().getClass(), emptyTurnEventsInstance.getClass());
    }

    @Test
    public void getOpponentsTurnEvents() {
        assertSame(abstractGodInstance.getOpponentsTurnEvents().getClass(), emptyTurnEventsInstance.getClass());
    }
}