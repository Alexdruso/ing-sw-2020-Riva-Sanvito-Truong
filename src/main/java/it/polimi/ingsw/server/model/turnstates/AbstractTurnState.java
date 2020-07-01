package it.polimi.ingsw.server.model.turnstates;

import it.polimi.ingsw.server.model.Turn;
import it.polimi.ingsw.server.model.actions.Action;
import it.polimi.ingsw.server.model.board.Cell;
import it.polimi.ingsw.server.model.workers.Worker;

import java.util.List;

/**
 * A generic TurnState, which is one of the possible states of the State pattern that handles the Turn.
 */
public interface AbstractTurnState {

    /**
     * This method sets things up before we can use the other methods provided by the state
     *
     * @param turn the Context
     */
    void setup(Turn turn);

    /**
     * This boolean methods checks if the worker can move to targetCell
     *
     * @param worker     the worker we want to move
     * @param targetCell the cell we want to move the worker to
     * @param turn       the Context
     * @return if the worker can move to targetCell
     */
    default boolean canMoveTo(Worker worker, Cell targetCell, Turn turn) {
        return false;
    }

    /**
     * This method moves the worker to targetCell
     *
     * @param worker       the worker we want to move
     * @param targetCell the cell we want to move the worker to
     * @param turn       the Context
     * @throws InvalidTurnStateException if not in the right state
     */
    default void moveTo(Worker worker, Cell targetCell, Turn turn) throws InvalidTurnStateException {
        throw new InvalidTurnStateException();
    }

    /**
     * This boolean methods checks if the worker can build a Dome in targetCell
     *
     * @param worker       the worker who performs the build
     * @param targetCell the cell involved in the build
     * @param turn       the Context
     * @return true if the worker can build dome in targetCell
     */
    default boolean canBuildDomeIn(Worker worker, Cell targetCell, Turn turn) {
        return false;
    }

    /**
     * This methods builds a dome in targetCell
     *
     * @param worker       the worker who performs the build
     * @param targetCell the cell involved in the build
     * @param turn       the Context
     * @throws InvalidTurnStateException if not in the right state
     */
    default void buildDomeIn(Worker worker, Cell targetCell, Turn turn) throws InvalidTurnStateException {
        throw new InvalidTurnStateException();
    }

    /**
     * This boolean methods checks if the worker can build a block in targetCell
     *
     * @param worker       the worker who performs the build
     * @param targetCell the cell involved in the build
     * @param turn       the Context
     * @return true if the worker can build a block in targetCell
     */
    default boolean canBuildBlockIn(Worker worker, Cell targetCell, Turn turn) {
        return false;
    }

    /**
     * This methods builds a block in targetCell
     *
     * @param worker       the worker who performs the build
     * @param targetCell the cell involved in the build
     * @param turn       the Context
     * @throws InvalidTurnStateException if not in the right state
     */
    default void buildBlockIn(Worker worker, Cell targetCell, Turn turn) throws InvalidTurnStateException {
        throw new InvalidTurnStateException();
    }

    /**
     * This method sets up the default allowed workers in the context
     *
     * @param turn the Context
     */
    default void setupDefaultAllowedWorkers(Turn turn) {
        //If there are no performed actions, the player can use all the workers by default
        //Otherwise he is bound to the last worker who performed the action
        if (turn.getPerformedAction().isEmpty()) {
            turn.addAllowedWorkers(turn.getPlayer().getWorkers());
        } else {
            List<Action> performedActions = turn.getPerformedAction();
            turn.addAllowedWorker(performedActions.get(performedActions.size() - 1).getWorker());
        }
    }
}
