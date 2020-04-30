package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.model.board.TargetCells;
import it.polimi.ingsw.model.workers.WorkerID;
import it.polimi.ingsw.utils.networking.ClientHandleable;

import java.util.HashMap;
import java.util.List;

public class ServerAskBuildMessage implements ServerMessage, ClientHandleable {
    public final User user;
    public final boolean isSkippable;
    public final List<WorkerID> allowedWorkers;
    public final HashMap<WorkerID, TargetCells> workerBlockBuildableCells;
    public final HashMap<WorkerID, TargetCells> workerDomeBuildableCells;

    public ServerAskBuildMessage(User user, boolean isSkippable, List<WorkerID> allowedWorkers, HashMap<WorkerID, TargetCells> workerBlockBuildableCells, HashMap<WorkerID, TargetCells> workerDomeBuildableCells){
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
