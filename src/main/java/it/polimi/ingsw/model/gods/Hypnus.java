package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.turnevents.TurnEvents;
import it.polimi.ingsw.model.workers.Worker;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;

/**
 * The god card Hypnus.
 */
public class Hypnus extends AbstractGod {
    /**
     * The TurnEvents for the opponents of the owner of the Hypnus god card.
     */
    private static final TurnEvents opponentsTurnEvents = new TurnEvents() {
        @Override
        protected void onBeforeMovement(Turn turn) {
            //check if turn has just begun
            if (turn.getPerformedAction().isEmpty()) {
                //check if there is one worker above all the others
                Arrays.stream(turn.getPlayer().getWorkers())
                        .max(Comparator.comparingInt(x -> x.getCell().getTower().getCurrentLevel()))
                        .ifPresent(
                                maxWorker -> Arrays.stream(turn.getPlayer().getWorkers())
                                        .filter(worker -> !worker.equals(maxWorker))
                                        .map(worker -> worker.getCell().getTower().getCurrentLevel())
                                        .filter(level -> level == maxWorker.getCell().getTower().getCurrentLevel())
                                        .findFirst().ifPresentOrElse(
                                                level -> {
                                                },
                                                () -> {
                                                    //if there is one worker above all the others,
                                                    //remove him from allowed workers
                                                    Set<Worker> allowedWorkers = turn.getAllowedWorkers();
                                                    allowedWorkers.removeIf(worker -> worker.equals(maxWorker));
                                                    turn.clearAllowedWorkers();
                                                    turn.addAllowedWorkers(allowedWorkers);
                                                }
                                        )
                        );
            }
        }
    };

    /**
     * Gets the god's name.
     *
     * @return the name
     */
    @Override
    public String getName() {
        return "Hypnus";
    }

    /**
     * Gets the TurnEvents for the opponents of the player owning Hypnus.
     *
     * @return the TurnEvents for the opponents of the player owning Hypnus
     */
    @Override
    public TurnEvents getOpponentsTurnEvents() {
        return opponentsTurnEvents;
    }
}
