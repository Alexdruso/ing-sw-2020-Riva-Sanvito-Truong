package it.polimi.ingsw.utils.networking.transmittables.servermessages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.utils.networking.ClientHandleable;
import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;

/**
 * The type Server ask start player message.
 */
public class ServerAskStartPlayerMessage implements ServerMessage, ClientHandleable {
    /**
     * The User who has to decide who starts.
     */
    ReducedUser user;

    /**
     * Instantiates a new Server ask start player message.
     *
     * @param user the user
     */
    public ServerAskStartPlayerMessage(ReducedUser user) {
        this.user = user;
    }

    @Override
    public boolean handleTransmittable(Client client) {
        client.setCurrentActiveUser(user);
        client.moveToState(ClientState.ASK_START_PLAYER);
        return true;
    }
}
