package it.polimi.ingsw.utils.messages;

import java.io.Serializable;

public class ReducedUser implements Serializable {
    public final String nickname;

    public ReducedUser(String nickname) {
        this.nickname = nickname;
    }
}
