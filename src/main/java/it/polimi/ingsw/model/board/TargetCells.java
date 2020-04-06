package it.polimi.ingsw.model.board;

import it.polimi.ingsw.parsing.ConfigParser;

import java.util.Collection;

/**
 * This class represents a set of cells that are targeted for actions.
 * A TargetCells instance should be created either via the fromMatrix() method or via constructor.
 * The instance should then be modified via the setPosition, setRow, setColumn methods.
 * Finally the Cell instances should be retrieved by the getTargets() method in the board.
 */
public class TargetCells {
    private static final int BOARD_SIZE = Integer.parseInt(ConfigParser.getInstance().getProperty("boardSize"));
    private boolean[][] targets;

    /**
     * Class constructor which creates a TargetCells instance which has no targeted cells
     */
    public TargetCells(){
        targets = new boolean[BOARD_SIZE][BOARD_SIZE];
    }

    private static boolean isValidCoord(int x, int y){
        return x >= 0  && x < BOARD_SIZE && y >= 0  && y < BOARD_SIZE;
    }

    /**
     * Overridden equals method to provide equality between two TargetCells instances
     * @param other the other TargetCells with which to compare
     * @return true if this and other are equal, false otherwise
     */
    @Override
    public boolean equals(Object other){
        //TODO: we should think about TargetCells sizes, I don't think they can ever be different but checking wouldn't
        //be too bad
        if(this == other){
            return true;
        }
        if(!(other instanceof TargetCells)){
            return false;
        }
        TargetCells target = (TargetCells) other;
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                if(this.getPosition(i, j) != target.getPosition(i, j)){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This method checks if the TargetCells has any cell set to be targeted
     *
     * @return false if there are targeted cells, true otherwise
     */
    public boolean isEmpty(){
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                if(targets[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This method sets all cells to the same value
     *
     * @param isTargeted if true, sets all cells to targeted. Otherwise, if false sets all cells to un-targeted
     * @return resulting TargetCells
     */
    public TargetCells setAllTargets(boolean isTargeted){
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                targets[i][j] = isTargeted;
            }
        }
        return this;
    }

    /**
     * Sets a cell at a given position to the given targeted status
     *
     * @param x the x coord
     * @param y the y coord
     * @param isTargeted  if true the cell is set to targeted, otherwise to un-targeted
     * @return resulting TargetCells
     */
    public TargetCells setPosition(int x, int y, boolean isTargeted){
        targets[x][y] = isTargeted;
        return this;
    }

    /**
     * Sets a cell at a given position to the given targeted status
     *
     * @param cell       the cell whose coordinates are object of this update
     * @param isTargeted if true the cell is set to targeted, otherwise to un-targeted
     * @return resulting TargetCells
     */
    public TargetCells setPosition(Cell cell, boolean isTargeted){
        targets[cell.getX()][cell.getY()] = isTargeted;
        return this;
    }

    /**
     * Retrieves the targeted status of the cell at a given position
     *
     * @param x int representing the x coord of the cell to be checked
     * @param y int representing the y coord of the cell to be checked
     * @return true if the cell is targeted, false otherwise
     */
    public boolean getPosition(int x, int y){
        return targets[x][y];
    }

    /**
     * Retrieves the targeted status of the given Cell
     *
     * @param cell the Cell object to be checked
     * @return true if the cell is targetd, false otherwise
     */
    public boolean getPosition(Cell cell){
        return targets[cell.getX()][cell.getY()];
    }

    /**
     * Sets an entire row's targeted status
     *
     * @param y the y coord
     * @param isTargeted if true the cells are set to targeted, otherwise to un-targeted
     * @return resulting TargetCells
     */
    public TargetCells setRow(int y, boolean isTargeted) {
        for(int i = 0; i < BOARD_SIZE; i++){
            targets[i][y] = isTargeted;
        }
        return this;
    }

    /**
     * Sets an entire row's targeted status
     *
     * @param x the x coord
     * @param isTargeted  if true the cells are set to targeted, otherwise to un-targeted
     * @return resulting TargetCells
     */
    public TargetCells setColumn(int x, boolean isTargeted) {
        for(int i = 0; i < BOARD_SIZE; i++){
            targets[x][i] = isTargeted;
        }
        return this;
    }

    /**
     * Sets the edge cells' targeted status
     *
     * @param isTargeted if true the cells are set to targetd, otherwise to un-targeted
     * @return resulting TargetCells
     */
    public TargetCells setEdges(boolean isTargeted){
        for(int i = 0; i < BOARD_SIZE; i++){
            targets[0][i] = isTargeted;
            targets[BOARD_SIZE-1][i] = isTargeted;
        }

        for(int i = 1; i < BOARD_SIZE - 1; i++){
            targets[i][0] = isTargeted;
            targets[i][BOARD_SIZE-1] = isTargeted;
        }
        return this;
    }

    /**
     * Sets this to be the intersection of this and other.
     * Cells in this after the method call with be targeted if and only if they were
     * previously targeted and if they are targeted by other
     *
     * @param other the other TargetCells instances with which apply the intersection
     * @return resulting TargetCells
     */
    public TargetCells intersect(TargetCells other){
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                targets[i][j] = targets[i][j] && other.getPosition(i, j);
            }
        }
        return this;
    }

    /**
     * This method inverts each cell's targeted status. i.e. a targeted cell becomes untargeted and viceversa
     *
     * @return the resulting TargetCells
     */
    public TargetCells invert(){
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                targets[i][j] = !targets[i][j];
            }
        }
        return this;
    }


    /**
     * Sets this to be the union of this and other.
     * Cells in this after the method call with be targeted either if they were
     * previously targeted or if they are targeted by other
     *
     * @param other the other TargetCells instances with which apply the union
     * @return resulting TargetCells
     */
    public TargetCells union(TargetCells other){
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                targets[i][j] = targets[i][j] || other.getPosition(i, j);
            }
        }
        return this;
    }


    /**
     * Factory method which creates a new TargetCells that sets to targeted all the cells around the center
     * with Manhattan distance of radius. This operation excludes the center itself.
     *
     * @param center The center cell of the targeted area
     * @param radius The Manhattan distance around the center at which all cells will be targeted, excluding the center.
     * @return the resulting TargetCells
     * @throws IllegalArgumentException if the radius is non-positive
     */
    public static TargetCells fromCellAndRadius(Cell center, int radius) throws IllegalArgumentException{
        if(radius <= 0){
            throw new IllegalArgumentException("Non-Positive Radius");
        }
        TargetCells target = new TargetCells();

        for(int i = -1 * radius; i <= radius; i++){
            if (isValidCoord(center.getX() + i, center.getY() + radius)){
                target.setPosition(center.getX() + i, center.getY() + radius, true);
            }
            if (isValidCoord(center.getX() + i, center.getY() -radius)) {
                target.setPosition(center.getX() + i, center.getY() - radius, true);
            }
        }

        for(int i = -1 * radius; i <= radius; i++){
            if (isValidCoord(center.getX() + radius, center.getY() + i)){
                target.setPosition(center.getX() + radius, center.getY() + i, true);
            }
            if (isValidCoord(center.getX() - radius, center.getY() + i)) {
                target.setPosition(center.getX() - radius , center.getY() + i, true);
            }
        }
        return target;
    }

    /**
     * Factory method to create a TargetCells method from a boolean matrix
     *
     * @param source the matrix from which to build the TargetCells instance
     * @return resulting TargetCells
     * @throws IllegalArgumentException if source is of invalid size
     */
    public static TargetCells fromMatrix(boolean[][] source) throws IllegalArgumentException{
        if(source.length != BOARD_SIZE){
            throw new IllegalArgumentException("Source matrix has invalid number of rows: " + source.length);
        }
        TargetCells target = new TargetCells();
        for(int i = 0; i < BOARD_SIZE; i++){
            if(source[i].length != BOARD_SIZE){
                throw new IllegalArgumentException("Source matrix has invalid number of columns at row " + i + ": " + source[i].length);
            }
            for(int j = 0; j < BOARD_SIZE; j++){
                target.setPosition(i, j, source[i][j]);
            }
        }
        return target;
    }

    /**
     * Factory method to create a TargetCells method from a collection of cells.
     *
     * @param cells the cells whose positions will be marked as targeted
     * @return resulting TargetCells
     */
    public static TargetCells fromCells(Collection<Cell> cells) {
        TargetCells target = new TargetCells();
        for (Cell cell : cells) {
            target.setPosition(cell, true);
        }
        return target;
    }
}
