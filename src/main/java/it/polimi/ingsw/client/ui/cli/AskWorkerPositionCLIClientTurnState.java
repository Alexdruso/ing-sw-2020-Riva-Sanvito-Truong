package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractAskWorkerPositionClientTurnState;
import it.polimi.ingsw.utils.messages.ClientSetWorkerStartPositionMessage;

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
            cli.readString("In quale cella vuoi posizionare il tuo worker? (coordinate)", null, 3);

            //targetCellX = ...

            clientState.notifyUiInteraction();
        }
        else {
            cli.println(String.format("Attendi che %s scelga dove posizionare i suoi workers...", client.getCurrentActiveUser().nickname));
        }
    }

    @Override
    public void notifyUiInteraction() {
        client.getConnection().send(new ClientSetWorkerStartPositionMessage(targetCellX, targetCellY, workerID));
    }
}
