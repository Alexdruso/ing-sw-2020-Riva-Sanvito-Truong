package it.polimi.ingsw.client.reducedmodel;

import it.polimi.ingsw.utils.messages.ReducedGod;
import it.polimi.ingsw.utils.messages.ReducedUser;

public class ReducedPlayer {
    private final ReducedUser user;
    private final boolean isLocalPlayer;
    private boolean isInGame;
    private final int playerIndex;
    private ReducedGod god;

    public ReducedPlayer(ReducedUser user, boolean isLocalPlayer, int playerIndex) {
        this.user = user;
        this.isLocalPlayer = isLocalPlayer;
        isInGame = true;
        this.playerIndex = playerIndex;
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
}
