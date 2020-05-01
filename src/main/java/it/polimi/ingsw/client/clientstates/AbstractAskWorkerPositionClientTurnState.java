package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.messages.ReducedWorkerID;

public abstract class AbstractAskWorkerPositionClientTurnState extends AbstractClientTurnState {
    protected int targetCellX;
    protected int targetCellY;
    protected ReducedWorkerID workerID;

    public AbstractAskWorkerPositionClientTurnState(Client client) {
        super(client);
    }
}
