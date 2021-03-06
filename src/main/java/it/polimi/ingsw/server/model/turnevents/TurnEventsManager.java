package it.polimi.ingsw.server.model.turnevents;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Turn;

import java.util.HashMap;

/**
 * The TurnEvents manager, which allows the gods to register their powers and executes them when requested to do so by the game.
 */
public class TurnEventsManager {
    /**
     * The player owning the turn events manager.
     */
    private final Player player;
    /**
     * The Turn events from the opponents.
     */
    private final HashMap<Player, TurnEvents> turnEventsFromOpponents;

    /**
     * The TurnEventsManager constructor.
     *
     * @param player the Player for which this TurnEventsManager is being created
     */
    public TurnEventsManager(Player player) {
        this.player = player;
        turnEventsFromOpponents = new HashMap<>();
    }

    /**
     * Allows opponents to add their TurnEvents to the current TurnEventManager.
     *
     * @param opponent   the opponent who is adding the TurnEvents to the current TurnEventManager
     * @param turnEvents the TurnEvents to add to the current TurnEventManager
     */
    public void addTurnEventsFromOpponents(Player opponent, TurnEvents turnEvents) {
        this.turnEventsFromOpponents.put(opponent, turnEvents);
    }

    /**
     * Allows opponents to remove their TurnEvents from the current TurnEventManager.
     *
     * @param opponent the opponent who wants to remove its TurnEvents from the current TurnEventManager
     */
    public void removeTurnEventsSetByOpponent(Player opponent) {
        this.turnEventsFromOpponents.remove(opponent);
    }

    /**
     * Process turn start events.
     *
     * @param turn the turn
     */
    public void processTurnStartEvents(Turn turn) {
        player.getGod().getOwnerTurnEvents().onTurnStart(turn);
        turnEventsFromOpponents.values().forEach(turnEvents -> turnEvents.onTurnStart(turn));
    }

    /**
     * Process before movement events.
     *
     * @param turn the turn
     */
    public void processBeforeMovementEvents(Turn turn) {
        player.getGod().getOwnerTurnEvents().onBeforeMovement(turn);
        turnEventsFromOpponents.values().forEach(turnEvents -> turnEvents.onBeforeMovement(turn));
    }

    /**
     * Process after movement events.
     *
     * @param turn the turn
     */
    public void processAfterMovementEvents(Turn turn) {
        player.getGod().getOwnerTurnEvents().onAfterMovement(turn);
        turnEventsFromOpponents.values().forEach(turnEvents -> turnEvents.onAfterMovement(turn));
    }

    /**
     * Process before build events.
     *
     * @param turn the turn
     */
    public void processBeforeBuildEvents(Turn turn) {
        player.getGod().getOwnerTurnEvents().onBeforeBuild(turn);
        turnEventsFromOpponents.values().forEach(turnEvents -> turnEvents.onBeforeBuild(turn));
    }

    /**
     * Process after build events.
     *
     * @param turn the turn
     */
    public void processAfterBuildEvents(Turn turn) {
        player.getGod().getOwnerTurnEvents().onAfterBuild(turn);
        turnEventsFromOpponents.values().forEach(turnEvents -> turnEvents.onAfterBuild(turn));
    }

    /**
     * Process turn end events.
     *
     * @param turn the turn
     */
    public void processTurnEndEvents(Turn turn) {
        player.getGod().getOwnerTurnEvents().onTurnEnd(turn);
        turnEventsFromOpponents.values().forEach(turnEvents -> turnEvents.onTurnEnd(turn));
    }

    /**
     * Process win condition events.
     *
     * @param turn the turn
     */
    public void processWinConditionEvents(Turn turn) {
        player.getGod().getOwnerTurnEvents().computeWinCondition(turn);
        turnEventsFromOpponents.values().forEach(turnEvents -> turnEvents.computeWinCondition(turn));
    }
}
