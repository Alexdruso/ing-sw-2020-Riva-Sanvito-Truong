package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.observer.LambdaObservable;
import it.polimi.ingsw.observer.LambdaObserver;
import it.polimi.ingsw.utils.StatusMessages;
import it.polimi.ingsw.utils.messages.ClientMessage;
import it.polimi.ingsw.utils.messages.DisconnectMessage;
import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.utils.networking.Transmittable;

public class View extends LambdaObservable<ViewClientMessage> implements LambdaObserver {
    /**
     * The user owning the view
     */
    private final User user;

    /**
     * The connection to the client
     */
    private final Connection connection;

    /**
     * Handler of a message coming from the client
     *
     * @param message a message coming from the client
     */
    public void updateFromClient(Transmittable message) {
        this.notify(new ViewClientMessage((ClientMessage) message, this, this.getUser()));
    }

    /**
     * Handler of a disconnect message coming from the client
     *
     * @param message a message coming from the client
     */
    public void updateFromClient(DisconnectMessage message) {
        connection.close();
        this.notify(new ViewClientMessage((ClientMessage) message, this, this.getUser()));
    }

    /**
     * Handler of a message coming from the game
     *
     * @param message a message coming from the game
     */
    public void updateFromGame(Transmittable message) {
        this.connection.send(message);
    }

    /**
     * Handler of a disconnect message coming from the game
     *
     * @param message a message coming from the game
     */
    public void updateFromGame(DisconnectMessage message) {
        connection.close(message);
    }

    /**
     * Handler of a status message
     *
     * @param message a message coming from the controller
     * @throws UnsupportedOperationException
     */
    public void handleMessage(StatusMessages message) throws UnsupportedOperationException {
        this.connection.send(message);
    }

    /**
     * Constructor of the view
     *
     * @param connection the connection inherent to the view
     * @param nickname   the nickname of the User owning the view
     */
    public View(Connection connection, String nickname) {
        this.connection = connection;
        connection.addObserver(this, (lambdaObserver, transmittable) ->
                ((View) lambdaObserver).updateFromClient(transmittable));
        this.user = new User(nickname);
    }

    /**
     * Getter of the user
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }
}
