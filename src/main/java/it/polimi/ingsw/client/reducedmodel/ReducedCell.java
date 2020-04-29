package it.polimi.ingsw.client.reducedmodel;

import java.util.Optional;
import java.util.Random;

public class ReducedCell {
    private int x;
    private int y;

    private int towerHeight;
    private boolean hasDome;
    private boolean isWalkable;
    private boolean isDomeBuildable;
    private boolean isBlockBuildable;
    private Optional<ReducedWorker> worker;

    public ReducedCell(int x, int y) {
        this.x = x;
        this.y = y;
//        towerHeight = 0;
        hasDome = false;
        worker = Optional.empty();

        Random r = new Random();
        towerHeight = r.nextInt(4);
        hasDome = r.nextInt(100) < 8;
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

    public boolean isWalkable() {
        return isWalkable;
    }

    public boolean isDomeBuildable() {
        return isDomeBuildable;
    }

    public boolean isBlockBuildable() {
        return isBlockBuildable;
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

    public void setWalkable(boolean walkable) {
        isWalkable = walkable;
    }

    public void setDomeBuildable(boolean domeBuildable) {
        isDomeBuildable = domeBuildable;
    }

    public void setBlockBuildable(boolean blockBuildable) {
        isBlockBuildable = blockBuildable;
    }

    public void setWorker(ReducedWorker worker) {
        this.worker = Optional.of(worker);
    }

    public void setNoWorker() {
        worker = Optional.empty();
    }
}
