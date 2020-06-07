package it.polimi.ingsw.server.model.workers;

import it.polimi.ingsw.utils.messages.ReducedWorkerID;

public enum WorkerID {
    WORKER1, WORKER2;

    /**
     * Converts a reduced worker id into a worker id
     *
     * @param reducedWorkerID the reduced worker id
     * @return the worker id
     */
    public static WorkerID fromReducedWorkerId(ReducedWorkerID reducedWorkerID) {
        return WorkerID.valueOf(reducedWorkerID.toString());
    }

    /**
     * Converts a worker id into a reduced worker id
     *
     * @return the reduced worker id
     */
    public ReducedWorkerID toReducedWorkerId() {
        return ReducedWorkerID.valueOf(this.name());
    }
}
