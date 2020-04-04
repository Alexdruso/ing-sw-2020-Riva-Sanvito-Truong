package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.turnstates.InvalidTurnStateException;
import it.polimi.ingsw.utils.playercommands.PlayerBuildCommand;
import it.polimi.ingsw.utils.playercommands.PlayerMoveCommand;
import it.polimi.ingsw.utils.playercommands.PlayerSkipCommand;
import it.polimi.ingsw.model.workers.Worker;

import java.util.List;
import java.util.Optional;

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
        Optional<Worker> sourceCellWorker = sourceCell.getWorker();
        if(!sourceCellWorker.isPresent() || !sourceCellWorker.get().getPlayer().equals(command.getPlayer())) {
            // Sanity check failed: illegal move!
            // TODO: we could even raise an exception here... Let's think about it
            return false;
        }
        if (!currentTurn.canMoveTo(sourceCellWorker.get(), targetCell)){
            //The target cell is not available for movement
            return false;
        }
        return true;
    }

    /**
     * Executes the PlayerMoveCommand
     * @param command the command to be executed
     */
    public void move(PlayerMoveCommand command) {
        Worker worker = command.getPerformer();
        Cell targetCell = command.getTargetCell();
        try{
            currentTurn.moveTo(worker, targetCell);
        } catch (InvalidTurnStateException e){
            e.printStackTrace();
        }
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
            // TODO: we could even raise an exception here... Let's think about it
            return false;
        }
        //I'd like this to be more explicit: getComponent should return a Component
        //instead of a buildable
        if(command.getComponent().isTargetable()) {
            if (!currentTurn.canBuildBlockIn(worker, targetCell)) {
                return false;
            }
        } else {
            if(!currentTurn.canBuildDomeIn(worker, targetCell)){
                return false;
            }
        }
        return true;
    }

    /**
     * Executes the PlayerBuildCommand
     * @param command the command to be executed
     */
    public void build(PlayerBuildCommand command) {
        Cell targetCell = command.getTargetCell();
        Worker worker = command.getPerformer();
        try{
            if(command.getComponent().isTargetable()){
                currentTurn.buildBlockIn(worker, targetCell);
            } else {
                currentTurn.buildDomeIn(worker, targetCell);
            }
        } catch (InvalidTurnStateException e){
            e.printStackTrace();
        }
    }

    /**
     * Checks if the PlayerSkipCommand can be executed
     * @param command the command to be checked
     * @return true if the command is valid, false otherwise
     */
    public boolean isValidSkip(PlayerSkipCommand command){
        return currentTurn.isSkippable();
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
