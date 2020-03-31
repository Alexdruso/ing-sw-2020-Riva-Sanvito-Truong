package it.polimi.ingsw.model.turnstates;

import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.actions.BuildAction;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Component;
import it.polimi.ingsw.model.board.TargetCells;
import it.polimi.ingsw.model.workers.Worker;

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

        //for every allowed worker, initializes a target cell with the radius minus blocked cells
        for(Worker allowedWorker : turn.getAllowedWorkers()){
            TargetCells blockBuildableCellsRadius = TargetCells.fromCellAndRadius(allowedWorker.getCell(), 1);
            TargetCells domeBuildableCellsRadius = TargetCells.fromCellAndRadius(allowedWorker.getCell(), 1);

            turn.getGame().getBoard().getTargets(blockBuildableCellsRadius)
                    .stream()
                    .filter(cell -> cell.getTower().isComplete() || cell.getWorker().isPresent() || cell.getTower().getCurrentLevel() == 3)
                    .forEach(cell -> blockBuildableCellsRadius.setPosition(cell, false)
            );

            turn.setWorkerBlockBuildableCells(allowedWorker, blockBuildableCellsRadius);

            turn.getGame().getBoard().getTargets(domeBuildableCellsRadius)
                    .stream()
                    .filter(cell -> cell.getTower().isComplete() || cell.getWorker().isPresent() || cell.getTower().getCurrentLevel() < 3)
                    .forEach(cell -> domeBuildableCellsRadius.setPosition(cell, false)
            );

            turn.setWorkerDomeBuildableCells(allowedWorker, domeBuildableCellsRadius);
        }

        //compute lose conditions
        if (
                !turn.isSkippable() //see if turn can't be skipped
                    && turn.getAllowedWorkers().stream().map(
                            allowedWorker -> turn.getGame().getBoard().getTargets(turn.getWorkerDomeBuildableCells(allowedWorker)).isEmpty() //check if worker can build dome in some cells
                                    && turn.getGame().getBoard().getTargets(turn.getWorkerBlockBuildableCells(allowedWorker)).isEmpty() //check if worker can build block in some cells
                        )
                    .reduce(true, (isNoActionAll, isNoAction) -> isNoActionAll && isNoAction) //see if no worker can perform a move
        ) {
            turn.triggerLosingTurn(); //sets the turn to losing turn
        }
        else {
            turn.getPlayer().getTurnEventsManager().processBeforeBuildEvents(turn);
            // notify();
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
        return turn.getWorkerDomeBuildableCells(pawn).getPosition(targetCell.getX(), targetCell.getY());
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
        return turn.getWorkerBlockBuildableCells(pawn).getPosition(targetCell.getX(),targetCell.getY());
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
