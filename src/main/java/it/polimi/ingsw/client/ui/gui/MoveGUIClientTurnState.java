package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractMoveClientTurnState;
import it.polimi.ingsw.client.reducedmodel.*;
import it.polimi.ingsw.client.ui.gui.guicontrollers.InGameController;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import javafx.application.Platform;

/**
 * A GUI-specific MOVE client turn state.
 */
public class MoveGUIClientTurnState extends AbstractMoveClientTurnState implements GUIClientTurnState{
    private final InGameGUIClientState clientState;
    private ReducedGame game;
    private ReducedTurn turn;
    private ReducedBoard board;
    private ReducedPlayer player;
    private InGameController controller;
    private boolean sourceSelected = false;
    private boolean firstRender = true;


    /**
     * Creates a new MOVE_GUI Client Turn State
     * @param client the client
     * @param clientState the client state
     */
    public MoveGUIClientTurnState(Client client, InGameGUIClientState clientState) {
        super(client);
        this.clientState = clientState;
        this.controller = (InGameController)((GUI)client.getUI()).getCurrentScene().controller;
    }

    @Override
    public void render() {
        this.game = client.getGame();
        this.turn = game.getTurn();
        this.board = game.getBoard();
        this.player = turn.getPlayer();

        if(firstRender){
            Platform.runLater(() -> {
                controller.setLabel("");
                controller.setPrompt("");
                controller.clearSideButtons();
            });
            if (client.isCurrentlyActive() && turn.isSkippable()) {
                Platform.runLater(controller::displaySkipButton);
            }
            firstRender = false;
        }

        if (client.isCurrentlyActive()) {
            showActiveScreen();
        } else {
            showPassiveScreen();
        }
        Platform.runLater(controller::redrawBoard);
    }

    private void showActiveScreen(){
        if (turn.getAllowedWorkers().size() == 1) {
            //The worker is automatically determined since there's only one that can move
            workerID = turn.getAllowedWorkers().get(0);
            ReducedWorker worker = player.getWorker(workerID);
            ReducedCell workerCell = worker.getCell();
            sourceCellX = workerCell.getX();
            sourceCellY = workerCell.getY();
            sourceSelected = true;
            board.getTargets(turn.getWorkerWalkableCells(workerID)).forEach(
                    targetedCell -> targetedCell.setHighlighted(true)
            );
            //Ask for the target, with no possibility of canceling
            Platform.runLater(() -> {
                controller.setLabel(I18n.string(I18nKey.WHERE_DO_YOU_WANT_TO_PLACE_YOUR_WORKER));
                controller.setPrompt("");
            });
        } else {
            //Ask which worker the player wants to move
            Platform.runLater(() -> {
                controller.setLabel(I18n.string(I18nKey.WHICH_WORKER_DO_YOU_WANT_TO_MOVE));
                controller.setPrompt("");
            });
        }
        Platform.runLater(() -> {
            controller.setBoardClickableStatus(true);
            controller.redrawBoard();
        });
    }

    private void showPassiveScreen(){
        Platform.runLater(() -> {
            controller.setLabel(
                    String.format(
                            I18n.string(I18nKey.WAIT_FOR_S_TO_PERFORM_THEIR_MOVE),
                            client.getCurrentActiveUser().getNickname()
                    )
            );
            controller.setPrompt("");
            controller.setBoardClickableStatus(false);
            controller.redrawBoard();
        });
    }

    @Override
    public void selectCell(int x, int y) {
        this.game = client.getGame();
        this.turn = game.getTurn();
        this.board = game.getBoard();
        this.player = turn.getPlayer();

        if(!sourceSelected){
            //The cell selected is to select the worker
            sourceCellX = x;
            sourceCellY = y;
            ReducedCell cell = board.getCell(x, y);
            cell.getWorker().ifPresent(
                    worker -> {
                        if (worker.getPlayer().getUser().equals(client.getCurrentActiveUser())) {
                            if (turn.getAllowedWorkers().contains(worker.getWorkerID())) {
                                workerID = worker.getWorkerID();
                            }
                            else {
                                Platform.runLater(() -> client.getUI().notifyError(I18n.string(I18nKey.YOU_CANT_MOVE_THE_SPECIFIED_WORKER)));
                            }
                        }
                    }
            );
            if(workerID != null){
                //If a cell containing a correct worker has been selected, highlight available cells and ask for target
                board.getTargets(turn.getWorkerWalkableCells(workerID)).forEach(
                        targetedCell -> targetedCell.setHighlighted(true)
                );
                Platform.runLater(() -> {
                    controller.redrawBoard();
                    controller.setLabel(I18n.string(I18nKey.WHERE_DO_YOU_WANT_TO_PLACE_YOUR_WORKER));
                    controller.displayCancelButton(); //We're in the case in which we select a worker: we can cancel
                });
                sourceSelected = true;
            }
        } else {
            board.getTargets(turn.getWorkerWalkableCells(workerID)).forEach(
                    targetedCell -> targetedCell.setHighlighted(false)
            );
            targetCellX = x;
            targetCellY = y;
            clientState.notifyUiInteraction();
            sourceSelected = false;
        }
    }

    @Override
    public void skip() {
        workerID = null;
        clientState.notifyUiInteraction();
    }

    @Override
    public void cancel() {
        board.getTargets(turn.getWorkerWalkableCells(workerID)).forEach(
                targetedCell -> targetedCell.setHighlighted(false)
        );
        sourceSelected = false;
        workerID = null;
        firstRender = true;
        render();
    }

    @Override
    public void handleError() {
        sourceSelected = false;
        workerID = null;
        firstRender = true;
    }
}
