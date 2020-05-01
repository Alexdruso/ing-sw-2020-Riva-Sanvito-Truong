package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.messages.ClientChooseGodsMessage;
import it.polimi.ingsw.utils.messages.ReducedGod;

import java.util.ArrayList;
import java.util.List;

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
