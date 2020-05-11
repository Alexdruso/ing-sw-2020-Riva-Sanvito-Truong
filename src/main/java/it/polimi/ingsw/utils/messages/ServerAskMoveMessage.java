package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.clientstates.ClientTurnState;
import it.polimi.ingsw.client.reducedmodel.ReducedGame;
import it.polimi.ingsw.client.reducedmodel.ReducedTurn;
import it.polimi.ingsw.utils.networking.ClientHandleable;

import java.util.List;
import java.util.Map;

/**
 * The type Server ask move message.
 */
public class ServerAskMoveMessage implements ServerMessage, ClientHandleable {
    /**
     * The User who has to move.
     */
    public final ReducedUser user;
    /**
     * A boolean expressing if the move action can be skipped.
     */
    public final boolean isSkippable;
    /**
     * The workers allowed to move.
     */
    public final List<ReducedWorkerID> allowedWorkers;
    /**
     * The Worker walkable cells.
     */
    public final Map<ReducedWorkerID, ReducedTargetCells> workerWalkableCells;

    /**
     * Instantiates a new Server ask move message.
     *
     * @param user                the user
     * @param isSkippable         the boolean expressing if the move action can be skipped
     * @param allowedWorkers      the allowed workers
     * @param workerWalkableCells the worker walkable cells
     */
    public ServerAskMoveMessage(ReducedUser user, boolean isSkippable, List<ReducedWorkerID> allowedWorkers,
                                Map<ReducedWorkerID, ReducedTargetCells> workerWalkableCells) {
        this.user = user;
        this.isSkippable = isSkippable;
        this.allowedWorkers = allowedWorkers;
        this.workerWalkableCells = workerWalkableCells;
    }

    @Override
    public boolean handleTransmittable(Client client) {
        client.setCurrentActiveUser(user);
        client.moveToState(ClientState.IN_GAME);
        client.getGame().getPlayer(user).ifPresent(
                targetUser -> client.getGame().setTurn(new ReducedTurn(targetUser, client.getUI().getClientTurnState(ClientTurnState.MOVE, client)))
        );
        client.requestRender();
        return true;
    }
}
