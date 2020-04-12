package it.polimi.ingsw.utils.messages;

/**
 * This immutable class represents a command given by set the players count in a new game.
 */
public class ClientSetPlayersCountMessage extends ClientMessage {
    private final int playersCount;

    /**
     * Class constructor
     *
     * @param playersCount the players count
     */
    public ClientSetPlayersCountMessage(int playersCount){
       super();
       this.playersCount = playersCount;
   }

    /**
     * Gets the players count chosen by the user.
     *
     * @return the players count
     */
    public int getPlayersCount() {
        return playersCount;
   }

    /**
     * This method returns the type of the current action
     * @return the type of the current action, as an instance of PlayerActions
     */
    @Override
    public ClientMessages getMessageType() {
        return ClientMessages.SET_PLAYERS_COUNT;
    }
}
