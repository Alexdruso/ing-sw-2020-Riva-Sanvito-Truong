package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gods.God;
import it.polimi.ingsw.model.turnevents.TurnEventsManager;
import it.polimi.ingsw.model.workers.Worker;
import it.polimi.ingsw.model.workers.WorkerID;

import java.util.Objects;

/**
 * This class is the model representation of the physical player.
 * It keep all the information regarding the single player.
 * It provides methods to perform actions on the game.
 */

public class Player {

    /**
     * The name chosen by the physical player
     */
    private String nickname;

    /**
     * A structure to contain all the player's workers
     */
    private Worker[] ownWorkers;

    /**
     * The player's god, can't be the same as other players
     */
    private God god;

    /**
     * A manager of the player's in game spells and buffs, must be passed to the turn in the binding process
     */
    private TurnEventsManager turnEventsManager;

    /**
     * The player's workers number, constant and common to all players
     */
    private static final int WORKER_NUMBER =2;

    /**
     * Constructor of the class, initializes nickname and workers, leaves god and turn event manager to be defined
     * @param nickname the name chosen by the player
     */
    public Player(String nickname) {
        this.nickname = nickname;
        this.ownWorkers = new Worker[WORKER_NUMBER];

        for(int i = 0; i< WORKER_NUMBER; i++){
            ownWorkers[i] = new Worker(this, WorkerID.values()[i]);
        }
    }

    /**
     * Getter of nickname
     * @return the nickname of the player
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Setter of nickname
     * @param nickname a new nickname for the player
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Getter of the list of workers
     * @return a list of the player's workers
     */
    public Worker[] getOwnWorkers() {
        return ownWorkers;
    }

    /**
     * Retrieves the player's worker by its ID
     * @param id the ID of the worker to be retrieved
     * @return the Worker which corresponds to the provided ID
     */
    public Worker getWorkerByID(WorkerID id){
        for(Worker w: ownWorkers){
            if(w.getWorkerID().equals(id)){
                return w;
            }
        }
        throw new IllegalArgumentException("No such worker found");
    }

    /**
     * Getter of the player's god
     * @return the god bound to the player
     */
    public God getGod() {
        return god;
    }

      /**
     * This method sets up the player's god.
     * It saves the god card and initializes the TurnEventManager
     * @param god the god chosen by the player
     */
    public void setGod(God god) {
        this.god = god;
        //not only does the method save the god, but it also initializes the turnEventsManager
        this.turnEventsManager = new TurnEventsManager(this);
    }

    /**
     * Getter of the player's TurnEventsManager
     * @return the player's TurnEventsManager
     */
    public TurnEventsManager getTurnEventsManager() {
        return turnEventsManager;
    }

    /**
     * Overridden equals method
     * @param o the object to compare
     * @return true if the player equals o
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return getNickname().equals(player.getNickname());
    }

    /**
     * Overridden hashcode method
     * @return hashed value of Player
     */
    @Override
    public int hashCode() {
        return Objects.hash(getNickname());
    }
}
