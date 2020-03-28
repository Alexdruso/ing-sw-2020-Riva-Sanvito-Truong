package it.polimi.ingsw.model.turnstates;

import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.actions.BuildAction;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Component;
import it.polimi.ingsw.model.board.Dome;
import it.polimi.ingsw.model.workers.Worker;

class Build extends AbstractTurnState {

    /**
     * This method sets things up before we can use the other methods provided by the state
     *
     * @param turn the Context
     */
    @Override
    public void setup(Turn turn) {
        //TODO compute lose conditions
        turn.getPlayer().getTurnEventsManager().processBeforeBuildEvents(turn);
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
        targetCell.getTower().placeComponent(Component.DOME);
        //TODO remove if not necessary
        //turn.getPlayer().getTurnEventsManager().processAfterBuildEvents(turn);
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
