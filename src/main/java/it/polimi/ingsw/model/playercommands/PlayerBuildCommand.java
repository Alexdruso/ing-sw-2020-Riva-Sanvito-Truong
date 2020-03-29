package it.polimi.ingsw.model.playercommands;

public class PlayerBuildCommand extends PlayerCommand {

    /**
     * This method returns the type of the current action
     * @return the type of the current action, as an instance of PlayerActions
     */
    @Override
    public PlayerCommands getActionType() {
        return PlayerCommands.BUILD;
    }
}
