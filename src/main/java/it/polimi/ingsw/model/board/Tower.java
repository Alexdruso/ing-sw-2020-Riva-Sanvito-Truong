package it.polimi.ingsw.model.board;

import java.util.ArrayList;

public class Tower {
    //Might implement this as an array later
    private final ArrayList<Buildable> components = new ArrayList<Buildable>();

    private static class InvalidBuildException extends RuntimeException {};

    public Boolean isComplete(){
        for(Buildable c: components){
            if(!c.isTargetable()) return true;
        }
        return false;
    }

    public int getCurrentLevel(){
        int count = 0;
        for(Buildable c: components){
            if(c.isTargetable()) count++;
        }
        return count;
    }

    public void placeComponent(Component component){
        if(this.isComplete()){
            throw new InvalidBuildException();
        } else {
            components.add(component.getInstance());
        }
    }
}
