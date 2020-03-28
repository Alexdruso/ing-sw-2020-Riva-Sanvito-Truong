package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.actions.MoveAction;
import it.polimi.ingsw.model.turnevents.TurnEvents;
import it.polimi.ingsw.model.workers.Worker;

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
            //TODO

            Player athenaPlayer = turn.getGame().getPlayersList().stream().filter(
                    player -> player.getGod().equals(GodCard.ATHENA.getGod())
            ).findFirst().orElse(null);
            turn.getGame().getLastRoundTurnsList().stream().filter(
                    previousTurn -> previousTurn.getPlayer().equals(athenaPlayer)
            ).findFirst().ifPresent(athenaLastTurn -> {
                List<MoveAction> athenaMoveUpActions = athenaLastTurn.getMoves().stream().filter(
                        action -> action.getTargetLevel() > action.getSourceLevel()
                ).collect(Collectors.toList());
                if (athenaMoveUpActions.size() > 0) {
//                    TODO
//                    TargetCells higherCells = TargetCells.fromMinimumHeight(worker.getCell().getHeight() + 1);
                    for (Worker worker : turn.getPlayer().getOwnWorkers()) {
//                        TODO
//                        turn.getWorkerWalkableCells(worker).removeTargets(higherCells);
                    }
                }
            });

        }
    };

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
