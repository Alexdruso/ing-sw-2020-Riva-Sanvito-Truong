package it.polimi.ingsw.model;

/**
 * This class represents the game board
 */

public class Board {
    /**
     * This groups all the tiles which make up the board
     */
    private final Cell[][] tiles;

    /**
     * This is the constant lenght of a side of the board
     */
    private static final int dimension = 5;

    /**
     * This method creates a board with dimension*dimension cells
     */
    public Board() {
        this.tiles = new Cell[dimension][dimension];

        //initializes the cells with their coordinates
        for(int x = 0; x < dimension; x++){

            for(int y = 0; y<dimension; y++ ){

                tiles[x][y] = new Cell( x , y );

            }

        }
    }

    /**
     * This method returns the side length of the board
     * @return the side length of the board
     */
    public int getDimension(){
        return dimension;
    }

    /**
     * This method returns the cell in position (x,y)
     * @param x the position of the cell on the x axis
     * @param y the position of the cell on the y axis
     * @return the required cell
     */
    public Cell getCell( int x, int y){
        return tiles[x][y];
    }
}
