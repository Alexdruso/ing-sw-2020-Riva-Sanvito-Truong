package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.workers.WorkerID;
import it.polimi.ingsw.utils.networking.ClientHandleable;

import java.util.HashMap;
import java.util.List;

public class ServerAskBuildMessage implements ServerMessage, ClientHandleable {
    public final ReducedUser user;
    public final boolean isSkippable;
    public final List<WorkerID> allowedWorkers;
    public final HashMap<WorkerID, ReducedTargetCells> workerBlockBuildableCells;
    public final HashMap<WorkerID, ReducedTargetCells> workerDomeBuildableCells;

    public ServerAskBuildMessage(ReducedUser user, boolean isSkippable, List<WorkerID> allowedWorkers,
                                 HashMap<WorkerID, ReducedTargetCells> workerBlockBuildableCells,
                                 HashMap<WorkerID, ReducedTargetCells> workerDomeBuildableCells) {
        this.user = user;
        this.isSkippable = isSkippable;
        this.allowedWorkers = allowedWorkers;
        this.workerBlockBuildableCells = workerBlockBuildableCells;
        this.workerDomeBuildableCells = workerDomeBuildableCells;
    }

    @Override
    public boolean handleTransmittable(Client client) {
//         if (handler.getGame().getLocalUser().equals(user)) {
//          handler.getGame().setTurnState(BUILD);
//         }
//        else {
//          handler.getGame().setTurnState(PASSIVE);
//        }

        client.requestRender();
        return false;
    }
}
