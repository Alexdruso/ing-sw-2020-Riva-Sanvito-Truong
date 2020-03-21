package it.polimi.ingsw.model.turnevents;

import it.polimi.ingsw.model.player.Player;

import java.util.HashMap;

//TODO: make the process*Events method match the signature of the TurnEvents methods
/**
 * The TurnEvents manager, which allows the gods to register their powers and executes them when requested to do so by the game.
 */
public class TurnEventsManager {
    private Player player;
    private HashMap<Player, TurnEvents> turnEventsFromOpponents;

    /**
     * The TurnEventsManager constructor.
     *
     * @param player the Player for which this TurnEventsManager is being created
     */
    public TurnEventsManager(Player player) {
        this.player = player;
        turnEventsFromOpponents = new HashMap<Player, TurnEvents>();
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
     */
    public void processTurnStartEvents() {
        player.getGod().getOwnerTurnEvents().onTurnStart();
        turnEventsFromOpponents.values().forEach(TurnEvents::onTurnStart);
    }

    /**
     * Process before movement events.
     */
    public void processBeforeMovementEvents() {
        player.getGod().getOwnerTurnEvents().onBeforeMovement();
        turnEventsFromOpponents.values().forEach(TurnEvents::onBeforeMovement);
    }

    /**
     * Process after movement events.
     */
    public void processAfterMovementEvents() {
        player.getGod().getOwnerTurnEvents().onAfterMovement();
        turnEventsFromOpponents.values().forEach(TurnEvents::onAfterMovement);
    }

    /**
     * Process before build events.
     */
    public void processBeforeBuildEvents() {
        player.getGod().getOwnerTurnEvents().onBeforeBuild();
        turnEventsFromOpponents.values().forEach(TurnEvents::onBeforeBuild);
    }

    /**
     * Process turn end events.
     */
    public void processTurnEndEvents() {
        player.getGod().getOwnerTurnEvents().onTurnEnd();
        turnEventsFromOpponents.values().forEach(TurnEvents::onTurnEnd);
    }

    /**
     * Process win condition events.
     */
    public void processWinConditionEvents() {
        player.getGod().getOwnerTurnEvents().computeWinCondition();
        turnEventsFromOpponents.values().forEach(TurnEvents::computeWinCondition);
    }
}
