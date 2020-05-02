package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.workers.WorkerID;
import it.polimi.ingsw.utils.networking.ClientHandleable;

import java.util.List;
import java.util.Map;

public class ServerAskMoveMessage implements ServerMessage, ClientHandleable {
    public final ReducedUser user;
    public final boolean isSkippable;
    public final List<WorkerID> allowedWorkers;
    public final Map<WorkerID, ReducedTargetCells> workerWalkableCells;

    public ServerAskMoveMessage(ReducedUser user, boolean isSkippable, List<WorkerID> allowedWorkers,
                                Map<WorkerID, ReducedTargetCells> workerWalkableCells) {
        this.user = user;
        this.isSkippable = isSkippable;
        this.allowedWorkers = allowedWorkers;
        this.workerWalkableCells = workerWalkableCells;
    }

    @Override
    public boolean handleTransmittable(Client client) {
//        if (handler.getGame().getLocalUser().equals(user)) {
//          handler.getGame().setTurnState(MOVE);
//        }
//        else {
//          handler.getGame().setTurnState(PASSIVE);
//        }

        client.requestRender();
        return false;
    }
}
