package it.polimi.ingsw.model;

import it.polimi.ingsw.model.actions.Action;
import it.polimi.ingsw.model.actions.BuildAction;
import it.polimi.ingsw.model.actions.MoveAction;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.TargetCells;
import it.polimi.ingsw.model.turnStates.Setup;
import it.polimi.ingsw.model.turnStates.TurnState;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
    private HashMap<Worker, TargetCells> blockBuildableCells;

    /**
     * Cells on which the worker can build a dome
     */
    private HashMap<Worker, TargetCells> domeBuildableCells;

    /**
     * Cells the worker can be moved to
     */
    private HashMap<Worker, TargetCells> walkableCells;

    /**
     * Current state of the turn, part of state pattern
     */
    private TurnState currentState;



    /**
     * Builder of the turn.
     * It binds to a specific game and player.
     * It initializes all the relevant structures and sets the first state, then calling .setup() on it
     * to decide the first actual turn.
     * @param game the game associated to the turn
     * @param player the player performing actions in the turn
     */
    public Turn(Game game, Player player){
        this.game = game;
        this.player = player;

        performedActions = new LinkedList<Action>();
        blockBuildableCells = new HashMap<Worker, TargetCells>();
        domeBuildableCells = new HashMap<Worker, TargetCells>();
        walkableCells = new HashMap<Worker, TargetCells>();
        //we use the first current state to prepare the turn for the first actual state
        currentState = new Setup();
    }

    /**
     * This method provides a List of all the performed builds
     * @return a list of all the performed builds in the turn
     */
    public List<BuildAction> getBuilds(){
        return performedActions.stream().filter(action -> action instanceof BuildAction).map(action -> (BuildAction) action).collect(Collectors.toList());
    }

    /**
     * This method provides a List of all the performed moves
     * @return a list of all the performed moves in the turn
     */
    public List<MoveAction> getMoves(){
        return performedActions.stream().filter(action -> action instanceof MoveAction).map(action -> (MoveAction) action).collect(Collectors.toList());
    }

    //TODO add some code

    /**
     * This method sets up the first actual state of the turn and performs
     * some default calculation on the buildableCells and walkableCells
     */
    public void startTurn(){}

    /**
     * This boolean methods checks if the pawn can move to targetCell
     * @param pawn the worker we want to move
     * @param targetCell the cell we want to move the worker to
     * @return if the pawn can move to targetCell
     */
    public boolean canMoveTo(Worker pawn, Cell targetCell){ return true; }

    /**
     * This method moves the pawn to targetCell
     * @param pawn the worker we want to move
     * @param targetCell the cell we want to move the worker to
     */
    public void moveTo(Worker pawn, Cell targetCell ){}

    /**
     * This boolean methods checks if the pawn can build in targetCell
     * @param pawn the worker who performs the build
     * @param targetCell the cell involved in the build
     * @return if the pawn can build in targetCell
     */
    public boolean canBuildIn(Worker pawn, Cell targetCell){ return true; } //This method should have another parameter to s set block or dome

    /**
     * This methods builds ((something)) in targetCell
     * @param pawn the worker who performs the build
     * @param targetCell the cell involved in the build
     */
    public void buildIn(Worker pawn, Cell targetCell){} //This method should have another parameter to s set block or dome

    /**
     * This method lets the player surrender
     */
    public void draw(){}

    /**
     * This method checks if we can end the turn
     * @return if the player can end the turn
     */
    public boolean canEndTurn(){
        return true;
    }

    /**
     * This method ends the turn
     */
    public void endTurn(){}; //Throws WinException or LoseException!!
}
