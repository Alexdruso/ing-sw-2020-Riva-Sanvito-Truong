package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.utils.networking.ClientHandleable;

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
    public ServerStartSetupMatchMessage(User[] userList) {
        super();
        this.userList = new ReducedUser[userList.length];
        for (int i = 0; i < userList.length; i++) {
            this.userList[i] = userList[i].toReducedUser();
        }
    }

    @Override
    public boolean handleTransmittable(Client client) {
        client.createGame(userList);
        return true;
    }
}
