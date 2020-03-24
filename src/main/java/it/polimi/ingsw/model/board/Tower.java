package it.polimi.ingsw.model.board;

import java.util.ArrayList;
import java.util.List;

/**
 * A Tower object is an aggregation of Buildable components and may represent a complete
 * or an incomplete tower
 */
public class Tower {
    //Might implement this as an array later
    private final ArrayList<Buildable> components;

    private static class InvalidBuildException extends RuntimeException {};

    /**
     * Class constructor
     */
    public Tower(){
        components = new ArrayList<Buildable>();
    }

    /**
     * This method checks whether or not a dome is present on the tower at any level
     * @return true if a dome is present, otherwise false
     */
    public Boolean isComplete(){
        for(Buildable c: components){
            if(!c.isTargetable()) return true;
        }
        return false;
    }

    /**
     * This method returns the highest level occupied by a block in the tower
     * @return the level of the block that is placed highest
     */

    public int getCurrentLevel(){
        int count = 0;
        for(Buildable c: components){
            if(c.isTargetable()) count++;
        }
        return count;
    }

    /**
     * This method places a component on the tower
     * @param component the instance of the Component enum which is to be built
     * @throws InvalidBuildException if attempting a build action on a complete tower
     */
    public void placeComponent(Component component){
        if(this.isComplete()){
            throw new InvalidBuildException();
        } else {
            components.add(component.getInstance());
        }
    }
}
