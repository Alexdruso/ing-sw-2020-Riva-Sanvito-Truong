package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.networking.ClientHandleable;

/**
 * The type Server set god message.
 */
public class ServerSetGodMessage implements ServerMessage, ClientHandleable {
    /**
     * The God.
     */
    public final ReducedGod god;
    /**
     * The User who has chosen the god.
     */
    public final ReducedUser user;

    /**
     * Instantiates a new Server set god message.
     *
     * @param god  the god
     * @param user the user
     */
    public ServerSetGodMessage(ReducedGod god, ReducedUser user) {
        this.god = god;
        this.user = user;
    }

    @Override
    public boolean handleTransmittable(Client client) {
        client.getGame().getPlayer(user).ifPresent(
                player -> player.setGod(god)
        );
        return true;
    }
}
