package it.polimi.ingsw.client.reducedmodel;

import it.polimi.ingsw.client.clientstates.AbstractClientTurnState;
import it.polimi.ingsw.utils.networking.transmittables.ReducedTargetCells;
import it.polimi.ingsw.utils.networking.transmittables.ReducedWorkerID;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ReducedTurn {
    private final ReducedPlayer player;
    private final AbstractClientTurnState turnState;
    private final List<ReducedWorkerID> allowedWorkers;
    private final EnumMap<ReducedWorkerID, ReducedTargetCells> workerBlockBuildableCells;
    private final EnumMap<ReducedWorkerID, ReducedTargetCells> workerDomeBuildableCells;
    private final EnumMap<ReducedWorkerID, ReducedTargetCells> workerWalkableCells;
    private final boolean isSkippable;

    public ReducedTurn(ReducedPlayer player, AbstractClientTurnState turnState) {
        this.player = player;
        this.turnState = turnState;
        this.allowedWorkers = new ArrayList<>();
        this.workerBlockBuildableCells = new EnumMap<>(ReducedWorkerID.class);
        this.workerDomeBuildableCells = new EnumMap<>(ReducedWorkerID.class);
        this.workerWalkableCells = new EnumMap<>(ReducedWorkerID.class);
        this.isSkippable = false;
    }

    public ReducedTurn(ReducedPlayer player, AbstractClientTurnState turnState, List<ReducedWorkerID> allowedWorkers, Map<ReducedWorkerID, ReducedTargetCells> workerWalkableCells, boolean isSkippable) {
        this.player = player;
        this.turnState = turnState;
        this.allowedWorkers = allowedWorkers;
        this.workerBlockBuildableCells = new EnumMap<>(ReducedWorkerID.class);
        this.workerDomeBuildableCells = new EnumMap<>(ReducedWorkerID.class);
        this.workerWalkableCells = new EnumMap<>(workerWalkableCells);
        this.isSkippable = isSkippable;
    }

    public ReducedTurn(ReducedPlayer player, AbstractClientTurnState turnState, List<ReducedWorkerID> allowedWorkers, Map<ReducedWorkerID, ReducedTargetCells> workerBlockBuildableCells, Map<ReducedWorkerID, ReducedTargetCells> workerDomeBuildableCells, boolean isSkippable) {
        this.player = player;
        this.turnState = turnState;
        this.allowedWorkers = allowedWorkers;
        this.workerBlockBuildableCells = new EnumMap<>(workerBlockBuildableCells);
        this.workerDomeBuildableCells = new EnumMap<>(workerDomeBuildableCells);
        this.isSkippable = isSkippable;
        this.workerWalkableCells = new EnumMap<>(ReducedWorkerID.class);
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
