package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractBuildClientTurnState;
import it.polimi.ingsw.client.reducedmodel.ReducedBoard;
import it.polimi.ingsw.client.reducedmodel.ReducedCell;
import it.polimi.ingsw.client.reducedmodel.ReducedGame;
import it.polimi.ingsw.client.reducedmodel.ReducedTurn;
import it.polimi.ingsw.client.ui.gui.guicontrollers.InGameController;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import it.polimi.ingsw.utils.networking.transmittables.ReducedComponent;
import javafx.application.Platform;

public class BuildGUIClientTurnState extends AbstractBuildClientTurnState implements GUIClientTurnState {
    private final InGameGUIClientState clientState;
    private ReducedGame game;
    private ReducedTurn turn;
    private ReducedBoard board;
    private InGameController controller;
    private ReducedComponent selectedComponent;
    private boolean targetSelected = false;

    public BuildGUIClientTurnState(Client client, InGameGUIClientState clientState) {
        super(client);
        this.clientState = clientState;
        this.game = client.getGame();
        this.turn = game.getTurn();
        this.board = game.getBoard();
        this.controller = (InGameController)((GUI)client.getUI()).getCurrentScene().controller;
    }

    @Override
    public void render() {
        this.game = client.getGame();
        this.turn = game.getTurn();
        this.board = game.getBoard();

        Platform.runLater(() -> {
            controller.setLabel("");
            controller.setPrompt("");
            controller.clearSideButtons();
        });

        if(client.isCurrentlyActive()){
            if(turn.getAllowedWorkers().size() == 1){
                //Case in which the choice of the worker is forced: automatically set workerID
                workerID = turn.getAllowedWorkers().get(0);
                setCellHighlighting(true);
                Platform.runLater(() -> {
                    controller.setLabel(I18n.string(I18nKey.WHERE_DO_YOU_WANT_TO_BUILD));
                    controller.setBoardClickableStatus(true);
                    controller.redrawBoard();
                });
            } else {
                //Case in which we expect the player to choose a worker
                Platform.runLater(() -> {
                    controller.setLabel(I18n.string(I18nKey.WHICH_WORKER_DO_YOU_WANT_TO_USE_TO_BUILD));
                    controller.setBoardClickableStatus(true);
                    controller.redrawBoard();
                });
            }
            if (turn.isSkippable()) {
                Platform.runLater(() -> {
                    controller.clearSideButtons();
                    controller.displaySkipButton();
                });
            } else {
                Platform.runLater(() -> controller.setPrompt(""));
            }
        } else {
            //Passive screen
            Platform.runLater(() -> {
                controller.setLabel(
                        String.format(
                                I18n.string(I18nKey.WAIT_FOR_S_TO_PERFORM_THEIR_BUILD),
                                client.getCurrentActiveUser().getNickname()
                        )
                );
                controller.setPrompt(I18n.string(I18nKey.ASK_WORKER_START_POSITION_PASSIVE_PROMPT));
                controller.setBoardClickableStatus(false);
                controller.redrawBoard();
            });
        }
        Platform.runLater(() -> {
            controller.redrawBoard();
        });
    }

    @Override
    public void selectCell(int x, int y) {
        game = client.getGame();
        turn = game.getTurn();
        board = game.getBoard();

        if(workerID == null) {
            //Infer that the selected cell is to choose a worker
            ReducedCell sourceCell = board.getCell(x, y);
            sourceCell.getWorker().ifPresent(
                    worker -> {
                        if (worker.getPlayer().getUser().equals(client.getCurrentActiveUser())) {
                            workerID = worker.getWorkerID();

                            setCellHighlighting(true);
                            Platform.runLater(() -> {
                                controller.redrawBoard();
                                controller.setLabel(I18n.string(I18nKey.WHERE_DO_YOU_WANT_TO_BUILD));
                            });
                        } else {
                            Platform.runLater(() -> client.getUI().notifyError(I18n.string(I18nKey.YOU_CANT_BUILD_WITH_THE_SPECIFIED_WORKER)));
                        }
                    }
            );
            targetSelected = false;
        } else if (!targetSelected) {
            targetCellX = x;
            targetCellY = y;
            targetSelected = true;

            setCellHighlighting(false);

            boolean canBuildBlock = turn.getWorkerBlockBuildableCells(workerID).getPosition(targetCellX, targetCellY);
            boolean canBuildDome = turn.getWorkerDomeBuildableCells(workerID).getPosition(targetCellX, targetCellY);

            if (canBuildBlock && canBuildDome) {
                selectedComponent = null;
                setCellHighlighting(false);
                board.getCell(x, y).setHighlighted(true);
                Platform.runLater(() -> {
                    controller.displayComponentSelection();
                    controller.redrawBoard();
                    controller.setPrompt(I18n.string(I18nKey.CHOOSE_BETWEEN_BLOCK_OR_DOME));
                });
            } else {
                selectedComponent = canBuildBlock ? ReducedComponent.BLOCK : ReducedComponent.DOME;
            }
            if(selectedComponent != null){
                component = selectedComponent;
                selectedComponent = null;
                clientState.notifyUiInteraction();
            }
        }
    }

    @Override
    public void skip() {
        setCellHighlighting(false);
        workerID = null;
        clientState.notifyUiInteraction();
    }

    @Override
    public void cancel() {
        //Cannot cancel a build
    }

    private void setCellHighlighting(boolean status){
        if(workerID != null){
            game = client.getGame();
            turn = game.getTurn();
            board = game.getBoard();
            board.getTargets(turn.getWorkerBlockBuildableCells(workerID)).forEach(
                    targetedCell -> targetedCell.setHighlighted(status)
            );

            board.getTargets(turn.getWorkerDomeBuildableCells(workerID)).forEach(
                    targetedCell -> targetedCell.setHighlighted(status)
            );
        }
    }

    @Override
    public void handleError() {
        workerID = null;
        targetSelected = false;
        selectedComponent = null;
    }

    public void selectComponent(ReducedComponent component) {
        this.component = component;
        selectedComponent = null;
        clientState.notifyUiInteraction();
        board.getCell(targetCellX, targetCellY).setHighlighted(false);
        Platform.runLater(() -> controller.setPrompt(""));
    }
}
