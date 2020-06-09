package it.polimi.ingsw.server.view;

import it.polimi.ingsw.server.controller.User;
import it.polimi.ingsw.utils.networking.transmittables.clientmessages.ClientMessage;

/**
 * Allows the View to wrap a ClientMessage with a reference to itself and to the User that triggered the message itself.
 */
public class ViewClientMessage {
    /**
     * The Client message.
     */
    public final ClientMessage clientMessage;
    /**
     * The View that triggered this message.
     */
    public final View view;
    /**
     * The User that triggered this message.
     */
    public final User user;

    /**
     * Instantiates a new ViewClientMessage.
     *
     * @param clientMessage the client message
     * @param view          the view that triggered this message
     * @param user          the user that triggered this message
     */
    public ViewClientMessage(ClientMessage clientMessage, View view, User user) {
        this.clientMessage = clientMessage;
        this.view = view;
        this.user = user;
    }
}
