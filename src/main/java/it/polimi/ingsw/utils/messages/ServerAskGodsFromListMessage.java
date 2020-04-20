package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.gods.GodCard;
import it.polimi.ingsw.server.Match;
import it.polimi.ingsw.utils.networking.ClientHandleable;
import it.polimi.ingsw.utils.networking.TransmittableHandler;

import java.util.ArrayList;
import java.util.List;

public class ServerAskGodsFromListMessage extends ServerMessage implements ClientHandleable{

    private List<GodCard> godList;

    public ServerAskGodsFromListMessage(List<GodCard> godList){
        super();
        this.godList = godList;
    }

    public List<GodCard> getGodList(){
        return (List<GodCard>)((ArrayList<GodCard>)godList).clone();
    }

    @Override
    public boolean handleTransmittable(Client handler) {
        return false;
    }

    /**
     * This method returns the type of the current action
     *
     * @return the type of the current action, as an instance of PlayerActions
     */
    @Override
    public ServerMessages getMessageType() {
        return ServerMessages.ASK_GOD_FROM_LIST;
    }
}
