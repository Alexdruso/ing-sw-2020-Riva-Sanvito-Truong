package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.networking.ClientHandleable;

import java.util.List;
import java.util.Map;

public class ServerAskBuildMessage implements ServerMessage, ClientHandleable {
    public final ReducedUser user;
    public final boolean isSkippable;
    public final List<ReducedWorkerID> allowedWorkers;
    public final Map<ReducedWorkerID, ReducedTargetCells> workerBlockBuildableCells;
    public final Map<ReducedWorkerID, ReducedTargetCells> workerDomeBuildableCells;

    public ServerAskBuildMessage(ReducedUser user, boolean isSkippable, List<ReducedWorkerID> allowedWorkers,
                                 Map<ReducedWorkerID, ReducedTargetCells> workerBlockBuildableCells,
                                 Map<ReducedWorkerID, ReducedTargetCells> workerDomeBuildableCells) {
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
