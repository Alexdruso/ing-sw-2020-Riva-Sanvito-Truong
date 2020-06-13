package it.polimi.ingsw.utils.networking.transmittables.servermessages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.clientstates.ClientTurnState;
import it.polimi.ingsw.client.reducedmodel.ReducedTurn;
import it.polimi.ingsw.utils.networking.ClientHandleable;
import it.polimi.ingsw.utils.networking.transmittables.ReducedTargetCells;
import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;
import it.polimi.ingsw.utils.networking.transmittables.ReducedWorkerID;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * The type Server ask build message.
 */
public class ServerAskBuildMessage implements ServerMessage, ClientHandleable {
    /**
     * The User who has to choose a build.
     */
    public final ReducedUser user;
    /**
     * A boolean expressing if the build can be skipped.
     */
    public final boolean isSkippable;
    /**
     * The workers allowed to move.
     */
    private final List<ReducedWorkerID> allowedWorkers;
    /**
     * The cells that can be built a block on by each worker.
     */
    private final EnumMap<ReducedWorkerID, ReducedTargetCells> workerBlockBuildableCells;
    /**
     * The cells that can be built a block on by each worker.
     */
    private final EnumMap<ReducedWorkerID, ReducedTargetCells> workerDomeBuildableCells;

    /**
     * Instantiates a new Server ask build message.
     *
     * @param user                      the user
     * @param isSkippable               the boolean expressing if the build can be skipped
     * @param allowedWorkers            the allowed workers
     * @param workerBlockBuildableCells the worker block buildable cells
     * @param workerDomeBuildableCells  the worker dome buildable cells
     */
    public ServerAskBuildMessage(ReducedUser user, boolean isSkippable, List<ReducedWorkerID> allowedWorkers,
                                 EnumMap<ReducedWorkerID, ReducedTargetCells> workerBlockBuildableCells,
                                 EnumMap<ReducedWorkerID, ReducedTargetCells> workerDomeBuildableCells) {
        this.user = user;
        this.isSkippable = isSkippable;
        this.allowedWorkers = allowedWorkers;
        this.workerBlockBuildableCells = workerBlockBuildableCells;
        this.workerDomeBuildableCells = workerDomeBuildableCells;
    }

    @Override
    public boolean handleTransmittable(Client client) {
        client.setCurrentActiveUser(user);
        client.moveToState(ClientState.IN_GAME);
        client.getGame().getPlayer(user).ifPresent(
                targetUser -> client.getGame().setTurn(new ReducedTurn(targetUser, client.getUI().getClientTurnState(ClientTurnState.BUILD, client), allowedWorkers, workerBlockBuildableCells, workerDomeBuildableCells, isSkippable))
        );
        client.requestRender();
        return true;
    }
}
