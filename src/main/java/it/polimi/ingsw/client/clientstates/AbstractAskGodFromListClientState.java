package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.networking.transmittables.ReducedGod;
import it.polimi.ingsw.utils.networking.transmittables.clientmessages.ClientChooseGodMessage;

/**
 * A generic ASK_GOD_FROM_LIST client state, to be extended by a UI-specific class.
 */
public abstract class AbstractAskGodFromListClientState extends AbstractClientState{
    protected ReducedGod chosenGod;

    /**
     * Instantiates a new ClientState.
     *
     * @param client the client
     */
    public AbstractAskGodFromListClientState(Client client) {
        super(client);
    }

    @Override
    public void setup() {
        triggerRender();
    }

    @Override
    public void notifyUiInteraction() {
        client.getConnection().send(new ClientChooseGodMessage(chosenGod));
    }
}
