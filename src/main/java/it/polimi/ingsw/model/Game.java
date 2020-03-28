package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.workers.Worker;

import java.util.List;

/**
 * This class is the game and its main purpose is to keep the general state of the match.
 * It provides methods to gain insights on the current state.
 */
public class Game {

    /**
     * Gets a list of the players that are playing this game.
     *
     * @return the list of players of this game
     */
    public List<Player> getPlayersList() {
        //TODO
        return null;
    }

    /**
     * Gets a list of the Turns of the last round.
     *
     * @return the list of the turns of the last round
     */
    public List<Turn> getLastRoundTurnsList() {
        //TODO
        return null;
    }

    /**
     * Gets the board.
     *
     * @return the board
     */
    public Board getBoard() {
        //TODO
        return null;
    }

    /**
     * Move a worker to a specific cell.
     * IMPORTANT! If the target cell is occupied, this function expects to be called again to perform the swap between
     * the workers, otherwise it will lead to an inconsistent state!
     *
     * @param worker the worker
     * @param cell   the cell
     */
    public void setWorkerCell(Worker worker, Cell cell) {
        if (!cell.getWorker().isPresent()) {
            worker.getCell().setNoWorker();
        }
        worker.setCell(cell);
        cell.setWorker(worker);
    }
}
