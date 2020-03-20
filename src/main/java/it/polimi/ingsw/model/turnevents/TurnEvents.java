package it.polimi.ingsw.model.turnevents;

//TODO: define and implement signature of methods
/**
 * This class defines the hooks the gods can attach to in order to modify the default game behavior.
 */
public class TurnEvents {
    /**
     * Allows a god to perform custom actions when a new turn starts for a player.
     * By default (if not overridden by a subclass), no custom action is performed.
     */
    protected void onTurnStart() {}

    /**
     * Allows a god to perform custom actions before the game allows the player to perform the move action.
     * By default (if not overridden by a subclass), no custom action is performed.
     */
    protected void onBeforeMovement() {}

    /**
     * Allows a god to perform custom actions after the player has performed the move action.
     * By default (if not overridden by a subclass), no custom action is performed.
     */
    protected void onAfterMovement() {}

    /**
     * Allows a god to perform custom actions before the game allows the player to perform the build action.
     * By default (if not overridden by a subclass), no custom action is performed.
     */
    protected void onBeforeBuild() {}

    /**
     * Allows a god to perform custom actions after the player has performed the move action.
     * By default (if not overridden by a subclass), no custom action is performed.
     */
    protected void onAfterBuild() {}

    /**
     * Allows a god to perform custom actions when a turn ends for a player.
     * By default (if not overridden by a subclass), no custom action is performed.
     */
    protected void onTurnEnd() {}

    /**
     * Allows a god to perform custom actions when computing the win conditions for a player.
     * By default (if not overridden by a subclass), no custom win condition is applied.
     */
    protected void computeWinCondition() {}
}
