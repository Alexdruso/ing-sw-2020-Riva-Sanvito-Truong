package it.polimi.ingsw.client.reducedmodel;

public class ReducedPlayer {
    private final String nickname;
    private final boolean isLocalPlayer;
    private boolean isInGame;
    private final int playerIndex;

    public ReducedPlayer(String nickname, boolean isLocalPlayer, int playerIndex) {
        this.nickname = nickname;
        this.isLocalPlayer = isLocalPlayer;
        isInGame = true;
        this.playerIndex = playerIndex;
    }

    public String getNickname() {
        return nickname;
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
}
