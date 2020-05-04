package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractAskWorkerPositionClientTurnState;
import it.polimi.ingsw.client.reducedmodel.ReducedCell;

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
            ReducedCell targetCell = cli.readCell(client.getGame().getBoard(), "In quale cella vuoi posizionare il tuo worker?");

            targetCellX = targetCell.getX();
            targetCellY = targetCell.getY();

            clientState.notifyUiInteraction();
        }
        else {
            cli.println(String.format("Attendi che %s scelga dove posizionare i suoi workers...", client.getCurrentActiveUser().nickname));
        }
    }

    @Override
    public void handleClientError() throws UnsupportedOperationException {
        cli.error("Impossibile posizionare il worker nella cella selezionata");
    }
}
