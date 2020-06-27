package it.polimi.ingsw.utils.networking.transmittables;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Reduced god.
 */
public class ReducedGod implements Serializable {
    /**
     * The name of the god.
     */
    private final String name;

    /**
     * Instantiates a new Reduced god.
     *
     * @param name the name
     */
    public ReducedGod(String name) {
        this.name = name;
    }

    /**
     * Instantiates a new Reduced god.
     *
     * @param that the other god
     */
    public ReducedGod(ReducedGod that) {
        name = that.name;
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

    /**
     * This method returns the name of the ReducedGod
     * @return
     */
    public String getName() {
        return name;
    }
}
