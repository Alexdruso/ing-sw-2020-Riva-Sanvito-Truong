package it.polimi.ingsw.utils.networking.transmittables.servermessages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.networking.ClientHandleable;
import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;

/**
 * The type Server start setup match message.
 */
public class ServerStartSetupMatchMessage implements ServerMessage, ClientHandleable {
    /**
     * The list of users participating into the game.
     */
    public final ReducedUser[] userList;

    /**
     * Instantiates a new Server start setup match message.
     *
     * @param userList the user list
     */
    public ServerStartSetupMatchMessage(ReducedUser[] userList) {
        super();
        this.userList = userList;
    }

    @Override
    public boolean handleTransmittable(Client client) {
        client.createGame(userList);
        return true;
    }
}
