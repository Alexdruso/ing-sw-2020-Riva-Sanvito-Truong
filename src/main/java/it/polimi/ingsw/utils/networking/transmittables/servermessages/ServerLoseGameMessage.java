package it.polimi.ingsw.utils.networking.transmittables.servermessages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.utils.networking.ClientHandleable;
import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;

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
        client.getGame().getPlayer(user).ifPresent(
                player -> player.setInGame(false)
        );
        client.setCurrentActiveUser(user);
        client.moveToState(ClientState.LOSE_GAME);
        return true;
    }
}
