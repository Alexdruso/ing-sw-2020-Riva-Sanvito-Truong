package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractAskWorkerPositionClientTurnState;
import it.polimi.ingsw.client.ui.gui.guicontrollers.InGameController;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import javafx.application.Platform;

public class AskWorkerPositionGUIClientTurnState extends AbstractAskWorkerPositionClientTurnState implements GUIClientTurnState{
    private final InGameGUIClientState clientState;

    public AskWorkerPositionGUIClientTurnState(Client client, InGameGUIClientState clientState) {
        super(client);
        this.clientState = clientState;
    }

    @Override
    public void render() {
        InGameController controller = (InGameController)((GUI)client.getUI()).getCurrentScene().controller;
        Platform.runLater(controller::clearSideButtons);
        if(client.isCurrentlyActive()){
            Platform.runLater(() -> {
                controller.setLabel(I18n.string(I18nKey.WHERE_DO_YOU_WANT_TO_PLACE_YOUR_WORKER));
                controller.setPrompt(I18n.string(I18nKey.ASK_WORKER_START_POSITION_PROMPT));
                controller.setBoardClickableStatus(true);
            });
        } else {
            Platform.runLater(() -> {
                controller.setLabel(
                        String.format(
                                I18n.string(I18nKey.WAIT_FOR_S_TO_PLACE_THEIR_WORKERS),
                                client.getCurrentActiveUser().getNickname()
                        )
                );
                controller.setPrompt(I18n.string(I18nKey.ASK_WORKER_START_POSITION_PASSIVE_PROMPT));
                controller.setBoardClickableStatus(false);
            });
        }
        Platform.runLater(() -> {
            controller.updatePlayerLabels();
            controller.redrawBoard();
        });
    }

    @Override
    public void selectCell(int x, int y) {
        this.targetCellX = x;
        this.targetCellY = y;
        clientState.notifyUiInteraction();
    }

    @Override
    public void skip() {
        //Cannot skip
    }

    @Override
    public void cancel() {
        //For now, we don't allow canceling
    }

    @Override
    public void handleError() {

    }
}
