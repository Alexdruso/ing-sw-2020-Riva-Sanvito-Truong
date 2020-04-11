package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.utils.messages.ClientMessage;
import it.polimi.ingsw.utils.messages.ClientBuildMessage;
import it.polimi.ingsw.utils.messages.ClientMoveMessage;
import it.polimi.ingsw.utils.messages.ClientSkipMessage;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.StatusMessages;

/**
 * This class represents the Controller of the MVC Architectural pattern. It observes the View and gets
 * notified whenever the user submits an input in order to handle it.
 */
public class Controller implements Observer<ClientMessage> {
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
    public void update(ClientMessage action){
        switch(action.getActionType()){
            case MOVE:
                dispatchMoveAction((ClientMoveMessage)action);
                break;
            case BUILD:
                dispatchBuildAction((ClientBuildMessage)action);
                break;
            case SKIP:
                dispatchSkipAction((ClientSkipMessage)action);
                break;
            default:
                action.view.handleMessage(StatusMessages.CLIENT_ERROR);
        }
    }

    /**
     * This method handles building actions
     * @param action the PlayerBuildCommand that has been requested
     */
    private void dispatchBuildAction(ClientBuildMessage action){
        if(model.isValidBuild(action)) {
            model.build(action);
        } else {
            action.view.handleMessage(StatusMessages.CLIENT_ERROR);
        }
    }

    /**
     * This method handles movement actions
     * @param action the PlayerMoveCommand that has been requested
     */
    private void dispatchMoveAction(ClientMoveMessage action){
        if(model.isValidMove(action)){
            model.move(action);
        } else {
            action.view.handleMessage(StatusMessages.CLIENT_ERROR);
        }
    }

    /**
     * This method handles skip actions
     * @param action the PlayerSkipCommand that has been requested
     */
    private void dispatchSkipAction(ClientSkipMessage action){
        if(model.isValidSkip(action)){
            model.skip(action);
        } else {
            action.view.handleMessage(StatusMessages.CLIENT_ERROR);
        }
    }
}
