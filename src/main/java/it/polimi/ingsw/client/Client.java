package it.polimi.ingsw.client;

import it.polimi.ingsw.client.clientstates.AbstractClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.reducedmodel.ReducedCell;
import it.polimi.ingsw.client.reducedmodel.ReducedGame;
import it.polimi.ingsw.client.reducedmodel.ReducedPlayer;
import it.polimi.ingsw.client.ui.UI;
import it.polimi.ingsw.utils.config.ConfigParser;
import it.polimi.ingsw.utils.networking.ClientHandleable;
import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.utils.networking.transmittables.ReducedGod;
import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;
import it.polimi.ingsw.utils.networking.transmittables.StatusMessages;
import it.polimi.ingsw.utils.networking.transmittables.Transmittable;
import it.polimi.ingsw.utils.observer.LambdaObserver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Santorini client.
 */
public class Client implements LambdaObserver {
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());
    /**
     * After receiving an exit request from the user, the client will try for up to EXIT_TIMEOUT_MILLIS
     * to cleanly disconnect from the server, then it will exit abruptly closing the connection.
     *
     * @see Client#onExit()
     */
    public static final int EXIT_TIMEOUT_MILLIS = 500;

    private final AtomicBoolean exitRequested = new AtomicBoolean(false);
    private final AtomicBoolean readyToExit = new AtomicBoolean(false);

    private Connection connection;
    private String nickname;
    private final UI ui;
    private final BlockingQueue<Runnable> renderRequestsQueue = new LinkedBlockingQueue<>();

    private ReducedGame game;
    private final Object gameLock = new Object();
    private List<ReducedGod> gods;
    private final Object godsLock = new Object();

    private AbstractClientState currentState;
    private final Object currentStateLock = new Object();
    private ClientState nextState;
    private ReducedUser currentActiveUser;

    /**
     * Instantiates a new Client.
     *
     * @param ui the user interface this Client will use
     */
    public Client(UI ui) {
        ConfigParser configParser = ConfigParser.getInstance();
        this.ui = ui;
        nextState = ClientState.WELCOME_SCREEN;
        LOGGER.log(Level.INFO, String.format("Starting %s client v. %s...", configParser.getProperty("projectName"), configParser.getProperty("version")));
    }

    /**
     * Runs the client.
     * The user interface renders itself in this function, in the thread that originally called Client::run.
     */
    public void run() {
        ui.init(this::onExit);
        changeState();

        while (!exitRequested.get() || renderRequestsQueue.size() > 0) {
            try {
                Runnable renderRequestAction = renderRequestsQueue.take();
                renderRequestAction.run();
            } catch (InterruptedException e) {
                LOGGER.log(Level.FINE, "Interrupting thread following InterruptedException", e);
                Thread.currentThread().interrupt();
            }
        }

        synchronized (readyToExit) {
            readyToExit.set(true);
            readyToExit.notifyAll();
        }
        onExit();
    }

    /**
     * Requests the client to close.
     */
    public void requestExit() {
        exitRequested.set(true);
        requestRender(() -> {});
    }

    /**
     * Tries to perform a clean exit of the client, waiting up to EXIT_TIMEOUT_MILLIS
     * before abruptly closing any connection.
     */
    private void onExit() {
        requestExit();
        synchronized (readyToExit) {
            try {
                readyToExit.wait(EXIT_TIMEOUT_MILLIS);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        }
        closeConnection();
    }

    /**
     * Sets the connection to the server.
     *
     * @param connection the connection to the server
     * @throws IllegalStateException if trying to set a connection after the client already has one
     */
    public void setConnection(Connection connection) throws IllegalStateException {
        if (this.connection == null || !this.connection.isActive()) {
            this.connection = connection;
        }
        else {
            throw new IllegalStateException("Illegal attempt to reassign the connection");
        }
        connection.addObserver(
                this,
                (obs, message) -> ((Client) obs).update(message)
        );
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
     * Closes the connection to the server, if established.
     */
    public void closeConnection() {
        if (connection != null) {
            connection.close();
            nickname = null;
        }
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
     * Gets the nickname of the user.
     *
     * @return the nickname of the user
     */
    public String getNickname() {
        return nickname;
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
            if(currentState != null){
                requestRender(getCurrentState()::tearDown);
            }
            currentState = ui.getClientState(nextState, this);
        }
        currentState.setup();
    }

    /**
     * Moves the client to a specific state.
     *
     * @param nextState the next state
     */
    public void moveToState(ClientState nextState) {
        setNextState(nextState);
        changeState();
    }

    /**
     * Gets the current client state.
     *
     * @return the current client state
     */
    public AbstractClientState getCurrentState() {
        synchronized (currentStateLock) {
            return currentState;
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
     * Requests the user interface to render the current state.
     * The actual rendering is performed by the main thread (the one that invoked Client::run).
     *
     * @see AbstractClientState#render()
     */
    public void requestRender() {
        requestRender(getCurrentState()::render);
    }

    /**
     * Requests the user interface to render a custom action onto the current state.
     * The actual rendering is performed by the main thread (the one that invoked Client::run).
     *
     * @param renderRequestAction the action to be executed to perform the render
     */
    public void requestRender(Runnable renderRequestAction) {
        try {
            renderRequestsQueue.put(renderRequestAction);
        } catch (InterruptedException e) {
            LOGGER.log(Level.FINE, "Interrupting thread following InterruptedException", e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Handles the messages received from the server.
     *
     * @param message the message received from the server
     */
    public void update(Transmittable message) {
        if (message instanceof StatusMessages) {
            switch ((StatusMessages) message) {
                case CLIENT_ERROR -> currentState.handleClientError();
                case CONTINUE -> currentState.handleContinue();
                case OK -> currentState.handleOk();
                case TEAPOT -> currentState.handleTeapot();
                case SERVER_ERROR -> currentState.handleServerError();
                default -> throw new IllegalStateException();
            }
        }
        else if (message instanceof ClientHandleable) {
            ((ClientHandleable) message).handleTransmittable(this);
        }
        else {
            throw new IllegalStateException();
        }
    }

    /**
     * Creates a new game with the data received from the server.
     *
     * @param users the users that will take part into the game
     */
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

    /**
     * Gets the current active game.
     *
     * @return the current active game
     */
    public ReducedGame getGame() {
        synchronized (gameLock) {
            return game;
        }
    }

    /**
     * Sets the gods that will be available in the currently active game.
     *
     * @param gods the gods that will be available in the currently active game
     */
    public void setGods(List<ReducedGod> gods) {
        synchronized (godsLock) {
            this.gods = new ArrayList<>(gods);
        }
    }

    /**
     * Gets the gods that are available in the currently active game.
     * @return the gods that are available in the currently active game
     */
    public List<ReducedGod> getGods() {
        synchronized (godsLock) {
            return gods;
        }
    }

    /**
     * Sets a specific user as being currently active, as per the data received from the server.
     *
     * @param currentActiveUser the user to be marked as currently active
     */
    public void setCurrentActiveUser(ReducedUser currentActiveUser) {
        this.currentActiveUser = currentActiveUser;
    }

    /**
     * Gets the user who is currently set to be active by the server.
     *
     * @return the user who is currently set to be active by the server
     */
    public ReducedUser getCurrentActiveUser() {
        return currentActiveUser;
    }

    /**
     * Checks whether the local player is currently set to be active by the server.
     *
     * @return whether the local player is currently set to be active by the server
     */
    public boolean isCurrentlyActive() {
        if (currentActiveUser == null) {
            return true;
        }
        else {
            return currentActiveUser.nickname.equals(nickname);
        }
    }
}
