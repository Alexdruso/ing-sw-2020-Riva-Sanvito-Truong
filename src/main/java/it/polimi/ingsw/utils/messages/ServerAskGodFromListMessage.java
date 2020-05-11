package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.utils.networking.ClientHandleable;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Server ask god from list message.
 */
public class ServerAskGodFromListMessage implements ServerMessage, ClientHandleable {
    /**
     * The User who has to choose a god.
     */
    public final ReducedUser user;
    /**
     * The list of available gods.
     */
    private final List<ReducedGod> godsList;

    /**
     * Instantiates a new Server ask god from list message.
     *
     * @param user     the user
     * @param godsList the gods list
     */
    public ServerAskGodFromListMessage(ReducedUser user, List<ReducedGod> godsList) {
        this.user = user;
        this.godsList = godsList;
    }

    /**
     * Gets gods list.
     *
     * @return the gods list
     */
    public List<ReducedGod> getGodsList() {
        return new ArrayList<>(godsList);
    }

    @Override
    public boolean handleTransmittable(Client client) {
        client.setCurrentActiveUser(user);
        client.setGods(godsList);
        client.moveToState(ClientState.ASK_GOD_FROM_LIST);
        return true;
    }
}
