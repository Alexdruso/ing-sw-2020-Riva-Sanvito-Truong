package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.clientstates.ClientTurnState;
import it.polimi.ingsw.client.reducedmodel.ReducedTurn;
import it.polimi.ingsw.utils.networking.ClientHandleable;

public class ServerAskWorkerPositionMessage implements ServerMessage, ClientHandleable {
    public final ReducedWorkerID worker;
    public final ReducedUser user;
    public final ReducedTargetCells targetCells;

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
