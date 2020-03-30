package it.polimi.ingsw.model.turnstates;

import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.actions.Action;
import it.polimi.ingsw.model.actions.BuildAction;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Component;
import it.polimi.ingsw.model.board.TargetCells;
import it.polimi.ingsw.model.workers.Worker;

import java.util.List;
import java.util.stream.Collectors;

class Build extends AbstractTurnState {

    /**
     * This method sets things up before we can use the other methods provided by the state
     *
     * @param turn the Context
     */
    @Override
    public void setup(Turn turn) {
        //Sets default next state
        turn.setNextState(TurnState.END.getTurnState());
        setupDefaultAllowedWorkers(turn);

        //for every allowed worker, intializes a target cell with the radius minus blocked cells
        for(Worker allowedWorker : turn.getAllowedWorkers()){
            TargetCells buildableCellsRadius = TargetCells.fromCellAndRadius(allowedWorker.getCell(), 1);
            TargetCells nonbuildableCells = (new TargetCells()).setAllTargets(true);
            TargetCells nonBlockBuildableCells = nonbuildableCells;
            TargetCells nonDomeBuildableCells = nonbuildableCells;

            List<Cell> blockedCells = turn.getGame().getBoard().getTargets(buildableCellsRadius).
                                        stream().
                                        filter(cell -> cell.getTower().isComplete() || cell.getWorker().isPresent() || cell.getTower().getCurrentLevel() == 3).
                                        collect(Collectors.toList());

            for(Cell cell : blockedCells) nonBlockBuildableCells = nonBlockBuildableCells.setPosition(cell, false);

            turn.setWorkerBlockBuildableCells(allowedWorker, buildableCellsRadius.intersect(nonBlockBuildableCells));

            blockedCells = turn.getGame().getBoard().getTargets(buildableCellsRadius).
                            stream().
                            filter(cell -> cell.getTower().isComplete() || cell.getWorker().isPresent() || cell.getTower().getCurrentLevel() < 3).
                            collect(Collectors.toList());

            for(Cell cell : blockedCells) nonDomeBuildableCells = nonDomeBuildableCells.setPosition(cell, false);

            turn.setWorkerDomeBuildableCells(allowedWorker, buildableCellsRadius.intersect(nonDomeBuildableCells));
        }

        //compute lose conditions
        if(turn. //the turn
                getAllowedWorkers(). //the set of allowed workers
                stream(). //the set gets turned into a stream
                map(allowedWorker -> turn.
                                        getGame().
                                        getBoard().
                                        getTargets( //take all the targetcells related to worker
                                                turn.
                                                getWorkerDomeBuildableCells(allowedWorker)
                                                ).
                                        isEmpty() //check if worker can build dome in some cells

                                        &&

                                        turn.
                                            getGame().
                                            getBoard().
                                            getTargets( //take all the targetcells related to worker
                                                    turn.
                                                    getWorkerBlockBuildableCells(allowedWorker)
                                            ).
                                        isEmpty() //check if worker can build block in some cells
                ).
                reduce(true, (isNoActionAll, isNoAction) -> isNoActionAll && isNoAction) //see if no worker can perform a move

                &&

                !turn.isSkippable() //see if turn can't be skipped
        ) turn.setLosingTurn(); //sets the turn to losing turn



        turn.getPlayer().getTurnEventsManager().processBeforeBuildEvents(turn);
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
     * This boolean methods checks if the pawn can build a Dome in targetCell
     *
     * @param pawn       the worker who performs the build
     * @param targetCell the cell involved in the build
     * @param turn       the Context
     * @return true if the pawn can build dome in targetCell
     */
    @Override
    public boolean canBuildDomeIn(Worker pawn, Cell targetCell, Turn turn) {
        return turn.getWorkerDomeBuildableCells(pawn).getPosition(targetCell.x, targetCell.y);
    }

    /**
     * This methods builds a dome in targetCell
     *
     * @param pawn       the worker who performs the build
     * @param targetCell the cell involved in the build
     * @param turn       the Context
     */
    @Override
    public void buildDomeIn(Worker pawn, Cell targetCell, Turn turn) {
        turn.addPerformedAction(new BuildAction(targetCell,//the target cell
                Component.DOME.getInstance(),//the buildable built
                targetCell.getTower().getCurrentLevel() + 1,//the new level built
                pawn));//the performer
        turn.getPlayer().getTurnEventsManager().processAfterBuildEvents(turn);
        targetCell.getTower().placeComponent(Component.DOME);
    }

    /**
     * This boolean methods checks if the pawn can build a block in targetCell
     *
     * @param pawn       the worker who performs the build
     * @param targetCell the cell involved in the build
     * @param turn       the Context
     * @return true if the pawn can build a block in targetCell
     */
    @Override
    public boolean canBuildBlockIn(Worker pawn, Cell targetCell, Turn turn) {
        return turn.getWorkerBlockBuildableCells(pawn).getPosition(targetCell.x,targetCell.y);
    }

    /**
     * This methods builds a block in targetCell
     *
     * @param pawn       the worker who performs the build
     * @param targetCell the cell involved in the build
     * @param turn       the Context
     */
    @Override
    public void buildBlockIn(Worker pawn, Cell targetCell, Turn turn) {
        turn.addPerformedAction(
                new BuildAction(
                        targetCell,//the target cell
                        Component.BLOCK.getInstance(),//the buildable built
                        targetCell.getTower().getCurrentLevel() + 1,//the new level built
                        pawn));//the performer

        turn.getPlayer().getTurnEventsManager().processAfterBuildEvents(turn);

        targetCell.getTower().placeComponent(Component.BLOCK);
    }

}
