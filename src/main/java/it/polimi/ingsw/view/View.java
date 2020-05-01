package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.observer.LambdaObservable;
import it.polimi.ingsw.observer.LambdaObserver;
import it.polimi.ingsw.utils.StatusMessages;
import it.polimi.ingsw.utils.messages.ClientMessage;
import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.utils.networking.Transmittable;

public class View extends LambdaObservable<ViewClientMessage> implements LambdaObserver {
    private final User user;
    private final Connection connection;

    public void updateFromClient(Transmittable message) {
        this.notify(new ViewClientMessage((ClientMessage) message, this, this.getUser()));
    }

    public void updateFromGame(Transmittable message) {
        this.connection.send(message);
    }

    public void handleMessage(StatusMessages message) throws UnsupportedOperationException{
        this.connection.send(message);
    }

    public View(Connection connection, String nickname){
        this.connection = connection;
        connection.addObserver(this, (lambdaObserver, transmittable) ->
                ((View)lambdaObserver).updateFromClient(transmittable));
        this.user = new User(nickname);
    }

    public User getUser() {
        return user;
    }
}
