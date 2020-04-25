package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.model.board.Component;
import it.polimi.ingsw.model.workers.WorkerID;
import it.polimi.ingsw.utils.networking.ClientHandleable;

public class ServerBuildMessage extends ServerMessage implements ClientHandleable {

    /**
     * The X coordinate of the cell on which the worker built
     */
    public final int targetCellX;

    /**
     * The Y coordinate of the cell on which the worker built
     */
    public final int targetCellY;

    /**
     * The component that is built on the cell
     */
    public final Component component;

    /**
     * The level that is built by the worker.
     */
    public final int builtLevel;

    /**
     * The worker who performed the build
     */
    public final WorkerID performer;

    /**
     * Constructor, stores all the variables by reference
     *
     * @param user        the user who ordered the build
     * @param targetCellX The x coordinate of the cell on which the worker built
     * @param targetCellY The y coordinate of the cell on which the worker built
     * @param component   The component built on the cell
     * @param builtLevel  The level built by the worker
     * @param performer   The worker who performed the build
     */
    public ServerBuildMessage(User user, int targetCellX, int targetCellY,
                              Component component, int builtLevel, WorkerID performer) {
        super(user);
        this.targetCellX = targetCellX;
        this.targetCellY = targetCellY;
        this.component = component;
        this.builtLevel = builtLevel;
        this.performer = performer;
    }

    /**
     * This method returns the type of the current action
     *
     * @return the type of the current action, as an instance of PlayerActions
     */
    @Override
    public ServerMessages getMessageType() {
        return ServerMessages.BUILD;
    }

    @Override
    public boolean handleTransmittable(Client handler) {
        return false;
    }
}
