package it.polimi.ingsw.utils.networking.transmittables;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Reduced user.
 */
public class ReducedUser implements Serializable {
    /**
     * The Nickname.
     */
    private final String nickname;

    /**
     * Instantiates a new Reduced user.
     *
     * @param nickname the nickname
     */
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

    /**
     * Gets nickname.
     *
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }
}
