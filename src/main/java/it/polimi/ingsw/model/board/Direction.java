package it.polimi.ingsw.model.board;

public class Direction {
    public final int dx;
    public final int dy;
    public Direction(Cell firstCell, Cell secondCell){
        dx = secondCell.getX() - firstCell.getX();
        dy = secondCell.getY() - firstCell.getY();
    }
}