package it.polimi.ingsw.server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.User;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.view.View;
import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.utils.networking.transmittables.DisconnectionMessage;
import it.polimi.ingsw.utils.networking.transmittables.StatusMessages;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is designed to be able to run on a separate thread and initialize all the classes
 * needed to start the game, in particular Game,VirtualView and Controller.
 * Before calling the "run" method, the server should add nicknames and connections in order.
 */
public class Match implements Runnable {
    /**
     * The constant LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(Match.class.getName());
    /**
     * The server.
     */
    private final Server server;
    /**
     * The participants, represented by nickname and connection.
     */
    private final LinkedHashMap<String, Connection> participantsNicknameToConnection = new LinkedHashMap<>();
    /**
     * The model.
     */
    private Game model;
    /**
     * The views related to each participant
     */
    private final List<View> virtualViews = new LinkedList<>();
    /**
     * The controller.
     */
    private Controller controller;
    /**
     * Boolean flag to shutdown the Match.
     */
    private boolean isPlaying = true;

    /**
     * The match constructor.
     *
     * @param server the server hosting the match
     */
    public Match(Server server) {
        this.server = server;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run() Thread#run()
     */
    @Override
    public void run() {
        //create a new game
        this.model = new Game(this.participantsNicknameToConnection.size());
        //create the controller
        this.controller = new Controller(model);
        //Create the views and add the player to the Game
        for (View virtualView : this.virtualViews) {
            //get the user from the view
            User user = virtualView.getUser();
            //add the user as a player in the model
            model.subscribeUser(user);
            //the view observes the model
            model.addObserver(
                    virtualView, (obs, message) ->
                    {
                        if (message instanceof DisconnectionMessage) {
                            ((View) obs).requestDisconnection();
                        } else {
                            ((View) obs).updateFromGame(message);
                        }
                    }
            );
            //the controller observes the view
            virtualView.addObserver(controller, (obs, message) ->
                    ((Controller) obs).update(message));
            //check if connection is still up, if not send a disconnection message to dismantle the game
            if (!participantsNicknameToConnection.get(user.nickname).isActive()) {
                virtualView.updateFromClient(new DisconnectionMessage());
            }
        }
        //Start setup procedure
        model.setup();
        //now just make the controller work on this thread
        while (this.isPlaying()) {
            try {
                this.controller.dispatchViewClientMessages();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
                participantsNicknameToConnection.values().stream()
                        .filter(Connection::isActive).forEach(connection -> connection.send(StatusMessages.SERVER_ERROR));
                break;
            }
            //check if the game is active
            this.setIsPlaying(this.model.isActive());
        }
        //Removes itself from the server
        server.removeMatch(this);
    }

    /**
     * Adds the connection and the nickname to the match own participants initializing the view.
     *
     * @param nickname   the nickname chosen by the participants
     * @param connection the connection of the participant
     */
    public void addParticipant(String nickname, Connection connection) {
        this.participantsNicknameToConnection.put(nickname, connection);
        virtualViews.add(new View(connection, nickname));
    }

    /**
     * Adds all the participants from the parameter to the match own participants initializing the views.
     * It should be called only before executing run.
     *
     * @param participants the structure holding the participants to be added
     */
    public void addParticipants(Map<String, Connection> participants) {
        this.participantsNicknameToConnection.putAll(participants);

        for (Map.Entry<String, Connection> participant : participants.entrySet()) {
            virtualViews.add(new View(participant.getValue(), participant.getKey()));
        }
    }

    /**
     * Returns the participants to the match, represented by their nickname and connection
     *
     * @return the participants to the match
     */
    public Map<String, Connection> getParticipantsNicknameToConnection() {
        return new LinkedHashMap<>(this.participantsNicknameToConnection);
    }

    /**
     * Gets model.
     *
     * @return the model
     */
    public Game getModel() {
        return model;
    }

    /**
     * Gets virtual views.
     *
     * @return the virtual views
     */
    public List<View> getVirtualViews() {
        return new LinkedList<>(virtualViews);
    }

    /**
     * Gets controller.
     *
     * @return the controller
     */
    public Controller getController() {
        return controller;
    }

    /**
     * Is playing boolean.
     *
     * @return the boolean
     */
    public boolean isPlaying() {
        return isPlaying;
    }

    /**
     * Sets isPlaying.
     *
     * @param isPlaying the parameter telling if the match is still active
     */
    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }
}
