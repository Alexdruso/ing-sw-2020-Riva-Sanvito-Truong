package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.messages.ClientSetWorkerStartPositionMessage;
import it.polimi.ingsw.utils.messages.ReducedWorkerID;

public abstract class AbstractAskWorkerPositionClientTurnState extends AbstractClientTurnState {
    protected int targetCellX;
    protected int targetCellY;
    protected ReducedWorkerID workerID;

    public AbstractAskWorkerPositionClientTurnState(Client client) {
        super(client);
    }

    @Override
    public void notifyUiInteraction() {
        client.getGame().getPlayer(client.getNickname()).ifPresent(
                player -> workerID = ReducedWorkerID.values()[player.getWorkers().size()]
        );
        client.getConnection().send(new ClientSetWorkerStartPositionMessage(targetCellX, targetCellY, workerID));
    }
}
