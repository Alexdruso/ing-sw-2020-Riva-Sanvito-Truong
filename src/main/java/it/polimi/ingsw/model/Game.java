package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Component;
import it.polimi.ingsw.model.gods.GodCard;
import it.polimi.ingsw.model.turnstates.InvalidTurnStateException;
import it.polimi.ingsw.model.workers.Worker;
import it.polimi.ingsw.model.workers.WorkerID;
import it.polimi.ingsw.observer.LambdaObservable;
import it.polimi.ingsw.utils.messages.*;
import it.polimi.ingsw.utils.networking.Transmittable;
import it.polimi.ingsw.utils.structures.BidirectionalLinkedHashMap;
import it.polimi.ingsw.utils.structures.BidirectionalMap;

import java.util.*;

/**
 * This class is the game and its main purpose is to keep the general state of the match.
 * It provides methods to gain insights on the current state.
 */
public class Game extends LambdaObservable<Transmittable> {
    /**
     * The number of maximum players of the game
     */
    private final int MAX_NUMBER_OF_PLAYERS;
    /**
     * The Board object of the game
     */
    private final Board board;
    /**
     * The mapping from the User to its relative Player instance
     */
    private final BidirectionalMap<User, Player> subscribedUsers;
    /**
     * The participating players, in order
     */
    private final LinkedList<Player> players;
    /**
     * The last round of turns, ordered by oldest to newest.
     */
    private final LinkedList<Turn> lastRound;
    /**
     * The Turn object representing the current game turn
     */
    private Turn currentTurn;
    /**
     * The list of all the gods in game
     */
    private final List<GodCard> availableGods = new LinkedList<>();
    /**
     * The current state of the game
     */
    private GameState gameState;

    /**
     * All the possible states of the game
     */
    enum GameState {
        /**
         * The first state
         */
        START_SETUP,
        /**
         * Waiting for a gods list
         */
        ASK_GODS_LIST,
        /**
         * Setting each player's god
         */
        SET_GODS,
        /**
         * The last state of the Game, possible just after a win or a draw
         */
        END_GAME
    }

    /**
     * The class constructor
     *
     * @param numberOfPlayers the number of players
     */
    public Game(int numberOfPlayers) {
        MAX_NUMBER_OF_PLAYERS = numberOfPlayers;
        subscribedUsers = new BidirectionalLinkedHashMap<>();
        players = new LinkedList<>();
        lastRound = new LinkedList<>();
        board = new Board();
        gameState = GameState.START_SETUP;
    }

    /**
     * This method takes the user and creates the Player.
     * The two instances are added to the subscribedUsers list attribute.
     *
     * @param user the representation of the user
     */
    public void subscribeUser(User user) {
        if (subscribedUsers.size() == MAX_NUMBER_OF_PLAYERS) {
            //This means that adding one will get us over the limit
            throw new IllegalStateException("Too many players");
        }
        Player player = new Player(user.nickname);
        subscribedUsers.put(user, player);
        players.add(player);
    }

    /**
     * This method removes the Player from the game by its User.
     * This deletes the entry from the subscribedUsers map and decreases the number of players in game
     *
     * @param user the user to remove from the game
     */
    public void unsubscribeUser(User user) {
        if (subscribedUsers.size() == 0 || !subscribedUsers.containsKey(user)) {
            throw new IllegalArgumentException("No such user");
        }
        subscribedUsers.removeByKey(user);
        players.removeIf(player -> player.getNickname().equals(user.nickname));
    }

    /**
     * Gets a List of the players that are currently still playing the game. (i.e. they've not lost yet)
     * The order provided is the same as the playing order, from first to last
     *
     * @return the List of players of this game
     */
    public List<Player> getPlayersList() {
        return new ArrayList<>(subscribedUsers.values());
    }

    /**
     * Gets a list of the Turns of the last round.
     *
     * @return the list of the turns of the last round
     */
    public List<Turn> getLastRoundTurnsList() {
        return new LinkedList<>(lastRound);
    }

    /**
     * Adds new turn to the round, replacing the precedent turn belonging to the player
     * This method automatically resizes the lastRound Map in order to match the number
     * of players still in the game.
     *
     * @param player the player that is playing the turn
     * @return the turn
     */
    public Turn addNewTurn(Player player) {
        Turn turn = new Turn(this, player);
        if (lastRound.size() < MAX_NUMBER_OF_PLAYERS) {
            lastRound.addLast(turn);
        } else {
            Collections.rotate(lastRound, -1);
            if (!getPlayersList().contains(lastRound.getLast().getPlayer())) {
                //Make the lastRound list shorter to match the number of players
                lastRound.removeLast();
            }
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
        int startX = worker.getCell().getX();
        int startY = worker.getCell().getY();
        if (cell.getWorker().isEmpty()) {
            worker.getCell().setNoWorker();
        }
        worker.setCell(cell);
        cell.setWorker(worker);
        //notify the move action
        notify(
                new ServerMoveMessage(
                        this.subscribedUsers.getKeyFromValue(players.peek()).toReducedUser(),
                        startX,
                        startY,
                        cell.getX(),
                        cell.getY(),
                        worker.getWorkerID()));
    }

    /**
     * Builds a component on a cell on behalf of the worker.
     *
     * @param worker     the building worker
     * @param cell       the cell to build on
     * @param component  the component to be built
     * @param builtLevel the built level
     */
    public void buildInCell(Worker worker, Cell cell, Component component, int builtLevel) {
        cell.getTower().placeComponent(component);
        //notify the build action
        notify(
                new ServerBuildMessage(
                        this.subscribedUsers.getKeyFromValue(players.peek()).toReducedUser(),
                        cell.getX(),
                        cell.getY(),
                        component,
                        builtLevel,
                        worker.getWorkerID()));
    }

    /**
     * Checks if the PlayerMoveCommand can be executed
     *
     * @param sourceCellX The x coordinate of the cell from which the worker moved
     * @param sourceCellY The y coordinate of the cell from which the worker moved
     * @param targetCellX The x coordinate of the cell to which the worker moved
     * @param targetCellY The y coordinate of the cell to which the worker moved
     * @param performer   The worker who performed the move
     * @param user        the user that triggered the command
     * @return true if the command is valid, false otherwise
     */
    public boolean isValidMove(int sourceCellX, int sourceCellY,
                               int targetCellX, int targetCellY,
                               WorkerID performer, User user) {
        Cell sourceCell = board.getCell(sourceCellX, sourceCellY);
        Cell targetCell = board.getCell(targetCellX, targetCellY);
        Player player = subscribedUsers.getValueFromKey(user);
        Worker worker = player.getWorkerByID(performer);
        if (sourceCell.getWorker().isEmpty()
                || (sourceCell.getWorker().isPresent() && !sourceCell.getWorker().get().equals(worker))) {
            // Sanity check failed: illegal move!
            return false;
        }
        //The target cell is not available for movement
        return currentTurn.getPlayer().equals(player)
                && currentTurn.canMoveTo(worker, targetCell);
    }

    /**
     * Executes the PlayerMoveCommand
     *
     * @param targetCellX The x coordinate of the cell to which the worker moved
     * @param targetCellY The y coordinate of the cell to which the worker moved
     * @param performer   The worker who performed the move
     * @param user        the user that triggered the command
     */
    public void move(int targetCellX, int targetCellY,
                     WorkerID performer, User user) {
        Cell targetCell = board.getCell(targetCellX, targetCellY);
        Player player = subscribedUsers.getValueFromKey(user);
        Worker worker = player.getWorkerByID(performer);
        try {
            currentTurn.moveTo(worker, targetCell);
        } catch (InvalidTurnStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the PlayerBuildCommand can be executed
     *
     * @param targetCellX The x coordinate of the cell on which the worker built
     * @param targetCellY The y coordinate of the cell on which the worker built
     * @param component   The component built on the cell
     * @param performer   The worker who performed the build
     * @param user        the user that triggered the command
     * @return true if the command is valid, false otherwise
     */
    public boolean isValidBuild(int targetCellX, int targetCellY,
                                Component component, WorkerID performer,
                                User user) {
        Cell targetCell = board.getCell(targetCellX, targetCellY);
        Worker worker = subscribedUsers.getValueFromKey(user).getWorkerByID(performer);
        if (!worker.getPlayer().equals(currentTurn.getPlayer())) {
            //The worker does not belong to the active player
            return false;
        }
        if (component == Component.BLOCK) {
            return currentTurn.canBuildBlockIn(worker, targetCell);
        } else {
            return currentTurn.canBuildDomeIn(worker, targetCell);
        }
    }

    /**
     * Executes the PlayerBuildCommand
     *
     * @param targetCellX The x coordinate of the cell on which the worker built
     * @param targetCellY The y coordinate of the cell on which the worker built
     * @param component   The component built on the cell
     * @param performer   The worker who performed the build
     * @param user        the user that triggered the command
     */
    public void build(int targetCellX, int targetCellY,
                      Component component, WorkerID performer,
                      User user) {
        Cell targetCell = board.getCell(targetCellX, targetCellY);
        Worker worker = subscribedUsers.getValueFromKey(user).getWorkerByID(performer);
        try {
            if (component == Component.BLOCK) {
                currentTurn.buildBlockIn(worker, targetCell);
            } else {
                currentTurn.buildDomeIn(worker, targetCell);
            }
        } catch (InvalidTurnStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the PlayerSkipCommand can be executed
     *
     * @param user the user that triggered the command
     * @return true if the command is valid, false otherwise
     */
    public boolean isValidSkip(User user) {
        return currentTurn.getPlayer().equals(subscribedUsers.getValueFromKey(user))
                && currentTurn.isSkippable();
    }

    /**
     * Executes the PlayerSkipCommand
     *
     * @param user the user that triggered the command
     * @throws UnsupportedOperationException the unsupported operation exception
     */
    public void skip(User user) throws UnsupportedOperationException {
        currentTurn.changeState();
    }

    /**
     * First method to be called by the match, sends the request of a god sub list to the player who created the game
     */
    public void setup() {
        User firstUser = subscribedUsers.getKeyFromValue(players.peek());
        List<ReducedGod> godsList = new ArrayList<>();
        for (GodCard godCard : GodCard.values()) {
            godsList.add(new ReducedGod(godCard.getGod().getName()));
        }

        notify(
                new ServerAskGodsFromListMessage(
                        firstUser.toReducedUser(),
                        godsList
                )
        );
    }

    /**
     * Signals the match to terminate
     */
    public void draw() {
        gameState = GameState.END_GAME;
    }

    /**
     * Checks if the Gods choice is fine
     *
     * @param chosenGods the list of gods chosen by the player
     * @param user       the user of the player
     * @return true if the command is valid, false otherwise
     */
    public boolean isValidGodsChoice(List<GodCard> chosenGods, User user) {
        Player player = subscribedUsers.getValueFromKey(user);
        return gameState == GameState.ASK_GODS_LIST //check right state
                && player.equals(players.peek()) //check the player is right
                && chosenGods.size() == players.size() //check right number of gods
                && (new HashSet<>(chosenGods)).size() == players.size(); //check no duplicates
    }

    /**
     * Sets the available gods list
     *
     * @param chosenGods the list of gods chosen by the player
     * @param user       the user of the player
     */
    public void setAvailableGodsList(List<GodCard> chosenGods, User user) {
        availableGods.addAll(chosenGods);
        //rotate the player
        Player player = players.poll();
        players.add(player);
        //change state
        gameState = GameState.SET_GODS;
        //send god request
        notify(new ServerAskGodFromListMessage(subscribedUsers.getKeyFromValue(players.peek()).toReducedUser(), chosenGods));
    }

    public boolean isValidGodChoice(ClientChooseGodMessage command, User user) {
        return false;
    }

    public void setGod(ClientChooseGodMessage command, User user) {
    }

    public boolean isValidStartPlayerChoice() {
        return false;
    }

    public void setStartPlayer() {
    }

    public boolean isValidPositioning(ClientSetWorkerStartPositionMessage command, User user) {
        return false;
    }

    public void setWorkerPosition(ClientSetWorkerStartPositionMessage command, User user) {
    }

    /*
    Getters and setters section
     */

    /**
     * Returns true if the game is active.
     *
     * @return true if the game is active
     */
    public boolean isActive() {
        return gameState != GameState.END_GAME;
    }
}
