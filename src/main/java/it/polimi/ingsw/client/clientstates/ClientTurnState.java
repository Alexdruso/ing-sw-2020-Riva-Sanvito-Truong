package it.polimi.ingsw.client.clientstates;

/**
 * The enum of valid client turn states.
 * A client turn state represents a phase of a turn of the server.
 */
public enum ClientTurnState {
    /**
     * Ask worker position client turn state.
     */
    ASK_WORKER_POSITION,
    /**
     * Build client turn state.
     */
    BUILD,
    /**
     * Move client turn state.
     */
    MOVE,
}
