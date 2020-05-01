package it.polimi.ingsw.client.clientstates;

/**
 * The client states.
 */
public enum ClientState {
    /**
     * Ask god from list client state.
     */
    ASK_GOD_FROM_LIST,
    /**
     * Ask gods from list client state.
     */
    ASK_GODS_FROM_LIST,
    /**
     * Ask start player client state.
     */
    ASK_START_PLAYER,
    /**
     * Ask worker position client state.
     */
    ASK_WORKER_POSITION,
    /**
     * Connect to server client state.
     */
    CONNECT_TO_SERVER,
    /**
     * Set nickname client state.
     */
    SET_NICKNAME,
    /**
     * Join lobby client state.
     */
    JOIN_LOBBY,
    /**
     * Set players count client state.
     */
    SET_PLAYERS_COUNT,
    /**
     * Wait players client state.
     */
    WAIT_PLAYERS,
    /**
     * Show game passive client state.
     */
    SHOW_GAME_PASSIVE,
    /**
     * Disconnect client state.
     */
    DISCONNECT,
    ;
}
