package it.polimi.ingsw.view;

import it.polimi.ingsw.utils.playercommands.PlayerCommand;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.StatusMessages;

public class View extends Observable<PlayerCommand> implements Observer<Object> {
    @Override
    public void update(Object message) throws UnsupportedOperationException{
        //TODO: define the type for Observer
        throw new UnsupportedOperationException();
    }

    public void handleMessage(StatusMessages message) throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }
}
