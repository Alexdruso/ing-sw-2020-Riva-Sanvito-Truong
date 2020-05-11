package it.polimi.ingsw.controller;

import it.polimi.ingsw.utils.messages.ReducedUser;

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
