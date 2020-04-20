package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.model.workers.WorkerID;
import it.polimi.ingsw.server.Match;
import it.polimi.ingsw.utils.networking.ClientHandleable;
import it.polimi.ingsw.utils.networking.TransmittableHandler;

public class ServerAskWorkerPositionMessage extends ServerMessage implements ClientHandleable {

    public final WorkerID worker;
    public final User user;
    public ServerAskWorkerPositionMessage(WorkerID worker, User user){
        super();
        this.worker = worker;
        this.user = user;
    }

    @Override
    public boolean handleTransmittable(Client handler) {
        return false;
    }

    /**
     * This method returns the type of the current action
     *
     * @return the type of the current action, as an instance of PlayerActions
     */
    @Override
    public ServerMessages getMessageType() {
        return ServerMessages.ASK_WORKER_POSITION;
    }
}
