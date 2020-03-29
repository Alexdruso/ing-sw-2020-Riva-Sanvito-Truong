package it.polimi.ingsw.view;

import it.polimi.ingsw.model.playercommands.PlayerCommand;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.StatusMessages;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class View extends Observable<PlayerCommand> implements Observer<Object> {
    @Override
    public void update(Object message) throws UnsupportedOperationException{
        //TODO: define the type for Observer
        throw new UnsupportedOperationException();
    }

    public void handleMessage(StatusMessages message){
        throw new NotImplementedException();
    }
}
