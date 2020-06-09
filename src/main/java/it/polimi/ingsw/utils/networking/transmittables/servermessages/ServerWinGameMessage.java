package it.polimi.ingsw.utils.networking.transmittables.servermessages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.utils.networking.ClientHandleable;
import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;

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
        client.setCurrentActiveUser(user);
        client.moveToState(ClientState.WIN_GAME);
        return true;
    }
}
