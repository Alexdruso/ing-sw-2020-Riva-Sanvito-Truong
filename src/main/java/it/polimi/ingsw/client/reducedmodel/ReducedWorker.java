package it.polimi.ingsw.client.reducedmodel;

import it.polimi.ingsw.utils.networking.transmittables.ReducedWorkerID;

public class ReducedWorker {
    private final ReducedPlayer player;
    private final ReducedWorkerID workerID;
    private ReducedCell cell;

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

    public ReducedCell getCell() {
        return cell;
    }

    public void setCell(ReducedCell cell) {
        this.cell = cell;
    }
}
