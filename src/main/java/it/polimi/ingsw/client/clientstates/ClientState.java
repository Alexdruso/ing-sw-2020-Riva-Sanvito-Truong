package it.polimi.ingsw.client.clientstates;

/**
 * The client states.
 */
public enum ClientState {
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
