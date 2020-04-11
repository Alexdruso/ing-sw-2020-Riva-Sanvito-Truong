package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.model.workers.WorkerID;
import it.polimi.ingsw.server.Match;

public class ServerAskWorkerPositionMessage extends ServerMessage{

    public final WorkerID worker;
    public final User user;
    public ServerAskWorkerPositionMessage(Match match, WorkerID worker, User user){
        super(match);
        this.worker = worker;
        this.user = user;
    }

    /**
     * This method returns the type of the current action
     *
     * @return the type of the current action, as an instance of PlayerActions
     */
    @Override
    public ServerMessages getMessageType() {
        return null;
    }
}
