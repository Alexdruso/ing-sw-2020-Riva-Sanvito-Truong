package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractBuildClientTurnState;
import it.polimi.ingsw.client.reducedmodel.ReducedCell;
import it.polimi.ingsw.client.ui.gui.guicontrollers.InGameController;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import javafx.application.Platform;

public class BuildGUIClientTurnState extends AbstractBuildClientTurnState implements GUIClientTurnState {
    public BuildGUIClientTurnState(Client client, InGameGUIClientState clientState) {
        super(client);
    }

    @Override
    public void render() {
        InGameController controller = (InGameController)((GUI)client.getUI()).getCurrentScene().controller;
        if(client.isCurrentlyActive()){
            Platform.runLater(() -> {
                controller.setLabel(I18n.string(I18nKey.WHERE_DO_YOU_WANT_TO_PLACE_YOUR_WORKER));
                controller.setPrompt(I18n.string(I18nKey.ASK_WORKER_START_POSITION_PROMPT));
                controller.setBoardClickableStatus(true);
                controller.redrawBoard();
            });
        } else {
            Platform.runLater(() -> {
                controller.setLabel(String.format(I18n.string(I18nKey.WAIT_FOR_S_TO_PLACE_THEIR_WORKERS), client.getCurrentActiveUser().nickname));
                controller.setPrompt(I18n.string(I18nKey.ASK_WORKER_START_POSITION_PASSIVE_PROMPT));
                controller.setBoardClickableStatus(false);
                controller.redrawBoard();
            });
        }

    }

    @Override
    public void selectCell(int x, int y) {

    }
}
