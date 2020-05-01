package it.polimi.ingsw.utils.messages;

import java.io.Serializable;
import java.util.Objects;

public class ReducedUser implements Serializable {
    public final String nickname;

    public ReducedUser(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReducedUser that = (ReducedUser) o;
        return nickname.equals(that.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname);
    }
}
