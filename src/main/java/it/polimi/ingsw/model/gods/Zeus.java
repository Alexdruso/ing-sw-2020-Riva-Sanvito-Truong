package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.turnevents.TurnEvents;

import java.util.Arrays;

/**
 * The god card Zeus.
 */
public class Zeus extends AbstractGod {
    /**
     * The TurnEvents for the owner of the Triton god card.
     */
    private static final TurnEvents ownerTurnEvents = new TurnEvents() {
        @Override
        protected void onBeforeBuild(Turn turn) {
            Arrays.stream(turn.getPlayer().getWorkers())
                    .forEach(
                            worker -> {
                                if (worker.getCell().getTower().getCurrentLevel() < 3) {
                                    turn.getWorkerBlockBuildableCells(worker)
                                            .setPosition(worker.getCell(), true);
                                }
                            }
                    );
        }
    };

    /**
     * Gets the god's name.
     *
     * @return the name
     */
    @Override
    public String getName() {
        return "Zeus";
    }

    /**
     * Gets the TurnEvents for the player owning Zeus.
     *
     * @return the TurnEvents for the player owning Zeus
     */
    @Override
    public TurnEvents getOwnerTurnEvents() {
        return ownerTurnEvents;
    }
}
