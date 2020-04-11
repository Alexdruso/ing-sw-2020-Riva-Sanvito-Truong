package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.gods.God;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.utils.networking.Transmittable;
import it.polimi.ingsw.view.View;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Match implements Runnable, Observer<Transmittable> {
    /**
     * The structure which holds the connections related to the game
     */
    private final Connection[] connections;
    /**
     * The structures which holds all the nicknames of the joining players
     */
    private final String[] nicknames;
    private Controller controller;
    private View[] virtualViews;

    /**
     * Constructor of a 2 players match
     * @param user1 the first player
     * @param nickname1 the first player's nickname
     * @param user2 the second player
     * @param nickname2 the second player's nickname
     */
    public Match(Connection user1, String nickname1, Connection user2, String nickname2) {
        //initialize connections and nicknames data structure
        this.connections = new Connection[]{user1, user2};
        this.nicknames = new String[]{nickname1,nickname2};
        for(Connection connection:connections){
            connection.addObserver(this);
        }
    }

    /**
     * Constructor of a 3 players match
     * @param user1 the first player
     * @param nickname1 the first player's nickname
     * @param user2 the second player
     * @param nickname2 the second player's nickname
     * @param user3 the third player
     * @param nickname3 the third player's nickname
     */
    public Match(Connection user1, String nickname1, Connection user2, String nickname2, Connection user3, String nickname3) {
        //initialize connections and nicknames data structure
        this.connections = new Connection[]{user1, user2, user3};
        this.nicknames = new String[]{nickname1,nickname2,nickname3};
        for(Connection connection:connections){
            connection.addObserver(this);
        }
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
     * @see Thread#run()
     */
    @Override
    public void run() {
        //TODO send match started notification
        //TODO ask god sublist to connections[0] (this shall be a broadcast message with nickname as payload)
        //TODO wait for an answer
        //TODO send confirmation
        //the structures which is to hold the God chosen by the player
        Map<String, God> chosenGods = new HashMap<String, God>();
        //TODO ask to chose own god to each player
        //instantiate the game
        Game model = new Game(this.nicknames.length);
        //the structure to hold the views
        List<View> views = new LinkedList<View>();
        //setup game and views
        for (String nickname : nicknames) {
            User user = new User(nickname);
            model.subscribeUser(nickname, chosenGods.get(nickname));
            //TODO initialize a virtualView w/ right parameters
            views.add(new View());
        }
        //TODO change controller initialization with right parameters
        Controller controller = new Controller(model);
        //TODO start game setup
    }

    /**
     * This method is called by the Observable to update the observer
     *
     * @param message the message to be received
     */
    @Override
    public void update(Transmittable message) {

    }
}
