package it.polimi.ingsw.model.board;

import it.polimi.ingsw.parsing.ConfigParser;

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

    /**
     * This method checks if the TargetCells has any cell set to be targeted
     * @return true if there are targeted cells, false otherwise
     */
    public boolean isEmpty(){
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                if(targets[j][i]){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method sets all cells to the same value
     * @param isTargeted if true, sets all cells to targeted. Otherwise, if false
     *                   sets all cells to un-targeted
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
     * @param rowIndex
     * @param columnIndex
     * @param isTargeted if true the cell is set to targeted, otherwise to un-targeted
     * @return resulting TargetCells
     */
    public TargetCells setPosition(int rowIndex, int columnIndex, boolean isTargeted){
        targets[columnIndex][rowIndex] = isTargeted;
        return this;
    }

    /**
     * Sets a cell at a given position to the given targeted status
     * @param cell the cell whose coordinates are object of this update
     * @param isTargeted if true the cell is set to targeted, otherwise to un-targeted
     * @return resulting TargetCells
     */
    public TargetCells setPosition(Cell cell, boolean isTargeted){
        targets[cell.x][cell.y] = isTargeted;
        return this;
    }

    /**
     * Retrieves the targeted status of the cell at a given position
     * @param rowIndex
     * @param columnIndex
     * @return true if the cell is targeted, false otherwise
     */
    public boolean getPosition(int rowIndex, int columnIndex){
        return targets[columnIndex][rowIndex];
    }

    /**
     * Sets an entire row's targeted status
     * @param rowIndex
     * @param isTargeted if true the cells are set to targeted, otherwise to un-targeted
     * @return resulting TargetCells
     */
    public TargetCells setRow(int rowIndex, boolean isTargeted) {
        for(int i = 0; i < BOARD_SIZE; i++){
            targets[rowIndex][i] = isTargeted;
        }
        return this;
    }

    /**
     * Sets an entire row's targeted status
     * @param columnIndex
     * @param isTargeted if true the cells are set to targeted, otherwise to un-targeted
     * @return resulting TargetCells
     */
    public TargetCells setColumn(int columnIndex, boolean isTargeted) {
        for(int i = 0; i < BOARD_SIZE; i++){
            targets[i][columnIndex] = isTargeted;
        }
        return this;
    }

    /**
     * Sets the edge cells' targeted status
     * @param isTargeted if true the cells are set to targetd, otherwise to un-targeted
     * @return resulting TargetCells
     */
    public TargetCells setEdges(boolean isTargeted){
        for(int i = 0; i < BOARD_SIZE; i++){
            //Set first and last rows
            targets[0][i] = isTargeted;
            targets[BOARD_SIZE][i] = isTargeted;
        }

        for(int i = 1; i < BOARD_SIZE - 1; i++){
            //Set first and last columns
            targets[i][0] = isTargeted;
            targets[i][BOARD_SIZE] = isTargeted;
        }
        return this;
    }

    /**
     * Sets this to be the intersection of this and other.
     * Cells in this after the method call with be targeted if and only if they were
     * previously targeted and if they are targeted by other
     * @param other the other TargetCells instances with which apply the intersection
     * @return resulting TargetCells
     */
    public TargetCells intersect(TargetCells other){
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                targets[j][i] = targets[j][i] && other.getPosition(j, i);
            }
        }
        return this;
    }

    /**
     * Sets this to be the union of this and other.
     * Cells in this after the method call with be targeted either if they were
     * previously targeted or if they are targeted by other
     * @param other the other TargetCells instances with which apply the union
     * @return resulting TargetCells
     */
    public TargetCells union(TargetCells other){
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                targets[j][i] = targets[j][i] || other.getPosition(j, i);
            }
        }
        return this;
    }

    /**
     * Factory method which creates a new TargetCells that sets to targeted all the cells around the center
     * with Manhattan distance of radius. This operation excludes the center itself.
     * @param center The center cell of the targeted area
     * @param radius The Manhattan distance around the center within which all cells will be targeted,
     *               excluding the center.
     * @return the resulting TargetCells
     * @throws IllegalArgumentException if the radius selects cells outside of the board or if the radius is negative
     */
    public static TargetCells fromCellAndRadius(Cell center, int radius) throws IllegalArgumentException{
        if(radius > BOARD_SIZE / 2){
            throw new IllegalArgumentException("Radius is outside Board");
        }
        if(radius < 0){
            throw new IllegalArgumentException("Negative Radius");
        }
        TargetCells target = new TargetCells();
        for(int i = -1 * radius; i <= radius; i++){
            for(int j = -1 * radius; j <= radius; j++){
                if(i == 0 && j == 0){
                    continue;
                }
                if (center.x + i < 0 || center.x + i > BOARD_SIZE || center.y + j < 0 || center.y + j > BOARD_SIZE){
                    continue;
                }
                target.setPosition(center.x + i, center.y + j, true);
            }
        }
        return target;
    }

    /**
     * Factory method to create a TargetCells method from a boolean matrix
     * @param source the matrix from which to build the TargetCells instance
     * @throws IllegalArgumentException if source is of invalid size
     * @return resulting TargetCells
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
                target.setPosition(j, i, source[j][i]);
            }
        }
        return target;
    }
}
