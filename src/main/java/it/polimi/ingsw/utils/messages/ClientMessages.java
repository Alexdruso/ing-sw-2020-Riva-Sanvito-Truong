package it.polimi.ingsw.utils.messages;

/**
 * This enum represents all possible commands of which the player can request the execution
 */
public enum ClientMessages {
    /**
     * A movement command
     */
    MOVE,
    /**
     * A building command
     */
    BUILD,
    /**
     * A set nickname command
     */
    SET_NICKNAME,
    /**
     * A skip command
     */
    SKIP,
}
