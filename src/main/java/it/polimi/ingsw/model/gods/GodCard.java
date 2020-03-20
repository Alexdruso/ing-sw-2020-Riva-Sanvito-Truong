package it.polimi.ingsw.model.gods;

/**
 * The enum containing all the gods.
 * All gods of the game are instantiated as values of the enum GodCard; for this reason, it is guaranteed the existence of exactly 1 instance for each god at runtime.
 */
public enum GodCard {
    /**
     * The Apollo god.
     *
     * @see Apollo
     */
    APOLLO(new Apollo()),
    /**
     * The Artemis god.
     *
     * @see Artemis
     */
    ARTEMIS(new Artemis()),
    /**
     * The Athena god.
     *
     * @see Athena
     */
    ATHENA(new Athena()),
    /**
     * The Atlas god.
     *
     * @see Atlas
     */
    ATLAS(new Atlas()),
    /**
     * The Demeter god.
     *
     * @see Demeter
     */
    DEMETER(new Demeter()),
    /**
     * The Hephaestus god.
     *
     * @see Hephaestus
     */
    HEPHAESTUS(new Hephaestus()),
    /**
     * The Minotaur god.
     *
     * @see Minotaur
     */
    MINOTAUR(new Minotaur()),
    /**
     * The Pan god.
     *
     * @see Pan
     */
    PAN(new Pan()),
    /**
     * The Prometheus god.
     *
     * @see Prometheus
     */
    PROMETHEUS(new Prometheus()),
    ;

    private final God god;

    GodCard(God god) {
        this.god = god;
    }

    /**
     * Gets the god.
     *
     * @return the god
     */
    public God getGod() {
        return this.god;
    }
}
