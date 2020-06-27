package it.polimi.ingsw.server.model.gods;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Turn;
import it.polimi.ingsw.server.model.actions.MoveAction;
import it.polimi.ingsw.server.model.board.TargetCells;
import it.polimi.ingsw.server.model.turnevents.TurnEvents;
import it.polimi.ingsw.server.model.workers.Worker;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The god card Athena.
 */
class Athena extends AbstractGod {
    /**
     * The TurnEvents for the opponents of the owner of the Athena god card.
     */
    private static final TurnEvents opponentsTurnEvents = new TurnEvents() {
        @Override
        protected void onBeforeMovement(Turn turn) {
            Player athenaPlayer = turn.getGame().getPlayersList().stream().filter(
                    player -> player.getGod().equals(GodCard.ATHENA.getGod())
            ).findFirst().orElse(null);
            turn.getGame().getLastRoundTurnsList().stream().filter(
                    previousTurn -> previousTurn.getPlayer().equals(athenaPlayer)
            ).findFirst().ifPresent(athenaLastTurn -> {
                List<MoveAction> athenaMoveUpActions = athenaLastTurn.getMoves().stream().filter(
                        action -> action.getTargetLevel() > action.getSourceLevel()
                ).collect(Collectors.toList());
                if (!athenaMoveUpActions.isEmpty()) {
                    for (Worker worker : turn.getPlayer().getWorkers()) {
                        TargetCells lowerOrEqualCells = TargetCells.fromCells(
                                turn.getGame().getBoard().getCellsList().stream().filter(
                                        cell -> cell.getTower().getCurrentLevel() <= worker.getCell().getTower().getCurrentLevel()
                                ).collect(Collectors.toList())
                        );
                        turn.getWorkerWalkableCells(worker).intersect(lowerOrEqualCells);
                    }
                }
            });

        }
    };

    /**
     * Gets the god's name.
     *
     * @return the name
     */
    @Override
    public String getName() {
        return "Athena";
    }

    /**
     * Gets the TurnEvents for the opponents of the player owning Athena.
     *
     * @return the TurnEvents for the opponents of the player owning Athena
     */
    @Override
    public TurnEvents getOpponentsTurnEvents() {
        return opponentsTurnEvents;
    }

}
