package it.polimi.ingsw.client.reducedmodel;

import it.polimi.ingsw.client.clientstates.AbstractClientTurnState;

public class ReducedTurn {
    private final ReducedPlayer player;
    private final AbstractClientTurnState turnState;

    public ReducedTurn(ReducedPlayer player, AbstractClientTurnState turnState) {
        this.player = player;
        this.turnState = turnState;
    }

    public ReducedPlayer getPlayer() {
        return player;
    }

    public AbstractClientTurnState getTurnState() {
        return turnState;
    }
}
