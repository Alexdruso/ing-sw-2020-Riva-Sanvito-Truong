package it.polimi.ingsw.client.reducedmodel;

import it.polimi.ingsw.utils.networking.transmittables.ReducedComponent;
import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;
import it.polimi.ingsw.utils.networking.transmittables.ReducedWorkerID;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * A representation of the game, reduced with respect to the server's Game to contain only the information required by the client.
 */
public class ReducedGame {
    private final List<ReducedPlayer> players;
    private ReducedTurn turn;
    private final ReducedBoard board;

    /**
     * Instantiates a new Reduced game.
     *
     * @param players the players
     */
    public ReducedGame(Collection<ReducedPlayer> players) {
        this.players = new ArrayList<>(players);
        board = new ReducedBoard();
    }

    /**
     * Gets the list of players of the game.
     *
     * @return the players list
     */
    public List<ReducedPlayer> getPlayersList() {
        return new ArrayList<>(players);
    }

    /**
     * Gets a player from their nickname.
     *
     * @param nickname the nickname of the player to retrieve
     * @return the player; if no player matches the nickname, Optional.empty() is returned
     */
    public Optional<ReducedPlayer> getPlayer(String nickname) {
        return players.stream().filter(x -> x.getNickname().equals(nickname)).findFirst();
    }

    /**
     * Gets a player from their ReducedUser.
     *
     * @param user the ReducedUser of the player to retrieve
     * @return the player; if no player matches the ReducedUser, Optional.empty() is returned
     */
    public Optional<ReducedPlayer> getPlayer(ReducedUser user) {
        return getPlayer(user.getNickname());
    }

    /**
     * Gets the current turn.
     *
     * @return the current turn
     */
    public ReducedTurn getTurn() {
        return turn;
    }

    /**
     * Sets the current turn.
     *
     * @param turn the current turn
     */
    public void setTurn(ReducedTurn turn) {
        this.turn = turn;
    }

    /**
     * Add a worker to the game.
     *
     * @param user        the user whose the worker belongs
     * @param workerID    the worker id
     * @param targetCellX the x coordinate of the cell in which to place the worker
     * @param targetCellY the y coordinate of the cell in which to place the worker
     */
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
     * Remove a worker from the game.
     *
     * @param user     the user whose worker is to be removed
     * @param workerID the worker id
     * @param cellX    the x coordinate of the cell in which the worker to be removed is placed
     * @param cellY    the y coordinate of the cell in which the worker to be removed is placed
     */
    public void removeWorker(ReducedUser user, ReducedWorkerID workerID, int cellX, int cellY) {
        getBoard().getCell(cellX, cellY).setNoWorker();
        getPlayer(user).ifPresent(
                player -> player.removeWorker(workerID)
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

    /**
     * Builds a component in a cell.
     *
     * @param targetCellX the x coordinate of the cell in which to build the component
     * @param targetCellY the y coordinate of the cell in which to build the component
     * @param component   the component to build
     * @param builtLevel  the level on which to build the component
     */
    public void buildComponentInCell(int targetCellX, int targetCellY, ReducedComponent component, int builtLevel) {
        ReducedCell targetCell = getBoard().getCell(targetCellX, targetCellY);
        targetCell.setTowerHeight(builtLevel);
        targetCell.setHasDome(component.equals(ReducedComponent.DOME));
    }

    /**
     * Gets the players count.
     *
     * @return the players count
     */
    public int getPlayersCount() {
        return players.size();
    }

    /**
     * Gets the board.
     *
     * @return the board
     */
    public ReducedBoard getBoard() {
        return board;
    }
}
