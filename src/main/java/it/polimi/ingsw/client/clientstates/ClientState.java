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
     * Connect to server client state.
     */
    CONNECT_TO_SERVER,
    /**
     * Disconnect client state.
     */
    DISCONNECT,
    /**
     * In game client state.
     */
    IN_GAME,
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
     * Show a welcome screen to the player.
     */
    WELCOME_SCREEN,
    /**
     * Show the win screen to the player.
     */
    WIN_GAME,
    /**
     * Show game passive client state.
     * TODO: TO BE REMOVED
     */
    SHOW_GAME_PASSIVE,
    ;
}
