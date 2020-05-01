package it.polimi.ingsw.client.reducedmodel;

import it.polimi.ingsw.utils.messages.ReducedUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class ReducedGame {
    private final List<ReducedPlayer> players;
    private ReducedTurn turn;
    private final ReducedBoard board;

    public ReducedGame(Collection<ReducedPlayer> players) {
        this.players = new ArrayList<>(players);
        board = new ReducedBoard();
    }

    public ArrayList<ReducedPlayer> getPlayersList() {
        return new ArrayList<>(players);
    }

    public Optional<ReducedPlayer> getPlayer(String nickname) {
        return players.stream().filter(x -> x.getNickname().equals(nickname)).findFirst();
    }

    public Optional<ReducedPlayer> getPlayer(ReducedUser user) {
        return getPlayer(user.nickname);
    }

    public ReducedTurn getTurn() {
        return turn;
    }

    public void setTurn(ReducedTurn turn) {
        this.turn = turn;
    }

    public void setWorkerCell(ReducedWorker worker, ReducedCell sourceCell, ReducedCell targetCell) {
        if (targetCell.getWorker().isEmpty()) {
            sourceCell.setNoWorker();
        }
        targetCell.setWorker(worker);
    }

    public int getPlayersCount() {
        return players.size();
    }

    public ReducedBoard getBoard() {
        return board;
    }
}
