package it.polimi.ingsw.model.workers;

import it.polimi.ingsw.utils.messages.ReducedWorkerID;

public enum WorkerID {
    WORKER1, WORKER2;

    public static WorkerID fromReducedWorkerId(ReducedWorkerID reducedWorkerID) {
        try {
            return WorkerID.valueOf(reducedWorkerID.toString());
        }
        catch (IllegalArgumentException|NullPointerException e) {
            return null;
        }
    }
}
