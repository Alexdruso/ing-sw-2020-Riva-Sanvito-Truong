package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.model.gods.GodCard;
import it.polimi.ingsw.model.workers.WorkerID;
import it.polimi.ingsw.utils.networking.ControllerHandleable;
import it.polimi.ingsw.utils.networking.TransmittableHandler;
import it.polimi.ingsw.view.View;

/**
 * This immutable class represents a command to set the initial position of a worker.
 */
public class ClientSetWorkerPositionMessage extends ClientMessage implements ControllerHandleable {
    private final WorkerID workerID;

    private final int targetX;
    private final int targetY;

    /**
     * Class constructor
     *
     * @param workerID the worker id
     * @param targetX  the target x
     * @param targetY  the target y
     */
    public ClientSetWorkerPositionMessage(WorkerID workerID, int targetX, int targetY){
       super();
       this.workerID = workerID;
       this.targetX = targetX;
       this.targetY = targetY;
   }

    /**
     * Gets the worker id.
     *
     * @return the worker id
     */
    public WorkerID getWorkerID() {
        return workerID;
    }

    /**
     * Gets the target x.
     *
     * @return the target x
     */
    public int getTargetX() {
        return targetX;
    }

    /**
     * Gets the target y.
     *
     * @return the target y
     */
    public int getTargetY() {
        return targetY;
    }

    @Override
    public boolean handleTransmittable(Controller handler, View view, User user) {
        return false;
    }

    /**
     * This method returns the type of the current action
     * @return the type of the current action, as an instance of PlayerActions
     */
    @Override
    public ClientMessages getMessageType() {
        return ClientMessages.SET_WORKER_POSITION;
    }
}
