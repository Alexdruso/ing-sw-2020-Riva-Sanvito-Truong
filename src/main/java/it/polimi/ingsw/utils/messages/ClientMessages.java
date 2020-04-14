package it.polimi.ingsw.utils.messages;

/**
 * This enum represents all possible commands of which the player can request the execution
 */
public enum ClientMessages {
    /**
     * A building command
     */
    BUILD,
    /**
     * A choose god command
     */
    CHOOSE_GOD,
    /**
     * A choose gods command
     */
    CHOOSE_GODS,
    /**
     * A disconnect command
     */
    DISCONNECT,
    /**
     * A movement command
     */
    MOVE,
    /**
     * A join lobby command
     */
    JOIN_LOBBY,
    /**
     * A set nickname command
     */
    SET_NICKNAME,
    /**
     * A set players count command
     */
    SET_PLAYERS_COUNT,
    /**
     * A set workers position command
     */
    SET_WORKER_POSITION,
    /**
     * A set worker starting position command
     */
    SET_WORKER_START_POSITION,
    /**
     * A skip command
     */
    SKIP,
}
