package it.polimi.ingsw.client.reducedmodel;

public class ReducedWorker {
    private final ReducedPlayer player;
    private final ReducedWorkerID workerID;

    public ReducedWorker(ReducedWorkerID workerID, ReducedPlayer player) {
        this.player = player;
        this.workerID = workerID;
    }

    public ReducedPlayer getPlayer() {
        return player;
    }

    public ReducedWorkerID getWorkerID() {
        return workerID;
    }
}