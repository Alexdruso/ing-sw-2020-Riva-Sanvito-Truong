package it.polimi.ingsw.utils.networking.transmittables.servermessages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.clientstates.ClientTurnState;
import it.polimi.ingsw.client.reducedmodel.ReducedTurn;
import it.polimi.ingsw.utils.networking.ClientHandleable;
import it.polimi.ingsw.utils.networking.transmittables.ReducedTargetCells;
import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;
import it.polimi.ingsw.utils.networking.transmittables.ReducedWorkerID;

/**
 * The type Server ask worker position message.
 */
public class ServerAskWorkerPositionMessage implements ServerMessage, ClientHandleable {
    /**
     * The Worker whose position has to be set.
     */
    public final ReducedWorkerID worker;
    /**
     * The User who has to choose the worker's position.
     */
    public final ReducedUser user;
    /**
     * The available cells.
     */
    public final ReducedTargetCells targetCells;

    /**
     * Instantiates a new Server ask worker position message.
     *
     * @param worker      the worker
     * @param user        the user
     * @param targetCells the target cells
     */
    public ServerAskWorkerPositionMessage(ReducedWorkerID worker, ReducedUser user, ReducedTargetCells targetCells) {
        this.worker = worker;
        this.user = user;
        this.targetCells = targetCells;
    }

    @Override
    public boolean handleTransmittable(Client client) {
        client.setCurrentActiveUser(user);
        client.moveToState(ClientState.IN_GAME);
        client.getGame().getPlayer(user).ifPresent(
                targetUser -> client.getGame().setTurn(new ReducedTurn(targetUser, client.getUI().getClientTurnState(ClientTurnState.ASK_WORKER_POSITION, client)))
        );
        client.requestRender();
        return true;
    }
}
