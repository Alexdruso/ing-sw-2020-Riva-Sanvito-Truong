package it.polimi.ingsw.utils.messages;

import java.io.Serializable;
import java.util.Objects;

public class ReducedGod implements Serializable {
    public final String name;

    public ReducedGod(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReducedGod that = (ReducedGod) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
