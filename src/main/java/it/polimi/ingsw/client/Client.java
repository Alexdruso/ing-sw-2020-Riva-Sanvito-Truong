package it.polimi.ingsw.client;

import it.polimi.ingsw.client.clientstates.AbstractClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.reducedmodel.ReducedCell;
import it.polimi.ingsw.client.reducedmodel.ReducedGame;
import it.polimi.ingsw.client.reducedmodel.ReducedPlayer;
import it.polimi.ingsw.client.ui.UI;
import it.polimi.ingsw.observer.LambdaObserver;
import it.polimi.ingsw.utils.StatusMessages;
import it.polimi.ingsw.utils.messages.ClientDisconnectMessage;
import it.polimi.ingsw.utils.messages.ReducedGod;
import it.polimi.ingsw.utils.messages.ReducedUser;
import it.polimi.ingsw.utils.networking.ClientHandleable;
import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.utils.networking.Transmittable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Client.
 */
public class Client implements LambdaObserver {
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());
    private Connection connection;
    private final Object currentStateLock = new Object();
    private AbstractClientState currentState;
    private ClientState nextState;
    private String nickname;
    private final AtomicBoolean renderRequested = new AtomicBoolean(false);
    private final UI ui;
    private boolean exitRequested = false;
    private final Set<ReducedCell> changedCells = new HashSet<>();
    private final Object gameLock = new Object();
    private ReducedGame game;
    private final Object godsLock = new Object();
    private List<ReducedGod> gods;
    private ReducedUser currentActiveUser;

    /**
     * Instantiates a new Client.
     *
     * @param ui the user interface this Client will use
     */
    public Client(UI ui) {
        this.ui = ui;
        nextState = ClientState.CONNECT_TO_SERVER;
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
                        LOGGER.log(Level.WARNING, e.getMessage(), e);
                        close();
                        Thread.currentThread().interrupt();
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
        connection.addObserver(this, (obs, message) ->
                ((Client)obs).update(message));
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
        synchronized (currentStateLock) {
            currentState = ui.getClientState(nextState, this);
        }
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

    public AbstractClientState getCurrentState() {
        synchronized (currentStateLock) {
            return currentState;
        }
    }

    /**
     * Requests the user interface to render the current state.
     * The actual rendering is performed by the main thread (the one that invoked Client::run).
     * Please, be aware that calls to the render of the current state are not guaranteed, since calls to render
     * can be concurrent with calls to changeState, thus resulting in the call of render in the context of the new
     * state.
     * This means that either:
     * - you guarantee that there will be no chances that changeState is called before render has finished rendering
     * - or each call to render is self-sufficient (i.e., it does not depend on previous calls to render)
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
    public UI getUI() {
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
        else if (message instanceof ClientHandleable) {
            ((ClientHandleable) message).handleTransmittable(this);
        }
        else {
            throw new IllegalStateException();
        }
    }

    public void addChangedCell(ReducedCell cell) {
        synchronized (changedCells) {
            changedCells.add(cell);
        }
    }

    public Set<ReducedCell> getChangedCells() {
        synchronized (changedCells) {
            return new HashSet<>(changedCells);
        }
    }

    public void clearChangedCells() {
        synchronized (changedCells) {
            changedCells.clear();
        }
    }

    public void createGame(ReducedUser[] users) {
        List<ReducedPlayer> players = new ArrayList<>();
        for (ReducedUser user : users) {
            players.add(new ReducedPlayer(
                    user,
                    user.nickname.equals(this.nickname),
                    players.size()
            ));
        }

        synchronized (gameLock) {
            game = new ReducedGame(players);
        }
    }

    public ReducedGame getGame() {
        synchronized (gameLock) {
            return game;
        }
    }

    public List<ReducedGod> getGods() {
        synchronized (godsLock) {
            return gods;
        }
    }

    public void setGods(List<ReducedGod> gods) {
        synchronized (godsLock) {
            this.gods = new ArrayList<>(gods);
        }
    }

    public ReducedUser getCurrentActiveUser() {
        return currentActiveUser;
    }

    public boolean isCurrentlyActive() {
        if (currentActiveUser == null) {
            return true;
        }
        else {
            return currentActiveUser.nickname.equals(nickname);
        }
    }

    public void setCurrentActiveUser(ReducedUser currentActiveUser) {
        this.currentActiveUser = currentActiveUser;
    }
}
