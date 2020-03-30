package it.polimi.ingsw.utils.playercommands;

public class PlayerSkipCommand extends PlayerCommand {
    /**
     * This method returns the type of the current action
     * @return the type of the current action, as an instance of PlayerActions
     */
    @Override
    public PlayerCommands getActionType() {
        return PlayerCommands.SKIP;
    }
}
