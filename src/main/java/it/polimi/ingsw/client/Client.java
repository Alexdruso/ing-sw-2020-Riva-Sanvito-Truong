package it.polimi.ingsw.client;

import it.polimi.ingsw.client.clientstates.AbstractClientState;
import it.polimi.ingsw.client.clientstates.AbstractConnectToServerClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.Ui;
import it.polimi.ingsw.client.ui.cli.Cli;
import it.polimi.ingsw.utils.messages.ClientDisconnectMessage;
import it.polimi.ingsw.utils.networking.Connection;

import java.util.concurrent.atomic.AtomicBoolean;

public class Client {
    private Connection connection;
    private AbstractClientState currentState;
    private ClientState nextState;
    private String nickname;
    private final AtomicBoolean renderRequested;
    private final Ui ui;
    private boolean exitRequested;

    public Client(Ui ui) {
        this.ui = ui;
        nextState = ClientState.CONNECT_TO_SERVER;
        renderRequested = new AtomicBoolean(false);
        exitRequested = false;
    }

    public void run() {
        ui.init();
        changeState();

        while (!exitRequested) {
            synchronized (renderRequested) {
                while (!renderRequested.getAndSet(false)) {
                    try {
                        renderRequested.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        close();
                        return;
                    }
                }
            }
            currentState.render();
        }
        close();
    }

    private void close() {
        if (connection != null) {
            connection.close(new ClientDisconnectMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        if (this.connection == null) {
            this.connection = connection;
        }
        else {
            throw new RuntimeException("Illegal attempt to reassign the connection");
        }
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        if (this.nickname == null) {
            this.nickname = nickname;
        }
        else {
            throw new RuntimeException("Illegal attempt to reassign the nickname");
        }
    }

    public void disconnect() {
        exitRequested = true;
    }

    public void setNextState(ClientState nextState) {
        this.nextState = nextState;
    }

    public void changeState() {
        if (connection != null && currentState != null) {
            connection.removeObserver(currentState);
        }
        currentState = ui.getClientState(nextState, this);
        if (connection != null) {
            connection.addObserver(currentState);
        }
        currentState.setup();
    }

    public void moveToState(ClientState nextState) {
        setNextState(nextState);
        changeState();
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
}
