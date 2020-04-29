package it.polimi.ingsw.client.reducedmodel;

public class ReducedTurn {
    private final ReducedPlayer player;

    public ReducedTurn(ReducedPlayer player) {
        this.player = player;
    }

    public ReducedPlayer getPlayer() {
        return player;
    }
}
