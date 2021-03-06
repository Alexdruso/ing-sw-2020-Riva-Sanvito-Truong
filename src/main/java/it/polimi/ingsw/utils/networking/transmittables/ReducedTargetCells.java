package it.polimi.ingsw.utils.networking.transmittables;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * A reduced version of the TargetCells implementation made to be sent over the air
 */
public class ReducedTargetCells implements Serializable {
    private final int boardSize;
    private final boolean[][] targets;

    /**
     * Class constructor which creates a ReducedTargetCells instance from a boolean matrix
     *
     * @param targets the matrix
     */
    public ReducedTargetCells(boolean[][] targets) {
        this.boardSize = targets.length;
        this.targets = targets.clone();
    }

    /**
     * This method checks if the ReducedTargetCells has any cell set to be targeted
     *
     * @return false if there are targeted cells, true otherwise
     */
    public boolean isEmpty() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (targets[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Retrieves the targeted status of the cell at a given position
     *
     * @param x int representing the x coordinate of the cell to be checked
     * @param y int representing the y coordinate of the cell to be checked
     * @return true if the cell is targeted, false otherwise
     */
    public boolean getPosition(int x, int y) {
        return targets[x][y];
    }

    /**
     * Overridden equals method
     *
     * @param o the object to compare
     * @return true if the objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReducedTargetCells that = (ReducedTargetCells) o;
        return boardSize == that.boardSize &&
                Arrays.equals(targets, that.targets);
    }

    /**
     * Overridden hashcode method
     *
     * @return the object hashcode
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(boardSize);
        result = 31 * result + Arrays.hashCode(targets);
        return result;
    }
}
