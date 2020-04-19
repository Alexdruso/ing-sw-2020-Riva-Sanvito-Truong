package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.utils.messages.ServerStartSetupMatchMessage;
import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.view.View;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is designed to be able to run on a separate thread and initialize all the classes
 * needed to start the game, in particular Game,VirtualView and Controller.
 * Before calling the "run" method, the server should add nicknames and connections in order.
 */
public class Match implements Runnable {
    /**
     * The participants, represented by nickname and connection.
     */
    private LinkedHashMap<String, Connection> participants = new LinkedHashMap<String, Connection>();
    /**
     * The model.
     */
    private Game model;
    /**
     * The views related to each participant
     */
    private List<View> virtualViews = new LinkedList<View>();
    /**
     * The controller.
     */
    private Controller controller;
    /**
     * Boolean flag to shutdown the Match.
     */
    private boolean isPlaying;

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        //create a new game
        this.model = new Game(this.participants.size());
        //create the controller
        this.controller = new Controller(model);
        //create array to hold users
        User[] users = new User[this.participants.size()];
        //initialize array index
        int usersIndex = 0;
        //Create the views and add the player to the Game
        for(String nickname : this.participants.keySet()){
            //create the view
            View virtualView = new View(this.participants.get(nickname), nickname);
            //get the user from the view
            User user = virtualView.getUser();
            //add user to user array
            users[usersIndex] = user;
            //add the user as a player in the model
            model.subscribeUser(user);
            //the view observes the model
            model.addObserver(virtualView);
            //the controller observes the view
            virtualView.addObserver(controller);
            //add virtualView to the virtualViews
            this.virtualViews.add(virtualView);
            //increment usersIndex
            usersIndex++;
        }
        //Start the game setup, first creating serverStartSetupMatchMessage
        //with the array of users, then sending the message over the connections
        ServerStartSetupMatchMessage serverStartSetupMatchMessage = new ServerStartSetupMatchMessage(users);
        for(Connection connection : this.participants.values()){
            connection.send(serverStartSetupMatchMessage);
        }
        //now just make the controller work on this thread
        while(this.isPlaying()){
            this.controller.dispatchViewClientMessages();
        }
    }

    /**
     * Adds the connection and the nickname to the match own participants
     *
     * @param nickname   the nickname chosen by the participants
     * @param connection the connection of the participant
     */
    public void addParticipant(String nickname, Connection connection){
        this.participants.put(nickname,connection);
    }

    /**
     * Adds all the participants from the parameter to the match own participants.
     * It should be called only before executing run.
     *
     * @param participants the structure holding the participants to be added
     */
    public void addParticipants(LinkedHashMap<String,Connection> participants){
        this.participants.putAll(participants);
    }

    /**
     * Returns the participants to the match, represented by their nickname and connection
     *
     * @return the participants to the match
     */
    public LinkedHashMap<String,Connection> getParticipants(){
        return new LinkedHashMap<String, Connection>(this.participants);
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
     * Sets playing.
     *
     * @param isPlaying the playing
     */
    public void setPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }
}
