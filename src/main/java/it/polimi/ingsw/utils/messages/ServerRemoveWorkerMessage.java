package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.networking.ClientHandleable;

public class ServerRemoveWorkerMessage implements ServerMessage, ClientHandleable {
    public final ReducedUser user;
    public final ReducedWorkerID worker;
    public final int cellX;
    public final int cellY;

    public ServerRemoveWorkerMessage(ReducedUser user, ReducedWorkerID worker, int cellX, int cellY) {
        this.user = user;
        this.worker = worker;
        this.cellX = cellX;
        this.cellY = cellY;
    }

    @Override
    public boolean handleTransmittable(Client client) {
        return false;
    }
}
