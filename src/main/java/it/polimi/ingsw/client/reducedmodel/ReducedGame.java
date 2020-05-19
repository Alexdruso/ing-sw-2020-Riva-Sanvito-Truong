package it.polimi.ingsw.client.reducedmodel;

import it.polimi.ingsw.utils.messages.ReducedComponent;
import it.polimi.ingsw.utils.messages.ReducedUser;
import it.polimi.ingsw.utils.messages.ReducedWorkerID;

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

    public void addWorker(ReducedUser user, ReducedWorkerID workerID, int targetCellX, int targetCellY) {
        getPlayer(user).ifPresent(
                player -> {
                    ReducedWorker worker = new ReducedWorker(workerID, player);
                    player.addWorker(worker);
                    setWorkerCell(worker, null, getBoard().getCell(targetCellX, targetCellY));
                }
        );
    }

    /**
     * Sets the worker position to the specified cell.
     *
     * @param user        the ReducedUser whose worker is to be positioned
     * @param workerID    the ReducedWorkerID of the worker to be positioned
     * @param sourceCellX the X coordinate of the source cell; if null, the worker is assumed to be placed on the board for the first time
     * @param sourceCellY the Y coordinate of the source cell; if null, the worker is assumed to be placed on the board for the first time
     * @param targetCellX the X coordinate of the target cell
     * @param targetCellY the Y coordinate of the target cell
     */
    public void setWorkerCell(ReducedUser user, ReducedWorkerID workerID, Integer sourceCellX, Integer sourceCellY, Integer targetCellX, Integer targetCellY) {
        ReducedCell sourceCell = null;
        ReducedCell targetCell = getBoard().getCell(targetCellX, targetCellY);
        if (sourceCellX != null && sourceCellY != null) {
            sourceCell = getBoard().getCell(sourceCellX, sourceCellY);
        }

        setWorkerCell(user, workerID, sourceCell, targetCell);
    }

    /**
     * Sets the worker position to the specified cell.
     *
     * @param user       the ReducedUser whose worker is to be positioned
     * @param workerID   the ReducedWorkerID of the worker to be positioned
     * @param sourceCell the ReducedCell on which the worker is before the placement; if null, the worker is assumed to be placed on the board for the first time
     * @param targetCell the ReducedCell on which the worker should be placed
     */
    public void setWorkerCell(ReducedUser user, ReducedWorkerID workerID, ReducedCell sourceCell, ReducedCell targetCell) {
        getPlayer(user).ifPresent(
                player -> setWorkerCell(player.getWorker(workerID), sourceCell, targetCell)
        );
    }

    /**
     * Sets the worker position to the specified cell.
     *
     * @param worker     the ReducedWorker to be positioned
     * @param sourceCell the ReducedCell on which the worker is before the placement; if null, the worker is assumed to be placed on the board for the first time
     * @param targetCell the ReducedCell on which the worker should be placed
     */
    public void setWorkerCell(ReducedWorker worker, ReducedCell sourceCell, ReducedCell targetCell) {
        if (sourceCell != null && targetCell.getWorker().isEmpty()) {
            sourceCell.setNoWorker();
        }
        targetCell.setWorker(worker);
        worker.setCell(targetCell);
    }

    public void buildComponentInCell(int targetCellX, int targetCellY, ReducedComponent component, int builtLevel) {
        ReducedCell targetCell = getBoard().getCell(targetCellX, targetCellY);
        targetCell.setTowerHeight(builtLevel);
        targetCell.setHasDome(component.equals(ReducedComponent.DOME));
    }

    public int getPlayersCount() {
        return players.size();
    }

    public ReducedBoard getBoard() {
        return board;
    }
}
