package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.model.workers.WorkerID;
import it.polimi.ingsw.utils.networking.ClientHandleable;

public class ServerAskWorkerPositionMessage implements ServerMessage, ClientHandleable {
    public final WorkerID worker;
    public final ReducedUser user;
    public final ReducedTargetCells targetCells;

    public ServerAskWorkerPositionMessage(WorkerID worker, ReducedUser user, ReducedTargetCells targetCells) {
        this.worker = worker;
        this.user = user;
        this.targetCells = targetCells;
    }

    @Override
    public boolean handleTransmittable(Client client) {
        client.setCurrentActiveUser(user);
        client.moveToState(ClientState.ASK_WORKER_POSITION);
        return true;
    }
}
