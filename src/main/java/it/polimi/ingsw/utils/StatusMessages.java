package it.polimi.ingsw.utils;

/**
 * An enum to contain all possible messages that can be sent from the Controller to the View
 */
public enum StatusMessages {
    /**
     * Response message if the command requested has been executed successfully
     */
    OK,
    /**
     * Response message if the command did not match any existing possible action
     */
    NON_EXISTING_ACTION,
    /**
     * Response message if the command represented a non valid move action
     */
    MOVE_ERROR,
    /**
     * Response message if the command represented a non valid build action
     */
    BUILD_ERROR,
    /**
     * Response message if the command represented a skip on a non-skippable turn state
     */
    SKIP_ERROR,
    /**
     * Message to notify the View that the player has won the game
     */
    VICTORY,
    /**
     * Message to notify the View that the player has lost the game
     */
    LOSS,
    /**
     * Message to notify the View that the player can play its turn
     */
    YOUR_TURN,
    /**
     * Message to notify the View that the player should wait for the opponent's turn to end
     */
    OPPONENT_TURN
}
