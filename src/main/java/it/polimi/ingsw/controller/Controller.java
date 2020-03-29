package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.playercommands.PlayerCommand;
import it.polimi.ingsw.model.playercommands.PlayerBuildCommand;
import it.polimi.ingsw.model.playercommands.PlayerMoveCommand;
import it.polimi.ingsw.model.playercommands.PlayerSkipCommand;
import it.polimi.ingsw.observer.Observer;

public class Controller implements Observer<PlayerCommand> {
    private Game model;

    public Controller(Game model){
        this.model = model;
    }

    public void update(PlayerCommand action){
        switch(action.getActionType()){
            case MOVE:
                dispatchMoveAction((PlayerMoveCommand)action);
                break;
            case BUILD:
                dispatchBuildAction((PlayerBuildCommand)action);
                break;
            case SKIP:
                dispatchSkipAction((PlayerSkipCommand)action);
                break;
            default:
                throw new IllegalArgumentException("Command does not exist");
        }
    }

    private void dispatchBuildAction(PlayerBuildCommand action){

    }

    private void dispatchMoveAction(PlayerMoveCommand action){

    }

    private void dispatchSkipAction(PlayerSkipCommand action){

    }
}
