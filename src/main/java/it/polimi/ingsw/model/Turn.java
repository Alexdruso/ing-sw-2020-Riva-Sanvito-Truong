package it.polimi.ingsw.model;

import it.polimi.ingsw.model.actions.Action;
import it.polimi.ingsw.model.actions.BuildAction;
import it.polimi.ingsw.model.actions.MoveAction;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.TargetCells;
import it.polimi.ingsw.model.turnstates.InvalidTurnStateException;
import it.polimi.ingsw.model.turnstates.AbstractTurnState;
import it.polimi.ingsw.model.turnstates.TurnState;
import it.polimi.ingsw.model.workers.Worker;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class represents a single turn in the match.
 * It is implemented with a state pattern.
 * On initialization it must be bound to a specific player.
 */
public class Turn{

    /**
     * Reference to the game, which represents the data related to the model
     */
    private final Game game;

    /**
     * Reference to the player involved in the turn
     */
    private final Player player;

    /**
     * List of all the actions performed in the turn
     */
    private List<Action> performedActions;

    /**
     * Cells on which the worker can build a block
     */
    private Map<Worker, TargetCells> blockBuildableCells;

    /**
     * Cells on which the worker can build a dome
     */
    private Map<Worker, TargetCells> domeBuildableCells;

    /**
     * Cells the worker can be moved to
     */
    private Map<Worker, TargetCells> walkableCells;

    private Set<Worker> allowedWorkers;

    /**
     * Current state of the turn, part of state pattern
     */
    private AbstractTurnState currentState;

    /**
     * Next state of the turn, part of state pattern
     */
    private AbstractTurnState nextState;

    /**
     * This attribute holds if the turn is a winning or losing turn
     */
    private VictoryConditions winLoseCondition;

    /**
     * Anonymous class to represent the win, lose or neutral conditions of the turn
     */
    enum VictoryConditions{
        /**
         * Win victory conditions.
         */
        WIN,
        /**
         * Lose victory conditions.
         */
        LOSE,
        /**
         * Neutral victory conditions.
         */
        NEUTRAL
    }


    /**
     * Builder of the turn.
     * It binds to a specific game and player.
     * It initializes all the relevant structures and sets the first state, then calling .setup() on it
     * to decide the first actual turn.
     *
     * @param game   the game associated to the turn
     * @param player the player performing actions in the turn
     */
    public Turn(Game game, Player player) {
        this.game = game;
        this.player = player;
        this.winLoseCondition = VictoryConditions.NEUTRAL;
        this.setSkippable(false);

        this.performedActions = new LinkedList<Action>();
        this.blockBuildableCells = new HashMap<Worker, TargetCells>();
        this.domeBuildableCells = new HashMap<Worker, TargetCells>();
        this.walkableCells = new HashMap<Worker, TargetCells>();
        //start with empty set
        this.allowedWorkers = new HashSet<Worker>();
        //we use the first current state to prepare the turn for the first actual state
        this.currentState = TurnState.START.getTurnState();
        this.currentState.setup(this);
    }

    /**
     * Gets the game.
     *
     * @return the game
     */
    public Game getGame() {
        return game;
    }

    /**
     * This method provides a List of all the performed actions
     *
     * @return a list of all the performed actions in the turn
     */
    public List<Action> getPerformedAction(){
        return new LinkedList<Action>(this.performedActions);
    }

    /**
     * This method provides a List of all the performed builds
     *
     * @return a list of all the performed builds in the turn
     */
    public List<BuildAction> getBuilds(){
        return performedActions.stream()
                .filter(action -> action instanceof BuildAction)
                .map(action -> (BuildAction) action)
                .collect(Collectors.toList());
    }

    /**
     * This method provides a List of all the performed moves
     *
     * @return a list of all the performed moves in the turn
     */
    public List<MoveAction> getMoves(){
        return performedActions.stream()
                .filter(action -> action instanceof MoveAction)
                .map(action -> (MoveAction) action)
                .collect(Collectors.toList());
    }

    /**
     * Get player player.
     *
     * @return the player performing actions in the turn
     */
    public Player getPlayer(){
        return player;
    }

    /**
     * This method returns TargetCells related to Cells the Worker can build a block in
     *
     * @param worker the worker involved
     * @return TargetCells related to Cells the Worker can build a block in
     */
    public TargetCells getWorkerBlockBuildableCells(Worker worker) {
        return blockBuildableCells.get(worker);
    }

    /**
     * This method returns TargetCells related to Cells the Worker can build a dome in
     *
     * @param worker the worker involved
     * @return TargetCells related to Cells the Worker can build a dome in
     */
    public TargetCells getWorkerDomeBuildableCells(Worker worker) {
        return domeBuildableCells.get(worker);
    }

    /**
     * This method returns TargetCells related to Cells the Worker can walk in
     *
     * @param worker the worker involved
     * @return TargetCells related to Cells the Worker can walk in
     */
    public TargetCells getWorkerWalkableCells(Worker worker) {
        return walkableCells.get(worker);
    }

    /**
     * This method sets new block buildable cells for the worker
     *
     * @param worker the worker involved
     * @param targetCells TargetCells related to Cells the Worker can build a block in
     */
    public void setWorkerBlockBuildableCells(Worker worker, TargetCells targetCells){
        this.blockBuildableCells.put(worker, targetCells);
    }

    /**
     * This method sets new dome buildable cells for the worker
     *
     * @param worker the worker involved
     * @param targetCells TargetCells related to Cells the Worker can build a dome in
     */
    public void setWorkerDomeBuildableCells(Worker worker, TargetCells targetCells){
        this.domeBuildableCells.put(worker, targetCells);
    }

    /**
     * This method sets new walkable cells for the worker
     *
     * @param worker the worker involved
     * @param targetCells TargetCells related to Cells the Worker can walk in
     */
    public void setWorkerWalkableCells(Worker worker, TargetCells targetCells){
        this.walkableCells.put(worker, targetCells);
    }

    /**
     * Returns a set of the allowed workers
     * @return a set of workers allowed to perform the action
     */
    public Set<Worker> getAllowedWorkers(){
        return new HashSet<Worker>(this.allowedWorkers);
    }

    /**
     * Clears the workers allowed to perform operations on this turn.
     */
    public void clearAllowedWorkers(){
        allowedWorkers.clear();
    }

    /**
     * Allows a worker to perform operations on this turn.
     *
     * @param allowedWorker the worker to allow performing operations on this turn
     */
    public void addAllowedWorker(Worker allowedWorker){
        allowedWorkers.add(allowedWorker);
    }

    /**
     * Allows a collection of workers to perform operations on this turn.
     *
     * @param allowedWorkers the workers to allow performing operations on this turn
     */
    public void addAllowedWorkers(Collection<Worker> allowedWorkers){
        this.allowedWorkers.addAll(allowedWorkers);
    }

    /**
     * Allows an array of workers to perform operations on this turn.
     *
     * @param allowedWorkers the workers to allow performing operations on this turn
     */
    public void addAllowedWorkers(Worker[] allowedWorkers){
        this.allowedWorkers.addAll(Arrays.asList(allowedWorkers));
    }

    /**
     * This method checks if the turn is a winning turn
     *
     * @return true if the turn is a winning turn
     */
    public boolean isWinningTurn(){
        return this.winLoseCondition == VictoryConditions.WIN;
    }

    /**
     * This methods checks if the turn is a losing turn
     *
     * @return true if the turn is a winning turn
     */
    public boolean isLosingTurn(){
        return this.winLoseCondition == VictoryConditions.LOSE;
    }

    /**
     * Setter of next state
     *
     * @param nextState the state we want to move next
     */
    public void setNextState(AbstractTurnState nextState){
        this.nextState = nextState;
    }

    /**
     * This method sets the turn as a winning turn
     */
    public void setWinningTurn(){
        this.winLoseCondition = VictoryConditions.WIN;
    }

    /**
     * This method sets the turn as a losing turn
     */
    public void triggerLosingTurn(){
        this.winLoseCondition = VictoryConditions.LOSE;
        this.changeState();
    }

    /**
     * Sets current state to next state
     * Doing this it also resets all the structures representing the state of the turn, including targetCells,
     * skippable and allowedWorkers
     */
    public void changeState(){
        // if lose -> go to state LOSE
        if(this.isLosingTurn()) this.setNextState(TurnState.LOSE.getTurnState());

        // computeWinConditions();
        this.computeWinConditions();

        // if win -> go to state WIN
        if(this.isWinningTurn()) this.setNextState(TurnState.WIN.getTurnState());

        //initialization of target cells related to all workers
        for(Worker worker : this.player.getOwnWorkers()){
            this.setWorkerWalkableCells(worker,(new TargetCells()).setAllTargets(false));
            this.setWorkerDomeBuildableCells(worker,(new TargetCells()).setAllTargets(false));
            this.setWorkerWalkableCells(worker,(new TargetCells()).setAllTargets(false));
        }

        //Clear allowed workers
        this.clearAllowedWorkers();
        this.setSkippable(false);
        this.currentState = this.nextState;
        this.currentState.setup(this);
    }

    /**
     * Brings together the default and custom computation of
     * win conditions
     */
    private void computeWinConditions() {
        //qui il codice da Move::move

        // sets winning turn is there is a the worker is moved from level 2 to level 3
        List<MoveAction> moves = this.getMoves();
        if(!moves.isEmpty()){
            MoveAction lastMove = moves.get(moves.size()-1);
            if(lastMove.getTargetLevel() == 3 && lastMove.getSourceLevel() < 3){
                this.setWinningTurn();
            }
        }

        //Custom win conditions
        this.getPlayer().getTurnEventsManager().processWinConditionEvents(this);
    }

    /**
     * This attribute signals if the current state is skippable
     */
    private boolean skippable;

    /**
     * Sets current state as skippable or not
     *
     * @param skippable the skip boolean value
     */
    public void setSkippable(boolean skippable){
        this.skippable = skippable;
    }

    /**
     * This method checks if the current state can be skipped
     *
     * @return true if the state can be skipped
     */
    public boolean isSkippable(){
        return skippable;
    }

    /**
     * This method adds a performed action
     *
     * @param performedAction the performed action
     */
    public void addPerformedAction(Action performedAction){
        this.performedActions.add(performedAction);
    }


    /**
     * This method sets up the first actual state of the turn and performs
     * some default calculation on the buildableCells and walkableCells
     *
     * @throws InvalidTurnStateException if in the wrong state
     */
    public void startTurn() throws InvalidTurnStateException {
        this.currentState.startTurn(this);
        this.changeState();
        this.currentState.setup(this);
    }

    /**
     * This boolean methods checks if the pawn can move to targetCell
     *
     * @param pawn       the worker we want to move
     * @param targetCell the cell we want to move the worker to
     * @return if the pawn can move to targetCell
     */
    public boolean canMoveTo(Worker pawn, Cell targetCell){
        return this.currentState.canMoveTo(pawn, targetCell, this);
    }

    /**
     * This method moves the pawn to targetCell
     *
     * @param pawn       the worker we want to move
     * @param targetCell the cell we want to move the worker to
     * @throws InvalidTurnStateException if in the wrong state
     */
    public void moveTo(Worker pawn, Cell targetCell ) throws InvalidTurnStateException {
        this.currentState.moveTo(pawn, targetCell, this);
        this.changeState();
    }

    /**
     * This boolean methods checks if the pawn can build a Dome in targetCell
     *
     * @param pawn       the worker who performs the build
     * @param targetCell the cell involved in the build
     * @return true if the pawn can build dome in targetCell
     */
    public boolean canBuildDomeIn(Worker pawn, Cell targetCell){
        return this.currentState.canBuildDomeIn(pawn, targetCell, this);
    }

    /**
     * This methods builds a dome in targetCell
     *
     * @param pawn       the worker who performs the build
     * @param targetCell the cell involved in the build
     * @throws InvalidTurnStateException if in the wrong state
     */
    public void buildDomeIn(Worker pawn, Cell targetCell) throws InvalidTurnStateException {
        this.currentState.buildDomeIn(pawn, targetCell, this);
        this.changeState();
    }

    /**
     * This boolean methods checks if the pawn can build a block in targetCell
     *
     * @param pawn       the worker who performs the build
     * @param targetCell the cell involved in the build
     * @return true if the pawn can build a block in targetCell
     */
    public boolean canBuildBlockIn(Worker pawn, Cell targetCell){
        return this.currentState.canBuildBlockIn(pawn, targetCell, this);
    }

    /**
     * This methods builds a block in targetCell
     *
     * @param pawn       the worker who performs the build
     * @param targetCell the cell involved in the build
     * @throws InvalidTurnStateException if in the wrong state
     */
    public void buildBlockIn(Worker pawn, Cell targetCell) throws InvalidTurnStateException {
        this.currentState.buildBlockIn(pawn, targetCell, this);
        this.changeState();
    }

    /**
     * This method lets the player surrender.
     * It doesn't call changeState and goes directly to Lose to avoid computing winConditions
     */
    public void draw(){
        this.currentState.draw(this);
    }

    /**
     * This method checks if we can end the turn
     *
     * @return if the player can end the turn
     */
    public boolean canEndTurn(){
        return this.currentState.canEndTurn(this);
    }

    /**
     * This method ends the turn
     *
     * @throws InvalidTurnStateException if in the wrong state
     */
    public void endTurn() throws InvalidTurnStateException {
        this.currentState.endTurn(this);
    }

}
