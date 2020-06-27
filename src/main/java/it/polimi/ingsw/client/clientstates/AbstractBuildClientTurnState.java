package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import it.polimi.ingsw.utils.networking.transmittables.ReducedComponent;
import it.polimi.ingsw.utils.networking.transmittables.ReducedWorkerID;
import it.polimi.ingsw.utils.networking.transmittables.clientmessages.ClientBuildMessage;
import it.polimi.ingsw.utils.networking.transmittables.clientmessages.ClientSkipMessage;

/**
 * A generic BUILD client turn state, to be extended by a UI-specific class.
 */
public abstract class AbstractBuildClientTurnState extends AbstractClientTurnState {
    protected int targetCellX;
    protected int targetCellY;
    protected ReducedComponent component;
    protected int builtLevel;
    protected ReducedWorkerID workerID = null;

    /**
     * Initializes the turn state.
     *
     * @param client the reference to the Client
     */
    public AbstractBuildClientTurnState(Client client) {
        super(client);
    }

    @Override
    public void notifyUiInteraction() {
        if (workerID != null) {
            client.getConnection().send(new ClientBuildMessage(targetCellX, targetCellY, component, builtLevel, workerID));
        }
        else {
            client.getConnection().send(new ClientSkipMessage());
        }
    }

    @Override
    public void handleClientError() {
        client.getUI().notifyError(I18n.string(I18nKey.YOU_CANT_BUILD_IN_THE_SPECIFIED_CELL));
    }

}
