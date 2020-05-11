package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.networking.ClientHandleable;

/**
 * The type Server win game message.
 */
public class ServerWinGameMessage implements ServerMessage, ClientHandleable {
    /**
     * The User.
     */
    public final ReducedUser user;

    /**
     * Instantiates a new Server win game message.
     *
     * @param user the user
     */
    public ServerWinGameMessage(ReducedUser user) {
        this.user = user;
    }


    @Override
    public boolean handleTransmittable(Client client) {
        return false;
    }
}
