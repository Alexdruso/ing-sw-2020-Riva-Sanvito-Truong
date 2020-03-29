package it.polimi.ingsw.model.actions;

import it.polimi.ingsw.model.workers.Worker;

/**
 * This interface provides a common access point to all the actions which can be performed
 * during a turn
 */
public interface Action {
    /**
     * @return The worker who performed the action
     */
    public Worker getPerformer();
}
