package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.networking.ClientHandleable;

/**
 * The type Server lose game message.
 */
public class ServerLoseGameMessage implements ServerMessage, ClientHandleable {
    /**
     * The User who lost the game.
     */
    public final ReducedUser user;

    /**
     * Instantiates a new Server lose game message.
     *
     * @param user the user
     */
    public ServerLoseGameMessage(ReducedUser user) {
        this.user = user;
    }

    @Override
    public boolean handleTransmittable(Client client) {
        return false;
    }
}
