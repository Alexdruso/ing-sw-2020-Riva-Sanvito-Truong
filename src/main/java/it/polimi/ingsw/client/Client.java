package it.polimi.ingsw.client;

import it.polimi.ingsw.client.clientstates.AbstractClientState;
import it.polimi.ingsw.client.clientstates.AbstractConnectToServerClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.Ui;
import it.polimi.ingsw.client.ui.cli.Cli;
import it.polimi.ingsw.utils.networking.Connection;

import java.util.concurrent.atomic.AtomicBoolean;

public class Client {
    private Connection connection;
    private AbstractClientState currentState;
    private ClientState nextState;
    private final AtomicBoolean renderRequested;
    private final Ui ui;

    public Client(Ui ui) {
        this.ui = ui;
        nextState = ClientState.CONNECT_TO_SERVER;
        renderRequested = new AtomicBoolean(false);
    }

    public void run() {
        ui.init();
        changeState();

        boolean exitRequested = false;
        while (!exitRequested) {
            synchronized (renderRequested) {
                while (!renderRequested.getAndSet(false)) {
                    try {
                        renderRequested.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
            currentState.render();
        }
    }

    public void setConnection(Connection connection) {
        if (this.connection != null) {
            this.connection = connection;
        }
        else {
            throw new RuntimeException("Illegal attempt to reassign the connection");
        }
    }

    public void changeState() {
        currentState = ui.getClientState(nextState, this);
        currentState.setup();
    }

    public void requestRender() {
        synchronized (renderRequested) {
            renderRequested.set(true);
            renderRequested.notifyAll();
        }
    }

    public Ui getUi() {
        return ui;
    }

//    public Gui getGui() {
//        return (Gui) ui;
//    }
}
