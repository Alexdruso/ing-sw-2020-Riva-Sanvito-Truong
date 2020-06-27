package it.polimi.ingsw.client.reducedmodel;

import it.polimi.ingsw.client.clientstates.AbstractClientTurnState;
import it.polimi.ingsw.utils.networking.transmittables.ReducedTargetCells;
import it.polimi.ingsw.utils.networking.transmittables.ReducedWorkerID;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * A representation of a turn of the game, reduced with respect to the server's Turn to contain only the information required by the client.
 */
public class ReducedTurn {
    private final ReducedPlayer player;
    private final AbstractClientTurnState turnState;
    private final List<ReducedWorkerID> allowedWorkers;
    private final EnumMap<ReducedWorkerID, ReducedTargetCells> workerBlockBuildableCells;
    private final EnumMap<ReducedWorkerID, ReducedTargetCells> workerDomeBuildableCells;
    private final EnumMap<ReducedWorkerID, ReducedTargetCells> workerWalkableCells;
    private final boolean isSkippable;

    /**
     * Instantiates a new Reduced turn without setting any kind of buildable or walkable cells.
     *
     * @param player    the player who is playing the turn
     * @param turnState the turn state the turn is currently into
     */
    public ReducedTurn(ReducedPlayer player, AbstractClientTurnState turnState) {
        this.player = player;
        this.turnState = turnState;
        this.allowedWorkers = new ArrayList<>();
        this.workerBlockBuildableCells = new EnumMap<>(ReducedWorkerID.class);
        this.workerDomeBuildableCells = new EnumMap<>(ReducedWorkerID.class);
        this.workerWalkableCells = new EnumMap<>(ReducedWorkerID.class);
        this.isSkippable = false;
    }

    /**
     * Instantiates a new Reduced and sets the walkable cells.
     *
     * @param player              the player who is playing the turn
     * @param turnState           the turn state the turn is currently into
     * @param allowedWorkers      the allowed workers
     * @param workerWalkableCells the worker walkable cells
     * @param isSkippable         whether the turn is skippable
     */
    public ReducedTurn(ReducedPlayer player, AbstractClientTurnState turnState, List<ReducedWorkerID> allowedWorkers, Map<ReducedWorkerID, ReducedTargetCells> workerWalkableCells, boolean isSkippable) {
        this.player = player;
        this.turnState = turnState;
        this.allowedWorkers = allowedWorkers;
        this.workerBlockBuildableCells = new EnumMap<>(ReducedWorkerID.class);
        this.workerDomeBuildableCells = new EnumMap<>(ReducedWorkerID.class);
        this.workerWalkableCells = new EnumMap<>(workerWalkableCells);
        this.isSkippable = isSkippable;
    }

    /**
     * Instantiates a new Reduced and sets the walkable cells.
     *
     * @param player                    the player who is playing the turn
     * @param turnState                 the turn state the turn is currently into
     * @param allowedWorkers            the allowed workers
     * @param workerBlockBuildableCells the worker block buildable cells
     * @param workerDomeBuildableCells  the worker dome buildable cells
     * @param isSkippable               whether the turn is skippable
     */
    public ReducedTurn(ReducedPlayer player, AbstractClientTurnState turnState, List<ReducedWorkerID> allowedWorkers, Map<ReducedWorkerID, ReducedTargetCells> workerBlockBuildableCells, Map<ReducedWorkerID, ReducedTargetCells> workerDomeBuildableCells, boolean isSkippable) {
        this.player = player;
        this.turnState = turnState;
        this.allowedWorkers = allowedWorkers;
        this.workerBlockBuildableCells = new EnumMap<>(workerBlockBuildableCells);
        this.workerDomeBuildableCells = new EnumMap<>(workerDomeBuildableCells);
        this.isSkippable = isSkippable;
        this.workerWalkableCells = new EnumMap<>(ReducedWorkerID.class);
    }

    /**
     * Gets the player who is actively playing the turn.
     *
     * @return the player who is actively playing the turn
     */
    public ReducedPlayer getPlayer() {
        return player;
    }

    /**
     * Gets the turn state.
     *
     * @return the turn state
     */
    public AbstractClientTurnState getTurnState() {
        return turnState;
    }

    /**
     * Gets the allowed workers.
     *
     * @return the allowed workers
     */
    public List<ReducedWorkerID> getAllowedWorkers() {
        return allowedWorkers;
    }

    /**
     * Gets the worker block buildable cells of a worker.
     *
     * @param workerID the worker id
     * @return the worker block buildable cells
     */
    public ReducedTargetCells getWorkerBlockBuildableCells(ReducedWorkerID workerID) {
        return workerBlockBuildableCells.get(workerID);
    }

    /**
     * Gets the worker dome buildable cells of a worker.
     *
     * @param workerID the worker id
     * @return the worker dome buildable cells
     */
    public ReducedTargetCells getWorkerDomeBuildableCells(ReducedWorkerID workerID) {
        return workerDomeBuildableCells.get(workerID);
    }

    /**
     * Gets the worker walkable cells of a worker.
     *
     * @param workerID the worker id
     * @return the worker walkable cells
     */
    public ReducedTargetCells getWorkerWalkableCells(ReducedWorkerID workerID) {
        return workerWalkableCells.get(workerID);
    }

    /**
     * Checks if the turn is skippable.
     *
     * @return whether the turn is skippable
     */
    public boolean isSkippable() {
        return isSkippable;
    }
}
