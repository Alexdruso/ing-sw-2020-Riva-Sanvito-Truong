package it.polimi.ingsw.model.turnevents;

import java.util.HashMap;

//TODO: make the process*Events method match the signature of the TurnEvents methods
/**
 * The TurnEvents manager, which allows the gods to register their powers and executes them when requested to do so by the game.
 */
public class TurnEventsManager {
    private TurnEvents playerTurnEvents;
    private HashMap<Player, TurnEvents> turnEventsFromOpponents;

    /**
     * The TurnEventsManager constructor.
     *
     * @param playerTurnEvents the TurnEvents of the current player
     */
    public TurnEventsManager(TurnEvents playerTurnEvents) {
        this.playerTurnEvents = playerTurnEvents;
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
     * Process turn start events.
     */
    public void processTurnStartEvents() {
        playerTurnEvents.onTurnStart();
        turnEventsFromOpponents.values().forEach(TurnEvents::onTurnStart);
    }

    /**
     * Process before movement events.
     */
    public void processBeforeMovementEvents() {
        playerTurnEvents.onBeforeMovement();
        turnEventsFromOpponents.values().forEach(TurnEvents::onBeforeMovement);
    }

    /**
     * Process after movement events.
     */
    public void processAfterMovementEvents() {
        playerTurnEvents.onAfterMovement();
        turnEventsFromOpponents.values().forEach(TurnEvents::onAfterMovement);
    }

    /**
     * Process before build events.
     */
    public void processBeforeBuildEvents() {
        playerTurnEvents.onBeforeBuild();
        turnEventsFromOpponents.values().forEach(TurnEvents::onBeforeBuild);
    }

    /**
     * Process turn end events.
     */
    public void processTurnEndEvents() {
        playerTurnEvents.onTurnEnd();
        turnEventsFromOpponents.values().forEach(TurnEvents::onTurnEnd);
    }

    /**
     * Process win condition events.
     */
    public void processWinConditionEvents() {
        playerTurnEvents.computeWinCondition();
        turnEventsFromOpponents.values().forEach(TurnEvents::computeWinCondition);
    }
}

/**
 * TODO: REMOVE ME as soon as the real Player is implemented!
 */
class Player{}