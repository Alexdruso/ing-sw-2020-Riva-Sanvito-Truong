package it.polimi.ingsw.model.turnstates;

import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.actions.Action;
import it.polimi.ingsw.model.actions.MoveAction;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.workers.Worker;

import java.util.List;

class Move extends AbstractTurnState {

    /**
     * This method sets things up before we can use the other methods provided by the state
     *
     * @param turn the Context
     */
    @Override
    public void setup(Turn turn) {
        //Sets default next state
        turn.setNextState(TurnState.BUILD.getTurnState());

        //TODO add allowedWorkers management
        setupDefaultAllowedWorkers(turn);
        //TODO compute lose conditions
        turn.getPlayer().getTurnEventsManager().processBeforeMovementEvents(turn);
    }

    /**
     * This method sets up the default allowed workers in the context
     *
     * @param turn the Context
     */
    private void setupDefaultAllowedWorkers(Turn turn){
        //If there are no performed actions, the player can use all the workers by default
        //Otherwise he is bound to the last worker who performed the action
        if(turn.getPerformedAction().isEmpty()){
            for(Worker pawn : turn.getPlayer().getOwnWorkers()) turn.getAllowedWorkers().add(pawn);
        }
        else{
            List<Action> performedActions = turn.getPerformedAction();
            turn.getAllowedWorkers().add(performedActions.get(performedActions.size()-1).getPerformer());
        }
    }

    /**
     * This boolean methods checks if the pawn can move to targetCell
     *
     * @param pawn       the worker we want to move
     * @param targetCell the cell we want to move the worker to
     * @param turn       the Context
     * @return if the pawn can move to targetCell
     */
    @Override
    public boolean canMoveTo(Worker pawn, Cell targetCell, Turn turn) {
        return turn.getWorkerWalkableCells(pawn).getPosition(targetCell.x,targetCell.y);
    }

    /**
     * This method moves the pawn to targetCell
     *
     * @param pawn       the worker we want to move
     * @param targetCell the cell we want to move the worker to
     * @param turn       the Context
     */
    @Override
    public void moveTo(Worker pawn, Cell targetCell, Turn turn) {
        turn.addPerformedAction(
                new MoveAction(
                        pawn.getCell(), //the source cell
                        targetCell, //the target cell
                        pawn.getCell().getTower().getCurrentLevel(), //the source cell level
                        targetCell.getTower().getCurrentLevel(), //the target cell level
                        pawn //the performer
                )
        );

        turn.getPlayer().getTurnEventsManager().processAfterMovementEvents(turn);

        turn.getGame().setWorkerCell(pawn, targetCell);
    }
}
