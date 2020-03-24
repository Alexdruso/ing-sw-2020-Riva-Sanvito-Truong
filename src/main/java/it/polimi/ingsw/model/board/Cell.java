package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.board.Tower;

public class Cell {
    public final int x;
    public final int y;

    public Tower tower = new Tower();

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Tower getTower(){
        return tower;
    }
}
