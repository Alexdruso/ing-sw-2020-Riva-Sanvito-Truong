package it.polimi.ingsw.model.workers;

import it.polimi.ingsw.utils.messages.ReducedWorkerID;

public enum WorkerID {
    WORKER1, WORKER2;

    public static WorkerID fromReducedWorkerId(ReducedWorkerID reducedWorkerID) {
            return WorkerID.valueOf(reducedWorkerID.toString());
    }

    public ReducedWorkerID toReducedWorkerId() {
        return ReducedWorkerID.valueOf(this.name());
    }
}
