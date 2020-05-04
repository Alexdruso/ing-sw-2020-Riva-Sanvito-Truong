package it.polimi.ingsw.client.reducedmodel;

import it.polimi.ingsw.utils.messages.ReducedGod;
import it.polimi.ingsw.utils.messages.ReducedUser;
import it.polimi.ingsw.utils.messages.ReducedWorkerID;

import java.util.HashMap;
import java.util.Map;

public class ReducedPlayer {
    private final ReducedUser user;
    private final boolean isLocalPlayer;
    private boolean isInGame;
    private final int playerIndex;
    private ReducedGod god;
    private Map<ReducedWorkerID, ReducedWorker> workers;

    public ReducedPlayer(ReducedUser user, boolean isLocalPlayer, int playerIndex) {
        this.user = user;
        this.isLocalPlayer = isLocalPlayer;
        isInGame = true;
        this.playerIndex = playerIndex;
        workers = new HashMap<>();
    }

    public String getNickname() {
        return user.nickname;
    }

    public ReducedUser getUser() {
        return user;
    }

    public boolean isLocalPlayer() {
        return isLocalPlayer;
    }

    public boolean isInGame() {
        return isInGame;
    }

    public void setInGame(boolean inGame) {
        isInGame = inGame;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public ReducedGod getGod() {
        return god;
    }

    public void setGod(ReducedGod god) {
        this.god = new ReducedGod(god);
    }

    public Map<ReducedWorkerID, ReducedWorker> getWorkers() {
        return new HashMap<>(workers);
    }

    void addWorker(ReducedWorker worker) {
        workers.put(worker.getWorkerID(), worker);
    }

    public ReducedWorker getWorker(ReducedWorkerID workerID) {
        return workers.get(workerID);
    }
}
