package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.workers.WorkerID;
import it.polimi.ingsw.observer.LambdaObserver;
import it.polimi.ingsw.utils.StatusMessages;
import it.polimi.ingsw.utils.messages.*;
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
    private final Game model;

    /**
     * The queue containing the messages to be processed.
     */
    private final BlockingQueue<ViewClientMessage> processingQueue = new LinkedBlockingQueue<>();

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
    public void dispatchViewClientMessages() {
        try {
            ViewClientMessage message = this.processingQueue.take();
            ControllerHandleable handleable = (ControllerHandleable) message.clientMessage;
            handleable.handleTransmittable(this, message.view, message.user);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * This method handles the draw action
     *
     * @param view the View that triggered this command
     * @param user the User that triggered this command
     */
    public void dispatchDrawAction(View view, User user) {
        view.removeObserver(this);
        model.removeObserver(view);

        if (model.isInGame(user)) {
            model.draw();
        }
    }

    /**
     * This method handles the choose gods action.
     *
     * @param action the ClientChooseGodsMessage requested
     * @param view   the view that triggered this command
     * @param user   the user that triggered this command
     */
    public void dispatchChooseGodsAction(ClientChooseGodsMessage action, View view, User user) {
        boolean isValidGodsChoice = model.isValidGodsChoice(action.getGods(), user);

        if (isValidGodsChoice) {
            model.setAvailableGodsList(action.getGods());
        } else {
            view.handleMessage(StatusMessages.CLIENT_ERROR);
        }
    }

    /**
     * This method handles the choose god action
     *
     * @param action the ClientChooseGodMessage requested
     * @param view   the view that triggered this command
     * @param user   the user that triggered this command
     */
    public void dispatchChooseGodAction(ClientChooseGodMessage action, View view, User user) {
        boolean isValidGodChoice = model.isValidGodChoice(action.getGod(), user);

        if (isValidGodChoice) {
            model.setGod(action.getGod(), user);
        } else {
            view.handleMessage(StatusMessages.CLIENT_ERROR);
        }
    }

    /**
     * This method handles the set start player action
     *
     * @param action the ClientSetStartPlayerMessage requested
     * @param view   the view that triggered this command
     * @param user   the user that triggered this command
     */
    public void dispatchSetStartPlayerAction(ClientSetStartPlayerMessage action, View view, User user) {
        boolean isValidStartPlayerChoice = model.isValidStartPlayerChoice(action.startPlayer, user);

        if (isValidStartPlayerChoice) {
            model.setStartPlayer(action.startPlayer);
        } else {
            view.handleMessage(StatusMessages.CLIENT_ERROR);
        }
    }

    /**
     * This method handles the set worker start position requested
     *
     * @param action the ClientSetWorkerStartPositionMessage action
     * @param view   the view that triggered this command
     * @param user   the user that triggered this command
     */
    public void dispatchSetWorkerStartPositionAction(ClientSetWorkerStartPositionMessage action, View view, User user) {
        WorkerID workerID = WorkerID.fromReducedWorkerId(action.workerID);
        boolean isValidPositioning =
                workerID != null
                && model.isValidPositioning(action.targetCellX, action.targetCellY, workerID, user);

        if (isValidPositioning) {
            model.setWorkerPosition(action.targetCellX, action.targetCellY, workerID, user);
        } else {
            view.handleMessage(StatusMessages.CLIENT_ERROR);
        }
    }


    /**
     * This method handles building actions.
     *
     * @param action the PlayerBuildCommand that has been requested
     * @param view   the View that triggered this command
     * @param user   the User that triggered this command
     */
    public void dispatchBuildAction(ClientBuildMessage action, View view, User user) {
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
            model.skip();
        } else {
            view.handleMessage(StatusMessages.CLIENT_ERROR);
        }
    }

}
