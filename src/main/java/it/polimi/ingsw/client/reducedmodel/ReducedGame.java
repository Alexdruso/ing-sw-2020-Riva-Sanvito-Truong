package it.polimi.ingsw.client.reducedmodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ReducedGame {
    private final List<ReducedPlayer> players;
    private ReducedTurn turn;

    public ReducedGame(Collection<ReducedPlayer> players) {
        this.players = new ArrayList<>(players);
    }

    public ArrayList<ReducedPlayer> getPlayersList() {
        return new ArrayList<>(players);
    }

    public ReducedTurn getTurn() {
        return turn;
    }

    public void setTurn(ReducedTurn turn) {
        this.turn = turn;
    }
}
