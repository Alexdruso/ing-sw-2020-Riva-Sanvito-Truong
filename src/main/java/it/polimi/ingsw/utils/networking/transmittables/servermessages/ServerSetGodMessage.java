package it.polimi.ingsw.utils.networking.transmittables.servermessages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.networking.ClientHandleable;
import it.polimi.ingsw.utils.networking.transmittables.ReducedGod;
import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;

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
     * @param user the user
     * @param god  the god
     */
    public ServerSetGodMessage(ReducedUser user, ReducedGod god) {
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
