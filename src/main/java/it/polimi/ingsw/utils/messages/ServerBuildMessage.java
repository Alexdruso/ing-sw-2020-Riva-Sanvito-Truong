package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.networking.ClientHandleable;

/**
 * The type Server build message.
 */
public class ServerBuildMessage implements ServerMessage, ClientHandleable {
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
    public final ReducedComponent component;

    /**
     * The level that is built by the worker.
     */
    public final int builtLevel;

    /**
     * The worker who performed the build
     */
    public final ReducedWorkerID performer;

    /**
     * The user who performed the action
     */
    public final ReducedUser user;

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
    public ServerBuildMessage(ReducedUser user, int targetCellX, int targetCellY,
                              ReducedComponent component, int builtLevel, ReducedWorkerID performer) {
        this.user = user;
        this.targetCellX = targetCellX;
        this.targetCellY = targetCellY;
        this.component = component;
        this.builtLevel = builtLevel;
        this.performer = performer;
    }

    @Override
    public boolean handleTransmittable(Client client) {
        client.getGame().buildComponentInCell(targetCellX, targetCellY, component, builtLevel);
//         ReducedCell targetCell = handler.getGame().getCell(targetCellX, targetCellY);
//         handler.getGame().move(woker, sourceCell, targetCell);

//        handler.addChangedCell(sourceCell);
//        handler.addChangedCell(targetCell);
        return false;
    }
}
