package it.polimi.ingsw.server.model.turnevents;

//TODO: define and implement signature of methods

import it.polimi.ingsw.server.model.Turn;

/**
 * This class defines the hooks the gods can attach to in order to modify the default game behavior.
 */
public class TurnEvents {
    /**
     * Allows a god to perform custom actions when a new turn starts for a player.
     * By default (if not overridden by a subclass), no custom action is performed.
     * @param turn
     */
    protected void onTurnStart(Turn turn) {}

    /**
     * Allows a god to perform custom actions before the game allows the player to perform the move action.
     * By default (if not overridden by a subclass), no custom action is performed.
     * @param turn
     */
    protected void onBeforeMovement(Turn turn) {}

    /**
     * Allows a god to perform custom actions after the player has performed the move action.
     * By default (if not overridden by a subclass), no custom action is performed.
     * @param turn
     */
    protected void onAfterMovement(Turn turn) {}

    /**
     * Allows a god to perform custom actions before the game allows the player to perform the build action.
     * By default (if not overridden by a subclass), no custom action is performed.
     * @param turn
     */
    protected void onBeforeBuild(Turn turn) {}

    /**
     * Allows a god to perform custom actions after the player has performed the move action.
     * By default (if not overridden by a subclass), no custom action is performed.
     * @param turn
     */
    protected void onAfterBuild(Turn turn) {}

    /**
     * Allows a god to perform custom actions when a turn ends for a player.
     * By default (if not overridden by a subclass), no custom action is performed.
     * @param turn
     */
    protected void onTurnEnd(Turn turn) {}

    /**
     * Allows a god to perform custom actions when computing the win conditions for a player.
     * By default (if not overridden by a subclass), no custom win condition is applied.
     * @param turn
     */
    protected void computeWinCondition(Turn turn) {}
}
