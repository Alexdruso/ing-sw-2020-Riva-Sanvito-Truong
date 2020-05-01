package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.messages.ClientChooseGodMessage;
import it.polimi.ingsw.utils.messages.ClientChooseGodsMessage;
import it.polimi.ingsw.utils.messages.ReducedGod;

import java.util.ArrayList;
import java.util.List;

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
