package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractAskWorkerPositionClientTurnState;
import it.polimi.ingsw.client.reducedmodel.ReducedCell;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;

public class AskWorkerPositionCLIClientTurnState extends AbstractAskWorkerPositionClientTurnState implements CLIClientTurnState {
    private final InGameCLIClientState clientState;
    private final CLI cli;

    public AskWorkerPositionCLIClientTurnState(Client client, InGameCLIClientState clientState) {
        super(client);
        this.clientState = clientState;
        this.cli = (CLI) client.getUI();
    }

    @Override
    public void render() {
        if (client.isCurrentlyActive()) {
            ReducedCell targetCell = cli.readCell(client.getGame().getBoard(), I18n.string(I18nKey.WHERE_DO_YOU_WANT_TO_PLACE_YOUR_WORKER));

            targetCellX = targetCell.getX();
            targetCellY = targetCell.getY();

            clientState.notifyUiInteraction();
        }
        else {
            cli.println(String.format(I18n.string(I18nKey.WHERE_DO_YOU_WANT_TO_PLACE_YOUR_WORKER), client.getCurrentActiveUser().nickname));
        }
    }

    @Override
    public void handleClientError() throws UnsupportedOperationException {
        cli.error(I18n.string(I18nKey.YOU_CANT_PLACE_THE_WORKER_IN_THE_SPECIFIED_CELL));
    }
}
