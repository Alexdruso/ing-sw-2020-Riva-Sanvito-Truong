package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.networking.transmittables.ReducedGod;
import it.polimi.ingsw.utils.networking.transmittables.clientmessages.ClientChooseGodsMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * A generic ASK_GODS_FROM_LIST client state, to be extended by a UI-specific class.
 */
public abstract class AbstractAskGodsFromListClientState extends AbstractClientState{
    protected List<ReducedGod> chosenGods;

    /**
     * Instantiates a new ClientState.
     *
     * @param client the client
     */
    public AbstractAskGodsFromListClientState(Client client) {
        super(client);
        chosenGods = new ArrayList<>();
    }

    @Override
    public void setup() {
        triggerRender();
    }

    @Override
    public void notifyUiInteraction() {
        client.getConnection().send(new ClientChooseGodsMessage(chosenGods));
    }
}
