package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playercommands.PlayerCommand;
import it.polimi.ingsw.model.playercommands.PlayerBuildCommand;
import it.polimi.ingsw.model.playercommands.PlayerMoveCommand;
import it.polimi.ingsw.model.playercommands.PlayerSkipCommand;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.StatusMessages;

/**
 * This class represents the Controller of the MVC Architectural pattern. It observes the View and gets
 * notified whenever the user submits an input in order to handle it.
 */
public class Controller implements Observer<PlayerCommand> {
    /**
     * The reference to the Model
     */
    private Game model;

    /**
     * The class constructor
     * @param model the Model that is to be bound to the Controller
     */
    public Controller(Game model){
        this.model = model;
    }

    /**
     * This method is called whenever the view receives a user input.
     * It selects the appropriate dispatcher in order to handle the requested action.
     * @param action the PlayerCommand object that represents the requested action.
     */
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
                action.getView().handleMessage(StatusMessages.NON_EXISTING_ACTION);
        }
    }

    /**
     * This method handles building actions
     * @param action the PlayerBuildCommand that has been requested
     */
    private void dispatchBuildAction(PlayerBuildCommand action){
        if(model.isValidBuild(action)) {
            model.build(action);
        } else {
            action.getView().handleMessage(StatusMessages.BUILD_ERROR);
        }
    }

    /**
     * This method handles movement actions
     * @param action the PlayerMoveCommand that has been requested
     */
    private void dispatchMoveAction(PlayerMoveCommand action){
        Player currentPlayer = action.getPlayer();
        if(model.isValidMove(action)){
            model.move(action);
        } else {
            action.getView().handleMessage(StatusMessages.MOVE_ERROR);
        }
    }

    /**
     * This method handles skip actions
     * @param action the PlayerSkipCommand that has been requested
     */
    private void dispatchSkipAction(PlayerSkipCommand action){
        Player currentPlayer = action.getPlayer();
        if(model.isValidSkip(action)){
            model.skip(action);
        } else {
            action.getView().handleMessage(StatusMessages.SKIP_ERROR);
        }
    }
}
