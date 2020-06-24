package it.polimi.ingsw.server.model.turnstates;

import it.polimi.ingsw.server.model.Turn;
import it.polimi.ingsw.server.model.actions.BuildAction;
import it.polimi.ingsw.server.model.board.Cell;
import it.polimi.ingsw.server.model.board.Component;
import it.polimi.ingsw.server.model.board.TargetCells;
import it.polimi.ingsw.server.model.workers.Worker;

import java.util.List;
import java.util.stream.Collectors;

class Build implements AbstractTurnState {

    /**
     * This method sets things up before we can use the other methods provided by the state
     *
     * @param turn the Context
     */
    public void setup(Turn turn) {
        //Sets default next state
        turn.setNextState(TurnState.END.getTurnState());
        setupDefaultAllowedWorkers(turn);

        //for every allowed worker, initializes a target cell with the radius minus blocked cells
        for (Worker worker : turn.getPlayer().getWorkers()) {
            TargetCells blockBuildableCellsRadius = TargetCells.fromCellAndRadius(worker.getCell(), 1);
            TargetCells domeBuildableCellsRadius = TargetCells.fromCellAndRadius(worker.getCell(), 1);

            turn.getGame().getBoard().getTargets(blockBuildableCellsRadius)
                    .stream()
                    .filter(cell -> cell.getTower().isComplete() || cell.getWorker().isPresent() || cell.getTower().getCurrentLevel() == 3)
                    .forEach(cell -> blockBuildableCellsRadius.setPosition(cell, false)
                    );

            turn.setWorkerBlockBuildableCells(worker, blockBuildableCellsRadius);

            turn.getGame().getBoard().getTargets(domeBuildableCellsRadius)
                    .stream()
                    .filter(cell -> cell.getTower().isComplete() || cell.getWorker().isPresent() || cell.getTower().getCurrentLevel() < 3)
                    .forEach(cell -> domeBuildableCellsRadius.setPosition(cell, false)
                    );

            turn.setWorkerDomeBuildableCells(worker, domeBuildableCellsRadius);
        }
        //use powers
        turn.getPlayer().getTurnEventsManager().processBeforeBuildEvents(turn);

        //now set as allowed workers only the ones who can build something
        List<Worker> buildAllowedWorkers = turn.getAllowedWorkers().stream()
                .filter(
                        allowedWorker -> !turn.getGame().getBoard()
                                .getTargets(turn.getWorkerDomeBuildableCells(allowedWorker))
                                .isEmpty() //check if worker can build dome in some cells
                                || !turn.getGame().getBoard().getTargets(turn.getWorkerBlockBuildableCells(allowedWorker))
                                .isEmpty() //check if worker can build a block somewhere
                )
                .collect(Collectors.toList());

        turn.clearAllowedWorkers();
        turn.addAllowedWorkers(buildAllowedWorkers);

        if (!buildAllowedWorkers.isEmpty())
            turn.getGame().notifyAskBuild(turn); //if there is available build, notify the message
        else if (turn.isSkippable())
            turn.getGame().skip(); //skip automatically to the next state if you can't perform any action
        else turn.triggerLosingTurn(); //else you lost
    }

    /**
     * This boolean methods checks if the pawn can build a Dome in targetCell
     *
     * @param worker     the worker who performs the build
     * @param targetCell the cell involved in the build
     * @param turn       the Context
     * @return true if the pawn can build dome in targetCell
     */
    @Override
    public boolean canBuildDomeIn(Worker worker, Cell targetCell, Turn turn) {
        return turn.getAllowedWorkers().contains(worker) &&
                turn.getWorkerDomeBuildableCells(worker).getPosition(targetCell.getX(), targetCell.getY());
    }

    /**
     * This methods builds a dome in targetCell
     *
     * @param worker       the worker who performs the build
     * @param targetCell the cell involved in the build
     * @param turn       the Context
     */
    @Override
    public void buildDomeIn(Worker worker, Cell targetCell, Turn turn) {
        turn.addPerformedAction(new BuildAction(targetCell,//the target cell
                Component.DOME.getInstance(),//the buildable built
                targetCell.getTower().getCurrentLevel(),//the level on which we built the dome
                worker));//the performer
        turn.getPlayer().getTurnEventsManager().processAfterBuildEvents(turn);

        turn.getGame().buildInCell(worker, targetCell, Component.DOME, targetCell.getTower().getCurrentLevel());
    }

    /**
     * This boolean methods checks if the pawn can build a block in targetCell
     *
     * @param worker       the worker who performs the build
     * @param targetCell the cell involved in the build
     * @param turn       the Context
     * @return true if the pawn can build a block in targetCell
     */
    @Override
    public boolean canBuildBlockIn(Worker worker, Cell targetCell, Turn turn) {
        return turn.getAllowedWorkers().contains(worker)
                && turn.getWorkerBlockBuildableCells(worker).getPosition(targetCell.getX(), targetCell.getY());
    }

    /**
     * This methods builds a block in targetCell
     *
     * @param worker       the worker who performs the build
     * @param targetCell the cell involved in the build
     * @param turn       the Context
     */
    @Override
    public void buildBlockIn(Worker worker, Cell targetCell, Turn turn) {
        turn.addPerformedAction(
                new BuildAction(
                        targetCell,//the target cell
                        Component.BLOCK.getInstance(),//the buildable built
                        targetCell.getTower().getCurrentLevel() + 1,//the new level built
                        worker));//the performer

        turn.getPlayer().getTurnEventsManager().processAfterBuildEvents(turn);

        turn.getGame().buildInCell(worker, targetCell, Component.BLOCK, targetCell.getTower().getCurrentLevel() + 1);
    }

}
