package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Component;
import it.polimi.ingsw.model.board.TargetCells;
import it.polimi.ingsw.model.gods.God;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * This class is the game and its main purpose is to keep the general state of the match.
 * It provides methods to gain insights on the current state.
 */
public class Game extends LambdaObservable<Transmittable> {
    private static final Logger LOGGER = Logger.getLogger(Game.class.getName());

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
     * The list of all the gods in game
     */
    private final List<God> availableGods = new LinkedList<>();
    /**
     * The Turn object representing the current game turn
     */
    private Turn currentTurn;
    /**
     * The current state of the game
     */
    private GameState gameState;

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
        return new ArrayList<>(players);
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
     * Removes the worker from the cell
     *
     * @param worker
     */
    public void removeWorkerFromCell(Worker worker) {
        Cell cell = worker.getCell();
        worker.getCell().setNoWorker();
        worker.setCell(null);

        notify(
                new ServerRemoveWorkerMessage(
                        subscribedUsers.getKeyFromValue(players.peek()).toReducedUser(),
                        worker.getWorkerID(),
                        cell.getX(),
                        cell.getY()
                )
        );
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

        Optional<Worker> workerOnCell = sourceCell.getWorker();
        return gameState == GameState.PLAY
                && workerOnCell.filter(value -> value.equals(worker)
                && currentTurn.getPlayer().equals(player)
                && currentTurn.canMoveTo(worker, targetCell)).isPresent();
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
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
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
        if (gameState != GameState.PLAY
                || !worker.getPlayer().equals(currentTurn.getPlayer())) {
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
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     * Checks if the PlayerSkipCommand can be executed
     *
     * @param user the user that triggered the command
     * @return true if the command is valid, false otherwise
     */
    public boolean isValidSkip(User user) {
        return gameState == GameState.PLAY
                && currentTurn.getPlayer().equals(subscribedUsers.getValueFromKey(user))
                && currentTurn.isSkippable();
    }

    /**
     * Executes the PlayerSkipCommand
     *
     * @throws UnsupportedOperationException the unsupported operation exception
     */
    public void skip() {
        currentTurn.changeState();
    }

    /**
     * Checks if the player is in game
     *
     * @param user the user asking if he is in game
     * @return true, unless the player lost at some point in the game
     */
    public boolean isInGame(User user) {
        Player player = subscribedUsers.getValueFromKey(user);

        return players.contains(player);
    }

    /**
     * Signals the match to terminate
     */
    public void draw() {
        gameState = GameState.END_GAME;
    }

    /**
     * First method to be called by the match, sends the request of a god sub list to the player who created the game
     */
    public void setup() {
        //signals the setup start
        ServerStartSetupMatchMessage serverStartSetupMatchMessage = new ServerStartSetupMatchMessage(
                subscribedUsers.getBackwardMap().values().toArray((new User[subscribedUsers.size()])));
        notify(serverStartSetupMatchMessage);

        //sens the request of the gods sub list
        User firstUser = subscribedUsers.getKeyFromValue(players.peek());
        List<ReducedGod> godsList = Arrays.stream(GodCard.values()).map(GodCard::getGod).map(God::getName)
                .map(ReducedGod::new).collect(Collectors.toList());

        gameState = GameState.ASK_GODS_LIST;
        notify(
                new ServerAskGodsFromListMessage(
                        firstUser.toReducedUser(),
                        godsList
                )
        );
    }

    /**
     * Checks if the Gods choice is fine
     *
     * @param chosenGods the list of gods chosen by the player
     * @param user       the user of the player
     * @return true if the command is valid, false otherwise
     */
    public boolean isValidGodsChoice(List<ReducedGod> chosenGods, User user) {
        Player player = subscribedUsers.getValueFromKey(user);
        return gameState == GameState.ASK_GODS_LIST //check right state
                && player.equals(players.peek()) //check the player is right
                && chosenGods.size() == players.size() //check right number of gods
                && (new HashSet<>(chosenGods)).size() == players.size() //Check no duplicates
                && Arrays.stream(GodCard.values()).map(Enum::toString).collect(Collectors.toList()) //Check right gods
                .containsAll(chosenGods.stream().map(x -> x.name.toUpperCase()).collect(Collectors.toList()));
    }

    /**
     * Sets the available gods list
     *
     * @param chosenGods the list of gods chosen by the player
     */
    public void setAvailableGodsList(List<ReducedGod> chosenGods) {
        availableGods.addAll(
                chosenGods.stream().map(
                        reducedGod -> GodCard.valueOf(reducedGod.name.toUpperCase()).getGod()
                ).collect(Collectors.toList())
        );
        //rotate the player
        Player player = players.poll();
        players.add(player);
        //change state
        gameState = GameState.SET_GODS;
        //send god request
        notify(
                new ServerAskGodFromListMessage(
                        subscribedUsers.getKeyFromValue(players.peek()).toReducedUser(),
                        chosenGods
                )
        );
    }

    /**
     * Checks if the god choice is fine
     *
     * @param reducedGod the chosen god
     * @param user       the user of the player
     * @return true if the command is valid, false otherwise
     */
    public boolean isValidGodChoice(ReducedGod reducedGod, User user) {
        Player player = subscribedUsers.getValueFromKey(user);
        return gameState == GameState.SET_GODS //check right state
                && player.equals(players.peek()) //check right player
                && availableGods.stream().map(God::getName) //check god is in game
                .anyMatch(x -> x.equalsIgnoreCase(reducedGod.name))
                && players.stream().filter(x -> x.getGod() != null).map(x -> x.getGod().getName()) //check god not already taken
                .noneMatch(x -> x.equalsIgnoreCase(reducedGod.name));
    }

    /**
     * This method sets a specific chosen god to a user.
     * If there is just one God left after the choice, this method changes the game state to SET_START_PLAYER
     * and notifies the request message, while assigning the last god to the remaining player.
     *
     * @param reducedGod the chosen god
     * @param user       teh user of the player
     */
    public void setGod(ReducedGod reducedGod, User user) {
        Player player = subscribedUsers.getValueFromKey(user);
        GodCard god = GodCard.valueOf(reducedGod.name.toUpperCase());

        player.setGod(god.getGod());
        notify(new ServerSetGodMessage(reducedGod, user.toReducedUser()));
        //change pseudo turn
        players.poll();
        players.add(player);
        //now check if just one player is missing god
        //checks which gods are already taken
        List<God> takenGods = players.stream().filter(x -> x.getGod() != null)
                .map(Player::getGod).collect(Collectors.toList());
        //checks gods left available
        List<God> remainingGods = new ArrayList<>(availableGods);
        remainingGods.removeAll(takenGods);
        if (remainingGods.size() == 1) {
            gameState = GameState.SET_START_PLAYER;
            //sets the last god to the player
            remainingGods.stream().findFirst().ifPresent(lastGod -> {
                assert players.peek() != null;
                players.peek().setGod(lastGod);
                notify(
                        new ServerSetGodMessage(
                                new ReducedGod(lastGod.getName()),
                                subscribedUsers.getKeyFromValue(players.peek()).toReducedUser()
                        )
                );
            });
            //sends a first player request
            notify(new ServerAskStartPlayerMessage(subscribedUsers.getKeyFromValue(players.peek()).toReducedUser()));
        } else {
            gameState = GameState.SET_GODS;
            //sends gods request
            notify(new ServerAskGodFromListMessage(
                    subscribedUsers.getKeyFromValue(players.peek()).toReducedUser(),
                    remainingGods.stream().map(x -> new ReducedGod(x.toString())).collect(Collectors.toList())));
        }
    }

    /**
     * Checks if the choice is fine
     *
     * @param startPlayer the player who should start
     * @param user        the user of the player choosing
     * @return true if the command is valid, false otherwise
     */
    public boolean isValidStartPlayerChoice(ReducedUser startPlayer, User user) {
        Player player = subscribedUsers.getValueFromKey(user);
        return gameState == GameState.SET_START_PLAYER //check right state
                && player.equals(players.peek()) //check it's the player's turn
                && players.stream().map(Player::getNickname) //check start player is possible
                .anyMatch(x -> x.equals(startPlayer.nickname));
    }

    /**
     * Sets the first player to set the workers, move and build
     *
     * @param startPlayer the player who should start
     */
    public void setStartPlayer(ReducedUser startPlayer) {
        //rotate queue till we get the right player
        while (!players.peek().getNickname().equals(startPlayer.nickname)) {
            Player player = players.poll();
            players.add(player);
        }
        //change state
        gameState = GameState.SET_WORKER_POSITION;
        //send message of start positioning
        notify(new ServerAskWorkerPositionMessage(
                        WorkerID.WORKER1, startPlayer, (new TargetCells()).setAllTargets(true).toReducedTargetCells()
                )
        );
    }

    /**
     * Checks if the choice is gine
     *
     * @param targetCellX the x coordinate of the cell to which the worker shall be positioned
     * @param targetCellY the y coordinate of the cell to which the worker shall be positioned
     * @param performer   the worker
     * @param user        the user of the player choosing
     * @return true if the command is valid, false otherwise
     */
    public boolean isValidPositioning(int targetCellX, int targetCellY, WorkerID performer, User user) {
        Player player = subscribedUsers.getValueFromKey(user);
        Cell targetCell = board.getCell(targetCellX, targetCellY);
        Worker worker = player.getWorkerByID(performer);
        return gameState == GameState.SET_WORKER_POSITION //check right state
                && player.equals(players.peek()) //check it's player's turn
                && Arrays.stream(player.getOwnWorkers()).filter(x -> x.getCell() == null) //check if it is the requested worker
                .findFirst().map(x -> x.getWorkerID() == performer).orElse(false)
                && worker.getCell() == null //check worker has no cell yet
                && targetCell.getWorker().isEmpty(); //check cell is not occupied
    }

    /**
     * Sets the worker first position
     *
     * @param targetCellX the x coordinate of the cell to which the worker shall be positioned
     * @param targetCellY the y coordinate of the cell to which the worker shall be positioned
     * @param performer   the worker
     * @param user        tje user of the player choosing
     */
    public void setWorkerPosition(int targetCellX, int targetCellY, WorkerID performer, User user) {
        Player player = subscribedUsers.getValueFromKey(user);
        Cell targetCell = board.getCell(targetCellX, targetCellY);
        Worker worker = player.getWorkerByID(performer);
        //set the position
        worker.setCell(targetCell);
        targetCell.setWorker(worker);
        //notify set position
        notify(new ServerSetWorkerStartPositionMessage(user.toReducedUser(), targetCellX, targetCellY, performer));
        //now check if any worker from the same player is left without a cell
        //generate the ReducedTargetCells
        TargetCells targetCells = (new TargetCells()).setAllTargets(true);
        players.stream().flatMap(p -> Arrays.stream(p.getOwnWorkers()))
                .filter(w -> w.getCell() != null).map(Worker::getCell)
                .forEach(x -> targetCells.setPosition(x, false));

        Arrays.stream(player.getOwnWorkers()).filter(x -> x.getCell() == null).findFirst().ifPresentOrElse(
                x -> {
                    //it's yet the user turn, he already has workers to position
                    gameState = GameState.SET_WORKER_POSITION;
                    notify(new ServerAskWorkerPositionMessage(
                                    x.getWorkerID(),
                                    user.toReducedUser(),
                                    targetCells.toReducedTargetCells()
                            )
                    );
                },
                () -> {
                    //change player turn
                    players.poll();
                    players.add(player);
                    //now check if the new first player has to position some workers
                    assert players.peek() != null;
                    Arrays.stream(players.peek().getOwnWorkers()).filter(y -> y.getCell() == null).findFirst()
                            .ifPresentOrElse(
                                    y -> {
                                        //there is some worker to be positioned
                                        gameState = GameState.SET_WORKER_POSITION;

                                        notify(new ServerAskWorkerPositionMessage(
                                                        y.getWorkerID(),
                                                        subscribedUsers.getKeyFromValue(players.peek()).toReducedUser(),
                                                        targetCells.toReducedTargetCells()
                                                )
                                        );
                                    },
                                    () -> {
                                        //no more workers to be positioned
                                        gameState = GameState.PLAY;
                                        notify(new ServerStartPlayMatchMessage());
                                        addNewTurn(players.peek());
                                    }
                            );
                }
        );
    }

    /**
     * Returns true if the game is active.
     *
     * @return true if the game is active
     */
    public boolean isActive() {
        return gameState != GameState.END_GAME;
    }

    //useful methods to handle interaction with the turn

    /**
     * Notifies an askMoveMessage retrieving information from the turn
     *
     * @param turn the turn asking the notify the ask move
     */
    public void notifyAskMove(Turn turn) {
        gameState = GameState.PLAY;
        notify(
                new ServerAskMoveMessage(
                        subscribedUsers.getKeyFromValue(turn.getPlayer()).toReducedUser(),
                        turn.isSkippable(),
                        turn.getAllowedWorkers().stream().map(Worker::getWorkerID).collect(Collectors.toList()),
                        turn.getWalkableCells().entrySet().stream()
                                .map(entry -> new AbstractMap.SimpleEntry<>(
                                                entry.getKey().getWorkerID(),
                                                entry.getValue().toReducedTargetCells()
                                        )
                                )
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
                                )
                )
        );
    }

    /**
     * notifies an askBuildMessage retrieving informations from the turn
     *
     * @param turn the turn asking to notify the ask build
     */
    public void notifyAskBuild(Turn turn) {
        gameState = GameState.PLAY;
        notify(
                new ServerAskBuildMessage(
                        subscribedUsers.getKeyFromValue(turn.getPlayer()).toReducedUser(),
                        turn.isSkippable(),
                        turn.getAllowedWorkers().stream().map(Worker::getWorkerID).collect(Collectors.toList()),
                        turn.getBlockBuildableCells().entrySet().stream()
                                .map(entry -> new AbstractMap.SimpleEntry<>(
                                                entry.getKey().getWorkerID(),
                                                entry.getValue().toReducedTargetCells()
                                        )
                                )
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)),
                        turn.getDomeBuildableCells().entrySet().stream()
                                .map(entry -> new AbstractMap.SimpleEntry<>(
                                                entry.getKey().getWorkerID(),
                                                entry.getValue().toReducedTargetCells()
                                        )
                                )
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                )
        );
    }

    /**
     * Triggers a default end of turn
     */
    public void triggerEndTurn() {
        gameState = GameState.PLAY;
        //rotate the players
        Player player = players.poll();
        players.add(player);
        //create a new turn
        addNewTurn(players.peek());
    }

    /**
     * Triggers a losing turn
     */
    public void triggerLosingTurn() {
        Player losingPlayer = players.peek();
        //removes all the workers of that player
        assert losingPlayer != null;
        Arrays.stream(losingPlayer.getOwnWorkers()).forEach(this::removeWorkerFromCell);
        //rotate the players removing the current turn player
        players.poll();
        //tell the player he lost
        notify(
                new ServerLoseGameMessage(
                        subscribedUsers.getKeyFromValue(losingPlayer).toReducedUser()
                )
        );
        //now check if the game is still going on
        if (players.size() == 1) {
            triggerWinningTurn();
        } else {
            gameState = GameState.PLAY;
            addNewTurn(players.peek());
        }
    }

    /**
     * Triggers a winning turn
     */
    public void triggerWinningTurn() {
        gameState = GameState.END_GAME;
        notify(
                new ServerWinGameMessage(
                        subscribedUsers.getKeyFromValue(players.peek()).toReducedUser()
                )
        );
    }

    /*
    Getters and setters section
     */

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
         * Setting the start player
         */
        SET_START_PLAYER,
        /**
         * Setting the workers initial position
         */
        SET_WORKER_POSITION,
        /**
         * entering the real game mode
         */
        PLAY,
        /**
         * The last state of the Game, possible just after a win or a draw
         */
        END_GAME
    }
}
