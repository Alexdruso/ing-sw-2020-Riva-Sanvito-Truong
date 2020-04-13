package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.utils.messages.ClientMessage;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.StatusMessages;
import it.polimi.ingsw.utils.networking.Connection;

public class View extends Observable<ClientMessage> implements Observer<Object> {
    private final User user;
    private final Connection connection;
    @Override
    public void update(Object message) throws UnsupportedOperationException{
        //TODO: define the type for Observer
        throw new UnsupportedOperationException();
    }

    public void handleMessage(StatusMessages message) throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

    public View(Connection connection, String nickname){
        this.connection = connection;
        this.user = new User(nickname);
    }

    public User getUser() {
        return user;
    }
}
