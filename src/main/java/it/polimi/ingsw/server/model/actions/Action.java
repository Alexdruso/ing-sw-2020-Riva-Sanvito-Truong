package it.polimi.ingsw.server.model.actions;

import it.polimi.ingsw.server.model.workers.Worker;

/**
 * This interface provides a common access point to all the actions which can be performed
 * during a turn
 */
public interface Action {
    /**
     * Gets the worker.
     *
     * @return The worker who performed the action
     */
    Worker getWorker();
}
