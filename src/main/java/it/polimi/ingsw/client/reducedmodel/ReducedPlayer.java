package it.polimi.ingsw.client.reducedmodel;

import it.polimi.ingsw.utils.networking.transmittables.ReducedGod;
import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;
import it.polimi.ingsw.utils.networking.transmittables.ReducedWorkerID;

import java.util.EnumMap;
import java.util.Map;

/**
 * A representation of a player of the game, reduced with respect to the server's Player to contain only the information required by the client.
 */
public class ReducedPlayer {
    private final ReducedUser user;
    private final boolean isLocalPlayer;
    private boolean isInGame;
    private final int playerIndex;
    private ReducedGod god;
    private final EnumMap<ReducedWorkerID, ReducedWorker> workers;

    /**
     * Instantiates a new Reduced player.
     *
     * @param user          the user to bind the ReducedPlayer to
     * @param isLocalPlayer whether the new ReducedPlayer is the one of the current client
     * @param playerIndex   the player index
     */
    public ReducedPlayer(ReducedUser user, boolean isLocalPlayer, int playerIndex) {
        this.user = user;
        this.isLocalPlayer = isLocalPlayer;
        isInGame = true;
        this.playerIndex = playerIndex;
        workers = new EnumMap<>(ReducedWorkerID.class);
    }

    /**
     * Gets the nickname.
     *
     * @return the nickname
     */
    public String getNickname() {
        return user.getNickname();
    }

    /**
     * Gets the user.
     *
     * @return the user
     */
    public ReducedUser getUser() {
        return user;
    }

    /**
     * Checks if the ReducedPlayer is the local one.
     *
     * @return whether the ReducedPlayer is the local one
     */
    public boolean isLocalPlayer() {
        return isLocalPlayer;
    }

    /**
     * Checks if the ReducedPlayer is currently in game.
     *
     * @return whether the ReducedPlayer is currently in game
     */
    public boolean isInGame() {
        return isInGame;
    }

    /**
     * Sets if the ReducedPlayer is currently in game.
     *
     * @param inGame whether the ReducedPlayer is currently in game
     */
    public void setInGame(boolean inGame) {
        isInGame = inGame;
    }

    /**
     * Gets the player index.
     *
     * @return the player index
     */
    public int getPlayerIndex() {
        return playerIndex;
    }

    /**
     * Gets the player's god.
     *
     * @return the player's god
     */
    public ReducedGod getGod() {
        return god;
    }

    /**
     * Sets the player's god.
     *
     * @param god the player's god
     */
    public void setGod(ReducedGod god) {
        this.god = new ReducedGod(god);
    }

    /**
     * Gets the player's workers.
     *
     * @return the player's workers
     */
    public Map<ReducedWorkerID, ReducedWorker> getWorkers() {
        return new EnumMap<>(workers);
    }

    /**
     * Adds a worker to the player.
     *
     * @param worker the worker to add
     */
    void addWorker(ReducedWorker worker) {
        workers.put(worker.getWorkerID(), worker);
    }

    /**
     * Remove a worker from the player.
     *
     * @param workerID the worker id
     */
    void removeWorker(ReducedWorkerID workerID) {
        workers.remove(workerID);
    }

    /**
     * Gets a player's worker from the worker id.
     *
     * @param workerID the worker id
     * @return the worker; if no worker with the given worker id is found, null is returned
     */
    public ReducedWorker getWorker(ReducedWorkerID workerID) {
        return workers.get(workerID);
    }
}
