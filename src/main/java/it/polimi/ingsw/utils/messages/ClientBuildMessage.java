package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.model.board.Component;
import it.polimi.ingsw.utils.networking.ControllerHandleable;
import it.polimi.ingsw.view.View;

/**
 * This immutable class represents a command given by the player to make a worker build on a given cell.
 */
public class ClientBuildMessage implements ClientMessage, ControllerHandleable{
    /**
     * The X coordinate of the cell on which the worker intends to build
     */
    public final int targetCellX;

    /**
     * The Y coordinate of the cell on which the worker intends to build
     */
    public final int targetCellY;

    /**
     * The component that is to be built on the cell
     */
    public final ReducedComponent component;

    /**
     * The level that is to be built by the worker.
     */
    public final int builtLevel;

    /**
     * The worker who is to perform the build
     */
    public final ReducedWorkerID workerID;

    /**
     * Constructor, stores all the variables by reference
     *
     * @param targetCellX The x coordinate of the cell on which the worker built
     * @param targetCellY The y coordinate of the cell on which the worker built
     * @param component   The component built on the cell
     * @param builtLevel  The level built by the worker
     * @param workerID    The worker who performed the build
     */
    public ClientBuildMessage(int targetCellX, int targetCellY,
                              ReducedComponent component, int builtLevel, ReducedWorkerID workerID) {
        super();
        this.targetCellX = targetCellX;
        this.targetCellY = targetCellY;
        this.component = component;
        this.builtLevel = builtLevel;
        this.workerID = workerID;
    }

    @Override
    public boolean handleTransmittable(Controller handler, View view, User user) {
        handler.dispatchBuildAction(this, view, user);
        return true;
    }
}
