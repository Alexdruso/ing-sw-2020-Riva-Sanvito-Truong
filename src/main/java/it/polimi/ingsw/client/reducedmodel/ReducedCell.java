package it.polimi.ingsw.client.reducedmodel;

import java.util.Optional;

public class ReducedCell {
    private final int x;
    private final int y;

    private int towerHeight;
    private boolean hasDome;
    private boolean isHighlighted;
    private Optional<ReducedWorker> worker;

    public ReducedCell(int x, int y) {
        this.x = x;
        this.y = y;
        towerHeight = 0;
        hasDome = false;
        isHighlighted = false;
        worker = Optional.empty();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTowerHeight() {
        return towerHeight;
    }

    public boolean hasDome() {
        return hasDome;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public Optional<ReducedWorker> getWorker() {
        return worker;
    }

    public void setTowerHeight(int towerHeight) {
        this.towerHeight = towerHeight;
    }

    public void setHasDome(boolean hasDome) {
        this.hasDome = hasDome;
    }

    public void setHighlighted(boolean highlighted) {
        isHighlighted = highlighted;
    }

    public void setWorker(ReducedWorker worker) {
        this.worker = Optional.of(worker);
    }

    public void setNoWorker() {
        worker = Optional.empty();
    }
}
