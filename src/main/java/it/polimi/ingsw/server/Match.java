package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.utils.messages.ServerStartSetupMatchMessage;
import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.view.View;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;

/**
 * This class is designed to be able to run on a separate thread and initialize all the classes
 * needed to start the game, in particular Game,VirtualView and Controller.
 * Before calling the "run" method, the server should add nicknames and connections in order.
 */
public class Match implements Runnable{
    LinkedHashMap<Connection, String> participants = new LinkedHashMap<Connection,String>();

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
        Game model = new Game(this.participants.size());
        //create the controller
        Controller controller = new Controller(model);
        //Create the views and add the player to the Game
        for(Connection connection : this.participants.keySet()){
            //create the view
            View virtualView = new View(connection,this.participants.get(connection));
            //get the user from the view
            User user = virtualView.getUser();
            //add the user as a player in the model
            model.subscribeUser(user);
            //the view observes the model
            model.addObserver(virtualView);
            //the controller observes the view
            //TODO remove comment when controller implements Observer<Transmittable>
            //virtualView.addObserver(controller);
        }
        //Start the game setup
        ServerStartSetupMatchMessage serverStartSetupMatchMessage = new ServerStartSetupMatchMessage(
                (User[]) this.participants.values().stream().map(User::new).toArray()
        );
        for(Connection connection : this.participants.keySet()){
            connection.send(serverStartSetupMatchMessage);
        }
    }

    /**
     * Adds the connection and the nickname to the match own participants
     * @param connection the connection of the participant
     * @param nickname the nickname chosen by the participants
     */
    public void addParticipant(Connection connection, String nickname){
        this.participants.put(connection,nickname);
    }

    /**
     * Adds all the participants from the parameter to the match own participants.
     * It should be called only before executing run.
     * @param participants the structure holding the participants to be added
     */
    public void addParticipants(LinkedHashMap<Connection,String> participants){
        this.participants.putAll(participants);
    }

    /**
     * Returns the participants to the match, represented by their connection and nickname
     * @return the participants to the match
     */
    public LinkedHashMap<Connection,String> getParticipants(){
        return new LinkedHashMap<Connection,String>(this.participants);
    }
}
