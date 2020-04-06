package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Component;
import it.polimi.ingsw.model.gods.God;
import it.polimi.ingsw.model.turnstates.InvalidTurnStateException;
import it.polimi.ingsw.utils.playercommands.PlayerBuildCommand;
import it.polimi.ingsw.utils.playercommands.PlayerMoveCommand;
import it.polimi.ingsw.utils.playercommands.PlayerSkipCommand;
import it.polimi.ingsw.model.workers.Worker;

import java.util.*;

/**
 * This class is the game and its main purpose is to keep the general state of the match.
 * It provides methods to gain insights on the current state.
 */
public class Game {
    /**
     * The number of players of the game
     */
    private final int NUMBER_OF_PLAYERS;

    /**
     * The Turn object representing the current game turn
     */
    private Turn currentTurn;

    /**
     * The Board object of the game
     */
    private Board board;

    /**
     * The mapping from the User to its relative Player instace
     */
    private Map<User, Player> subscribedUsers;

    /**
     * The last round of turns, ordered by oldest to newest.
     */
    private LinkedList<Turn> lastRound;

    /**
     * The class constructor
     */
    public Game(int numberOfPlayers){
        NUMBER_OF_PLAYERS = numberOfPlayers;
        subscribedUsers = new LinkedHashMap<>();
        lastRound = new LinkedList<>();
    }

    /**
     * This method takes the nickname and the divinity of a player and creates the Player and User instances.
     * The two instances are added to the subscribedUsers list attribute.
     * @param nickname a String representing the name chosen by the player for the game
     * @param god the God instance chosen by the player
     * @return the User instance representing the player
     */
    public User subscribeUser(String nickname, God god){
        if(subscribedUsers.size() == NUMBER_OF_PLAYERS){
            //This means that adding one will get us over the limit
            throw new IllegalStateException("Too many players");
        }
        Player player = new Player(nickname);
        player.setGod(god);
        User user = new User(nickname);
        subscribedUsers.put(user, player);
        return user;
    }

    /**
     * Gets a List of the players that are playing the game.
     * The order provided is the same as the playing order, from first to last
     *
     * @return the List of players of this game
     */
    public List<Player> getPlayersList() {
        return new ArrayList<Player>(subscribedUsers.values());
    }

    /**
     * Gets a list of the Turns of the last round.
     *
     * @return the list of the turns of the last round
     */
    public List<Turn> getLastRoundTurnsList() {
        return (List<Turn>) lastRound.clone();
    }

    /**
     * Adds new turn to the round, replacing the precedent turn belonging to the player
     * @param player the player that is playing the turn
     */
    public Turn addNewTurn(Player player){
        Turn turn = new Turn(this, player);
        if(lastRound.size() < NUMBER_OF_PLAYERS){
            lastRound.addLast(turn);
        } else {
            Collections.rotate(lastRound, -1);
            lastRound.removeLast();
            lastRound.addLast(turn);
            currentTurn = lastRound.getLast();
        }
        return turn;
    }

    /**
     * Gets the board.
     *
     * @return the board
     */
    public Board getBoard() {
        return board;
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
        if (cell.getWorker().isEmpty()) {
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
        Cell sourceCell = board.getCell(command.sourceCellX, command.sourceCellY);
        Cell targetCell = board.getCell(command.targetCellX, command.targetCellY);
        Player player = subscribedUsers.get(command.user);
        Worker worker = player.getWorkerByID(command.performer);
        if(sourceCell.getWorker().isEmpty()
                || !sourceCell.getWorker().get().equals(worker)) {
            // Sanity check failed: illegal move!
            return false;
        }
        //The target cell is not available for movement
        return currentTurn.canMoveTo(worker, targetCell);
    }

    /**
     * Executes the PlayerMoveCommand
     * @param command the command to be executed
     */
    public void move(PlayerMoveCommand command) {
        Cell targetCell = board.getCell(command.targetCellX, command.targetCellY);
        Player player = subscribedUsers.get(command.user);
        Worker worker = player.getWorkerByID(command.performer);
        try {
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
        Cell targetCell = board.getCell(command.targetCellX, command.targetCellY);
        User user = command.user;
        Worker worker = subscribedUsers.get(user).getWorkerByID(command.performer);
        if(!worker.getPlayer().equals(currentTurn.getPlayer())){
            //The worker does not belong to the active player
            return false;
        }
        if(command.component == Component.BLOCK) {
            return currentTurn.canBuildBlockIn(worker, targetCell);
        } else {
            return currentTurn.canBuildDomeIn(worker, targetCell);
        }
    }

    /**
     * Executes the PlayerBuildCommand
     * @param command the command to be executed
     */
    public void build(PlayerBuildCommand command) {
        Cell targetCell = board.getCell(command.targetCellX, command.targetCellY);
        User user = command.user;
        Worker worker = subscribedUsers.get(user).getWorkerByID(command.performer);
        try{
            if(command.component == Component.BLOCK){
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
