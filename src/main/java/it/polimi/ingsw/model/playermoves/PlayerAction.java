package it.polimi.ingsw.model.playermoves;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.View;

public interface PlayerAction {
    public Player getPlayer();
    public View getView();
}
