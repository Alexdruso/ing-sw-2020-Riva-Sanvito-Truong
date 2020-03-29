package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Dome;
import it.polimi.ingsw.model.board.Block;
import it.polimi.ingsw.model.playercommands.PlayerBuildCommand;
import it.polimi.ingsw.model.playercommands.PlayerMoveCommand;
import it.polimi.ingsw.model.playercommands.PlayerSkipCommand;
import it.polimi.ingsw.model.workers.Worker;

import java.util.List;

/**
 * This class is the game and its main purpose is to keep the general state of the match.
 * It provides methods to gain insights on the current state.
 */
public class Game {

    /**
     * The Turn object representing the current game turn
     */
    private Turn currentTurn;

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

    /**
     * Checks if the PlayerMoveCommand can be executed
     * @param command the command to be checked
     * @return true if the command is valid, false otherwise
     */
    public boolean isValidMove(PlayerMoveCommand command){
        Cell sourceCell = command.getSourceCell();
        Cell targetCell = command.getTargetCell();
        if(!sourceCell.getWorker().isPresent()) {
            //There is no worker in the source cell
            return false;
        }
        if(!sourceCell.getWorker().get().getPlayer().equals(command.getPlayer())){
            //The worker in the source cell does not belong to the active player
            return false;
        }
        if(!currentTurn.getWorkerWalkableCells(command.getPerformer()).getPosition(targetCell)){
            //The target cell is not available for movement
            return false;
        }
        return true;
    }

    /**
     * Executes the PlayerMoveCommand
     * @param command the command to be executed
     */
    public void move(PlayerMoveCommand command) throws UnsupportedOperationException{
        //TODO
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if the PlayerBuildCommand can be executed
     * @param command the command to be checked
     * @return true if the command is valid, false otherwise
     */
    public boolean isValidBuild(PlayerBuildCommand command){
        Cell targetCell = command.getTargetCell();
        Worker worker = command.getPerformer();
        if(!worker.getPlayer().equals(currentTurn.getPlayer())){
            //The worker does not belong to the active player
            return false;
        }
        //I'd like this to be more explicit: getComponent should return a Component
        //instead of a buildable
        if(command.getComponent().isTargetable()) {
            if (!currentTurn.getWorkerBlockBuildableCells(worker).getPosition(targetCell)) {
                return false;
            }
        } else {
            if(!currentTurn.getWorkerDomeBuildableCells(worker).getPosition(targetCell)){
                return false;
            }
        }
        return true;
    }

    /**
     * Executes the PlayerBuildCommand
     * @param command the command to be executed
     */
    public void build(PlayerBuildCommand command) throws UnsupportedOperationException{
        //TODO
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if the PlayerSkipCommand can be executed
     * @param command the command to be checked
     * @return true if the command is valid, false otherwise
     */
    public boolean isValidSkip(PlayerSkipCommand command){
        //TODO
        return false;
    }

    /**
     * Executes the PlayerSkipCommand
     * @param command the command to be executed
     */
    public void skip(PlayerSkipCommand command) throws UnsupportedOperationException{
        //TODO
        throw new UnsupportedOperationException();
    }
}
