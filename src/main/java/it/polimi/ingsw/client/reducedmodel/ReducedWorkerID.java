package it.polimi.ingsw.client.reducedmodel;

public enum ReducedWorkerID {
    WORKER1(0), WORKER2(1);

    private final int workerIDIndex;

    ReducedWorkerID(int workerIDIndex) {
        this.workerIDIndex = workerIDIndex;
    }

    public int getWorkerIDIndex() {
        return workerIDIndex;
    }
}
