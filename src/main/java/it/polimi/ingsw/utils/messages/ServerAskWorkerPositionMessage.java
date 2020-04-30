package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.model.workers.WorkerID;
import it.polimi.ingsw.utils.networking.ClientHandleable;

public class ServerAskWorkerPositionMessage implements ServerMessage, ClientHandleable {
    public final WorkerID worker;
    public final ReducedUser user;

    public ServerAskWorkerPositionMessage(WorkerID worker, ReducedUser user){
        this.worker = worker;
        this.user = user;
    }

    @Override
    public boolean handleTransmittable(Client client) {
        return false;
    }
}
