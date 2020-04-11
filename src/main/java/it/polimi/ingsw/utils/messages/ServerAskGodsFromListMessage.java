package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.model.gods.GodCard;
import it.polimi.ingsw.server.Match;

import java.util.ArrayList;
import java.util.List;

public class ServerAskGodsFromListMessage extends ServerMessage{

    private List<GodCard> godList;

    public ServerAskGodsFromListMessage(Match match, List<GodCard> godList){
        super(match);
        this.godList = godList;
    }

    public List<GodCard> getGodList(){
        return (List<GodCard>)((ArrayList<GodCard>)godList).clone();
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
