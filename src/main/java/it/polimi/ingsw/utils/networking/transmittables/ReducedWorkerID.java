package it.polimi.ingsw.utils.networking.transmittables;

/**
 * A enum representing a reduced version of a worker ID.
 */
public enum ReducedWorkerID {
    /**
     * Worker ID.
     */
    WORKER1(0),
    /**
     * Worker 2 reduced worker id.
     */
    WORKER2(1);

    private final int workerIDIndex;

    /**
     * Constructs a new ReducedWorkerID.
     *
     * @param workerIDIndex the worker ID index
     */
    ReducedWorkerID(int workerIDIndex) {
        this.workerIDIndex = workerIDIndex;
    }

    /**
     * Gets worker id index.
     *
     * @return the worker id index
     */
    public int getWorkerIDIndex() {
        return workerIDIndex;
    }
}
