package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.utils.messages.ClientMessage;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.StatusMessages;
import it.polimi.ingsw.utils.messages.ServerMessage;
import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.utils.networking.Transmittable;

public class View extends Observable<ViewClientMessage> implements Observer<Transmittable>{
    private final User user;
    private final Connection connection;
    @Override
    public void update(Transmittable message) throws UnsupportedOperationException{
        //TODO: define the type for Observer
        throw new UnsupportedOperationException();
    }

    public void handleMessage(StatusMessages message) throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

    public View(Connection connection, String nickname){
        this.connection = connection;
        connection.addObserver(this);
        this.user = new User(nickname);
    }

    public User getUser() {
        return user;
    }
}
