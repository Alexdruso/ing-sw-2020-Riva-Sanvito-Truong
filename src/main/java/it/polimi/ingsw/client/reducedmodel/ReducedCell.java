package it.polimi.ingsw.client.reducedmodel;

import java.util.Optional;

/**
 * A representation of a cell of the board, reduced with respect to the server's Cell to contain only the information required by the client.
 */
public class ReducedCell {
    private final int x;
    private final int y;

    private int towerHeight;
    private boolean hasDome;
    private boolean isHighlighted;
    private Optional<ReducedWorker> worker;


    /**
     * Instantiates a new Reduced cell.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public ReducedCell(int x, int y) {
        this.x = x;
        this.y = y;
        towerHeight = 0;
        hasDome = false;
        isHighlighted = false;
        worker = Optional.empty();
    }

    /**
     * Gets the x coordinate.
     *
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y coordinate.
     *
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the tower height.
     *
     * @return the tower height
     */
    public int getTowerHeight() {
        return towerHeight;
    }

    /**
     * Checks if the cell has a dome built in it.
     *
     * @return whether the cell has a dome built in it
     */
    public boolean hasDome() {
        return hasDome;
    }

    /**
     * Check if the cell is in an highlighted state.
     *
     * @return whether the cell is in an highlighted state
     */
    public boolean isHighlighted() {
        return isHighlighted;
    }

    /**
     * Gets the worker on the cell.
     *
     * @return the worker; if no worker is present, Optional.empty() is returned
     */
    public Optional<ReducedWorker> getWorker() {
        return worker;
    }

    /**
     * Sets the tower height.
     *
     * @param towerHeight the tower height
     */
    public void setTowerHeight(int towerHeight) {
        this.towerHeight = towerHeight;
    }

    /**
     * Sets if the cell has a dome built on it.
     *
     * @param hasDome whether the cell has a dome built on it
     */
    public void setHasDome(boolean hasDome) {
        this.hasDome = hasDome;
    }

    /**
     * Sets the highlighted state of the cell.
     *
     * @param highlighted whether the cell is to be marked as highlighted
     */
    public void setHighlighted(boolean highlighted) {
        isHighlighted = highlighted;
    }

    /**
     * Puts a worker on the cell.
     *
     * @param worker the worker
     */
    public void setWorker(ReducedWorker worker) {
        this.worker = Optional.of(worker);
    }

    /**
     * Removes the worker from the cell, if any is present.
     */
    public void setNoWorker() {
        worker = Optional.empty();
    }
}
