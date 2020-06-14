package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;

/**
 * A utility class that represents a user connected to the server.
 * It is also used by the Model to reference a Player outside of it.
 *
 * @see it.polimi.ingsw.server.model.Player
 */
public class User {
    public final String nickname;

    /**
     * Instantiates a new User.
     *
     * @param nickname the nickname
     */
    public User(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Converts a User into a ReducedUser
     *
     * @return the reduced user
     */
    public ReducedUser toReducedUser() {
        return new ReducedUser(nickname);
    }
}
