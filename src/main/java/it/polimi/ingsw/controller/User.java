package it.polimi.ingsw.controller;

import it.polimi.ingsw.utils.messages.ReducedUser;

public class User {
    public final String nickname;

    public User(String nickname){
        this.nickname = nickname;
    }

    public ReducedUser toReducedUser() {
        return new ReducedUser(nickname);
    }
}
