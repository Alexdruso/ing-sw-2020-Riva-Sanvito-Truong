package it.polimi.ingsw.controller;

import java.io.Serializable;

public class User implements Serializable {
    public final String nickname;

    public User(String nickname){
        this.nickname = nickname;
    }
}
