package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.observer.LambdaObserver;
import it.polimi.ingsw.utils.StatusMessages;
import it.polimi.ingsw.utils.messages.ClientBuildMessage;
import it.polimi.ingsw.utils.messages.ClientMoveMessage;
import it.polimi.ingsw.utils.networking.ControllerHandleable;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.ViewClientMessage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class represents the Controller of the MVC Architectural pattern. It observes the View and gets
 * notified whenever the user submits an input in order to handle it.
 */
public class Controller implements LambdaObserver {
    /**
     * The reference to the Model
     */
    private Game model;

    /**
     * The queue containing the messages to be processed.
     */
    private BlockingQueue<ViewClientMessage> processingQueue = new LinkedBlockingQueue<>();

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
            ViewClientMessage message = this.processingQueue.take();
            ControllerHandleable handleable = (ControllerHandleable) message.clientMessage;
            handleable.handleTransmittable(this, message.view, message.user);
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
    public void dispatchBuildAction(ClientBuildMessage action, View view, User user){
        boolean isValidBuild = model.isValidBuild(
                action.targetCellX, action.targetCellY,
                action.component, action.performer,
                user);

        if (isValidBuild) {
            model.build(
                    action.targetCellX, action.targetCellY,
                    action.component, action.performer,
                    user);
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
    public void dispatchMoveAction(ClientMoveMessage action, View view, User user) {
        boolean isValidMove = model.isValidMove(
                action.sourceCellX, action.sourceCellY,
                action.targetCellX, action.targetCellY,
                action.performer, user);

        if (isValidMove) {
            model.move(
                    action.targetCellX, action.targetCellY,
                    action.performer, user);
        } else {
            view.handleMessage(StatusMessages.CLIENT_ERROR);
        }
    }

    /**
     * This method handles skip actions
     *
     * @param view the View that triggered this command
     * @param user the User that triggered this command
     */
    public void dispatchSkipAction(View view, User user) {
        if (model.isValidSkip(user)) {
            model.skip(user);
        } else {
            view.handleMessage(StatusMessages.CLIENT_ERROR);
        }
    }

    public void dispatchDrawAction(View view, User user) {
        //TODO: discuss how to close view connection
        model.draw();
    }


}
