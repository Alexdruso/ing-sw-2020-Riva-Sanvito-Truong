package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;
import it.polimi.ingsw.utils.networking.transmittables.clientmessages.ClientSetStartPlayerMessage;

/**
 * A generic ASK_START_PLAYER client state, to be extended by a UI-specific class.
 */
public abstract class AbstractAskStartPlayerClientState extends AbstractClientState{
    protected ReducedUser chosenUser;

    /**
     * Instantiates a new ASK_START_PLAYER ClientState.
     *
     * @param client the client
     */
    public AbstractAskStartPlayerClientState(Client client) {
        super(client);
    }

    @Override
    public void setup() {
        triggerRender();
    }

    @Override
    public void notifyUiInteraction() {
        client.getConnection().send(new ClientSetStartPlayerMessage(chosenUser));
    }
}
