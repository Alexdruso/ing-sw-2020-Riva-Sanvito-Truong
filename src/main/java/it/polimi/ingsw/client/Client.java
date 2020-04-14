package it.polimi.ingsw.client;

import it.polimi.ingsw.client.clientstates.AbstractClientState;
import it.polimi.ingsw.client.clientstates.AbstractConnectToServerClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.Ui;
import it.polimi.ingsw.client.ui.cli.Cli;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.StatusMessages;
import it.polimi.ingsw.utils.messages.ClientDisconnectMessage;
import it.polimi.ingsw.utils.messages.ServerMessage;
import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.utils.networking.Transmittable;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The Client.
 */
public class Client implements Observer<Transmittable> {
    private Connection connection;
    private AbstractClientState currentState;
    private ClientState nextState;
    private String nickname;
    private final AtomicBoolean renderRequested;
    private final Ui ui;
    private boolean exitRequested;

    /**
     * Instantiates a new Client.
     *
     * @param ui the user interface this Client will use
     */
    public Client(Ui ui) {
        this.ui = ui;
        nextState = ClientState.CONNECT_TO_SERVER;
        renderRequested = new AtomicBoolean(false);
        exitRequested = false;
    }

    /**
     * Runs the client.
     * The user interface renders itself in this function, in the thread that originally called Client::run.
     */
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

    /**
     * Closes the connection to the server, if established.
     */
    private void close() {
        if (connection != null) {
            connection.close(new ClientDisconnectMessage());
        }
    }

    /**
     * Gets the connection to the server.
     *
     * @return the connection to the server
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Sets the connection to the server.
     *
     * @param connection the connection to the server
     * @throws IllegalStateException if trying to set a connection after the client already has one
     */
    public void setConnection(Connection connection) throws IllegalStateException {
        if (this.connection == null) {
            this.connection = connection;
        }
        else {
            throw new IllegalStateException("Illegal attempt to reassign the connection");
        }
        connection.addObserver(this);
    }

    /**
     * Gets the nickname of the user.
     *
     * @return the nickname of the user
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Sets the nickname of the user.
     *
     * @param nickname the nickname of the user
     * @throws IllegalStateException tf trying to set the nickname after the client already has one
     */
    public void setNickname(String nickname) throws IllegalStateException {
        if (this.nickname == null) {
            this.nickname = nickname;
        }
        else {
            throw new IllegalStateException("Illegal attempt to reassign the nickname");
        }
    }

    /**
     * Disconnect cleanly from the server.
     */
    public void disconnect() {
        exitRequested = true;
    }

    /**
     * Sets the next state for the Client.
     *
     * @param nextState the next state
     */
    public void setNextState(ClientState nextState) {
        this.nextState = nextState;
    }

    /**
     * Moves the client to a previously set next state.
     */
    public void changeState() {
        currentState = ui.getClientState(nextState, this);
        currentState.setup();
    }

    /**
     * Move the client to a specific state.
     *
     * @param nextState the next state
     */
    public void moveToState(ClientState nextState) {
        setNextState(nextState);
        changeState();
    }

    /**
     * Requests the user interface to render the current state.
     * The actual rendering is performed by the main thread (the one that invoked Client::run).
     */
    public void requestRender() {
        synchronized (renderRequested) {
            renderRequested.set(true);
            renderRequested.notifyAll();
        }
    }

    /**
     * Gets the user interface.
     *
     * @return the user interface
     */
    public Ui getUi() {
        return ui;
    }


    /**
     * Handles the messages received from the server.
     *
     * @param message the message received from the server
     */
    public void update(Transmittable message) {
        if (message instanceof StatusMessages) {
            switch ((StatusMessages) message) {
                case CLIENT_ERROR:
                    currentState.handleClientError();
                    break;
                case CONTINUE:
                    currentState.handleContinue();
                    break;
                case OK:
                    currentState.handleOk();
                    break;
                case TEAPOT:
                    currentState.handleTeapot();
                    break;
                case SERVER_ERROR:
                    currentState.handleServerError();
                    break;
                default:
                    throw new IllegalStateException();
            }
        }
        else if (message instanceof ServerMessage) {
            currentState.handleServerMessage((ServerMessage) message);
        }
        else {
            throw new IllegalStateException();
        }
    }
}
