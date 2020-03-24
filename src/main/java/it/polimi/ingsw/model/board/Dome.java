package it.polimi.ingsw.model.board;

public class Dome implements Buildable {
    @Override
    public Boolean isTargetable() {
       return false;
    }
}
