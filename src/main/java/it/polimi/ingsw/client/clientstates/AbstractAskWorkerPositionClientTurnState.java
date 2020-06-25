package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import it.polimi.ingsw.utils.networking.transmittables.ReducedWorkerID;
import it.polimi.ingsw.utils.networking.transmittables.clientmessages.ClientSetWorkerStartPositionMessage;

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
                player -> {
                    ReducedWorkerID[] reducedWorkerIDS = ReducedWorkerID.values();
                    int workerIDIndex = player.getWorkers().size();
                    if (workerIDIndex < reducedWorkerIDS.length) {
                        workerID = reducedWorkerIDS[workerIDIndex];
                        client.getConnection().send(new ClientSetWorkerStartPositionMessage(targetCellX, targetCellY, workerID));
                    }
                }
        );
    }

    @Override
    public void handleClientError() {
        client.getUI().notifyError(I18n.string(I18nKey.YOU_CANT_PLACE_THE_WORKER_IN_THE_SPECIFIED_CELL));
    }
}
