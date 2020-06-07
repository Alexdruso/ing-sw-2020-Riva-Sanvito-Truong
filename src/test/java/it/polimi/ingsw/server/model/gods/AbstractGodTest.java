package it.polimi.ingsw.server.model.gods;

import it.polimi.ingsw.server.model.turnevents.TurnEvents;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


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
        assertEquals(abstractGodInstance.getOwnerTurnEvents().getClass(), emptyTurnEventsInstance.getClass());
    }

    @Test
    public void getOpponentsTurnEvents() {
        assertEquals(abstractGodInstance.getOpponentsTurnEvents().getClass(), emptyTurnEventsInstance.getClass());
    }
}