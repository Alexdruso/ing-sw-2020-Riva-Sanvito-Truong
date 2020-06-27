package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import it.polimi.ingsw.utils.networking.transmittables.ReducedWorkerID;
import it.polimi.ingsw.utils.networking.transmittables.clientmessages.ClientMoveMessage;
import it.polimi.ingsw.utils.networking.transmittables.clientmessages.ClientSkipMessage;

/**
 * A generic MOVE client turn state, to be extended by a UI-specific class.
 */
public abstract class AbstractMoveClientTurnState extends AbstractClientTurnState {
    protected int sourceCellX;
    protected int sourceCellY;
    protected int targetCellX;
    protected int targetCellY;
    protected ReducedWorkerID workerID = null;

    /**
     * Initializes the turn state.
     *
     * @param client the reference to the Client
     */
    public AbstractMoveClientTurnState(Client client) {
        super(client);
    }

    @Override
    public void notifyUiInteraction() {
        if (workerID != null) {
            client.getConnection().send(new ClientMoveMessage(sourceCellX, sourceCellY, targetCellX, targetCellY, workerID));
        }
        else {
            client.getConnection().send(new ClientSkipMessage());
        }
    }

    @Override
    public void handleClientError() {
        client.getUI().notifyError(I18n.string(I18nKey.YOU_CANT_PLACE_THE_WORKER_IN_THE_SPECIFIED_CELL));
    }
}
