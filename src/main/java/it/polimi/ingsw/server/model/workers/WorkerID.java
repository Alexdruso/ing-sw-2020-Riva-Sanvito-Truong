package it.polimi.ingsw.server.model.workers;

import it.polimi.ingsw.utils.networking.transmittables.ReducedWorkerID;

/**
 * The enum Worker id.
 */
public enum WorkerID {
    /**
     * ID of the first worker.
     */
    WORKER1,
    /**
     * ID of the second worker.
     */
    WORKER2;

    /**
     * Converts a reduced worker id into a worker id
     *
     * @param reducedWorkerID the reduced worker id
     * @return the worker id
     */
    public static WorkerID fromReducedWorkerID(ReducedWorkerID reducedWorkerID) {
        return WorkerID.valueOf(reducedWorkerID.toString());
    }

    /**
     * Converts a worker id into a reduced worker id
     *
     * @return the reduced worker id
     */
    public ReducedWorkerID toReducedWorkerID() {
        return ReducedWorkerID.valueOf(this.name());
    }
}
