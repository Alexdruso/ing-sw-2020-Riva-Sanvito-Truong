package it.polimi.ingsw.client.reducedmodel;

import it.polimi.ingsw.client.clientstates.AbstractClientTurnState;
import it.polimi.ingsw.utils.networking.transmittables.ReducedTargetCells;
import it.polimi.ingsw.utils.networking.transmittables.ReducedWorkerID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReducedTurn {
    private final ReducedPlayer player;
    private final AbstractClientTurnState turnState;
    private final List<ReducedWorkerID> allowedWorkers;
    private final Map<ReducedWorkerID, ReducedTargetCells> workerBlockBuildableCells;
    private final Map<ReducedWorkerID, ReducedTargetCells> workerDomeBuildableCells;
    private final Map<ReducedWorkerID, ReducedTargetCells> workerWalkableCells;
    private final boolean isSkippable;

    public ReducedTurn(ReducedPlayer player, AbstractClientTurnState turnState) {
        this.player = player;
        this.turnState = turnState;
        this.allowedWorkers = new ArrayList<>();
        this.workerBlockBuildableCells = new HashMap<>();
        this.workerDomeBuildableCells = new HashMap<>();
        this.workerWalkableCells = new HashMap<>();
        this.isSkippable = false;
    }

    public ReducedTurn(ReducedPlayer player, AbstractClientTurnState turnState, List<ReducedWorkerID> allowedWorkers, Map<ReducedWorkerID, ReducedTargetCells> workerWalkableCells, boolean isSkippable) {
        this.player = player;
        this.turnState = turnState;
        this.allowedWorkers = allowedWorkers;
        this.workerBlockBuildableCells = new HashMap<>();
        this.workerDomeBuildableCells = new HashMap<>();
        this.workerWalkableCells = workerWalkableCells;
        this.isSkippable = isSkippable;
    }

    public ReducedTurn(ReducedPlayer player, AbstractClientTurnState turnState, List<ReducedWorkerID> allowedWorkers, Map<ReducedWorkerID, ReducedTargetCells> workerBlockBuildableCells, Map<ReducedWorkerID, ReducedTargetCells> workerDomeBuildableCells, boolean isSkippable) {
        this.player = player;
        this.turnState = turnState;
        this.allowedWorkers = allowedWorkers;
        this.workerBlockBuildableCells = workerBlockBuildableCells;
        this.workerDomeBuildableCells = workerDomeBuildableCells;
        this.isSkippable = isSkippable;
        this.workerWalkableCells = new HashMap<>();
    }

    public ReducedPlayer getPlayer() {
        return player;
    }

    public AbstractClientTurnState getTurnState() {
        return turnState;
    }

    public List<ReducedWorkerID> getAllowedWorkers() {
        return allowedWorkers;
    }

    public ReducedTargetCells getWorkerBlockBuildableCells(ReducedWorkerID workerID) {
        return workerBlockBuildableCells.get(workerID);
    }

    public ReducedTargetCells getWorkerDomeBuildableCells(ReducedWorkerID workerID) {
        return workerDomeBuildableCells.get(workerID);
    }

    public ReducedTargetCells getWorkerWalkableCells(ReducedWorkerID workerID) {
        return workerWalkableCells.get(workerID);
    }

    public boolean isSkippable() {
        return isSkippable;
    }
}
