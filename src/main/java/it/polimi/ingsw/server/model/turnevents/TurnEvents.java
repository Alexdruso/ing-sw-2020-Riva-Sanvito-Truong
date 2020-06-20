package it.polimi.ingsw.server.model.turnevents;

import it.polimi.ingsw.server.model.Turn;

/**
 * This class defines the hooks the gods can attach to in order to modify the default game behavior.
 */
public class TurnEvents {
    /**
     * Allows a god to perform custom actions when a new turn starts for a player.
     * By default (if not overridden by a subclass), no custom action is performed.
     * @param turn the current Turn
     */
    protected void onTurnStart(Turn turn) {
        // By default (if not overridden by a subclass), no custom action is performed.
    }

    /**
     * Allows a god to perform custom actions before the game allows the player to perform the move action.
     * By default (if not overridden by a subclass), no custom action is performed.
     * @param turn the current Turn
     */
    protected void onBeforeMovement(Turn turn) {
        // By default (if not overridden by a subclass), no custom action is performed.
    }

    /**
     * Allows a god to perform custom actions after the player has performed the move action.
     * By default (if not overridden by a subclass), no custom action is performed.
     * @param turn the current Turn
     */
    protected void onAfterMovement(Turn turn) {
        // By default (if not overridden by a subclass), no custom action is performed.
    }

    /**
     * Allows a god to perform custom actions before the game allows the player to perform the build action.
     * By default (if not overridden by a subclass), no custom action is performed.
     * @param turn the current Turn
     */
    protected void onBeforeBuild(Turn turn) {
        // By default (if not overridden by a subclass), no custom action is performed.
    }

    /**
     * Allows a god to perform custom actions after the player has performed the move action.
     * By default (if not overridden by a subclass), no custom action is performed.
     * @param turn the current Turn
     */
    protected void onAfterBuild(Turn turn) {
        // By default (if not overridden by a subclass), no custom action is performed.
    }

    /**
     * Allows a god to perform custom actions when a turn ends for a player.
     * By default (if not overridden by a subclass), no custom action is performed.
     * @param turn the current Turn
     */
    protected void onTurnEnd(Turn turn) {
        // By default (if not overridden by a subclass), no custom action is performed.
    }

    /**
     * Allows a god to perform custom actions when computing the win conditions for a player.
     * By default (if not overridden by a subclass), no custom win condition is applied.
     * @param turn the current Turn
     */
    protected void computeWinCondition(Turn turn) {
        // By default (if not overridden by a subclass), no custom action is performed.
    }
}
