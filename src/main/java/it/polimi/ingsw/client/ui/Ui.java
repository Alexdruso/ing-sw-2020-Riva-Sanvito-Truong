package it.polimi.ingsw.client.ui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.utils.StringCapturedStackTrace;

public abstract class Ui {
    public abstract void init();

    public abstract AbstractClientState getClientState(ClientState clientState, Client client);

    public abstract void notifyError(String message);
    public void notifyError(Exception ex) {
        notifyError(new StringCapturedStackTrace(ex).toString());
    }
    public void notifyError(String message, Exception ex) {
        notifyError(String.format("%s\nDettagli:\n", message, new StringCapturedStackTrace(ex).toString()));
    }
}
