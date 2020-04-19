package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.utils.messages.ClientBuildMessage;
import it.polimi.ingsw.utils.messages.ClientMoveMessage;
import it.polimi.ingsw.utils.messages.ClientSkipMessage;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.StatusMessages;
import it.polimi.ingsw.utils.networking.Transmittable;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.ViewClientMessage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class represents the Controller of the MVC Architectural pattern. It observes the View and gets
 * notified whenever the user submits an input in order to handle it.
 */
public class Controller implements Observer<ViewClientMessage> {
    /**
     * The reference to the Model
     */
    private Game model;

    /**
     * The queue containing the messages to be processed.
     */
    private BlockingQueue<ViewClientMessage> processingQueue = new LinkedBlockingQueue<ViewClientMessage>();

    /**
     * The class constructor
     *
     * @param model the Model that is to be bound to the Controller
     */
    public Controller(Game model){
        this.model = model;
    }

    /**
     * This method takes a ViewClientMessage from the view and adds it to the processing queue.
     *
     * @param action the action the view is commanding
     */
    public void update(ViewClientMessage action){
        try {
            this.processingQueue.put(action);
        }catch(InterruptedException ignored){
            Thread.currentThread().interrupt();
        }
    }

    /**
     * This method is called continuously by the Match thread.
     * It selects the appropriate dispatcher in order to handle the requested action.
     */
    public void dispatchViewClientMessages(){
        try {
            ViewClientMessage action = this.processingQueue.take();
            switch(action.clientMessage.getMessageType()){
                case MOVE:
                    dispatchMoveAction((ClientMoveMessage)action.clientMessage, action.view, action.user);
                    break;
                case BUILD:
                    dispatchBuildAction((ClientBuildMessage)action.clientMessage, action.view, action.user);
                    break;
                case SKIP:
                    dispatchSkipAction((ClientSkipMessage)action.clientMessage, action.view, action.user);
                    break;
                default:
                    action.view.handleMessage(StatusMessages.CLIENT_ERROR);
            }
        }catch(InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * This method handles building actions.
     *
     * @param action the PlayerBuildCommand that has been requested
     * @param view   the View that triggered this command
     * @param user   the User that triggered this command
     */
    private void dispatchBuildAction(ClientBuildMessage action, View view, User user){
        if(model.isValidBuild(action, user)) {
            model.build(action, user);
        } else {
            view.handleMessage(StatusMessages.CLIENT_ERROR);
        }
    }

    /**
     * This method handles movement actions

     * @param action the PlayerMoveCommand that has been requested
     * @param view   the View that triggered this command
     * @param user   the User that triggered this command
     */
    private void dispatchMoveAction(ClientMoveMessage action, View view, User user){
        if(model.isValidMove(action, user)){
            model.move(action, user);
        } else {
            view.handleMessage(StatusMessages.CLIENT_ERROR);
        }
    }

    /**
     * This method handles skip actions
     * 
     * @param action the PlayerSkipCommand that has been requested
     * @param view   the View that triggered this command
     * @param user   the User that triggered this command
     */
    private void dispatchSkipAction(ClientSkipMessage action, View view, User user){
        if(model.isValidSkip(action, user)){
            model.skip(action, user);
        } else {
            view.handleMessage(StatusMessages.CLIENT_ERROR);
        }
    }


}
