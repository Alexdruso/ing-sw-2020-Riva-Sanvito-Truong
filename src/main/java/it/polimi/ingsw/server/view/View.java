package it.polimi.ingsw.server.view;

import it.polimi.ingsw.server.controller.User;
import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.utils.networking.transmittables.DisconnectionMessage;
import it.polimi.ingsw.utils.networking.transmittables.StatusMessages;
import it.polimi.ingsw.utils.networking.transmittables.Transmittable;
import it.polimi.ingsw.utils.networking.transmittables.clientmessages.ClientMessage;
import it.polimi.ingsw.utils.observer.LambdaObservable;
import it.polimi.ingsw.utils.observer.LambdaObserver;

/**
 * This class represents the View of the MVC Architectural pattern, bound to a specific Connection (thus to a specific User).
 * It observes the Connection and the Model and gets notified whenever the model evolves or the client sends a message.
 */
public class View extends LambdaObservable<ViewClientMessage> implements LambdaObserver {
    /**
     * The user owning the view.
     */
    private final User user;

    /**
     * The connection to the client.
     */
    private final Connection connection;

    /**
     * Handler of a message coming from the client.
     *
     * @param message a message coming from the client
     */
    public void updateFromClient(Transmittable message) {
        this.notify(
                new ViewClientMessage((ClientMessage) message, this, this.getUser())
        );
    }

    /**
     * Handler of a disconnect message coming from the client.
     *
     * @param message a message coming from the client
     */
    public void updateFromClient(DisconnectionMessage message) {
        connection.close();
        this.updateFromClient((Transmittable) message);
    }

    /**
     * Handler of a message coming from the game.
     *
     * @param message a message coming from the game
     */
    public void updateFromGame(Transmittable message) {
        this.connection.send(message);
    }

    /**
     * Handler of a disconnect message coming from the game.
     */
    public void requestDisconnection() {
        connection.close();
    }

    /**
     * Handler of a status message.
     *
     * @param message a message coming from the controller
     */
    public void handleStatusMessage(StatusMessages message) {
        this.connection.send(message);
    }

    /**
     * Constructor of the view.
     *
     * @param connection the connection inherent to the view
     * @param nickname   the nickname of the User owning the view
     */
    public View(Connection connection, String nickname) {
        this.connection = connection;
        connection.addObserver(
                this,
                (lambdaObserver, transmittable) ->
                {
                    if (transmittable instanceof DisconnectionMessage) {
                        ((View) lambdaObserver).updateFromClient((DisconnectionMessage) transmittable);
                    } else {
                        ((View) lambdaObserver).updateFromClient(transmittable);
                    }
                }
        );
        this.user = new User(nickname);
    }

    /**
     * Getter of the user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }
}
