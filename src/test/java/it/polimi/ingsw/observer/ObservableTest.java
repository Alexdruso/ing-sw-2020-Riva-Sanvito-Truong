package it.polimi.ingsw.observer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ObservableTest {

    @Test
    void behavioralTest() {
        //as we cannot see the rep or internal state of this class, we are going to test it from a behavioral pov
        //initialize our test class
        Observable<Object> myObservable = new Observable<>();
        //mock two observers to check various combination
        Observer<Object> observer1 = mock(Observer.class);
        Observer<Object> observer2 = mock(Observer.class);
        //Create an object to pass as message
        Object message = new Object();
        //register the first mock to observable
        myObservable.addObserver(observer1);
        //try to notify it
        myObservable.notify(message);
        //check our observer was notified
        verify(observer1).update(message);
        //if ok then remove
        myObservable.removeObserver(observer1);
        //change object
        message = new Object();
        //try to notify again
        myObservable.notify(message);
        //check that now there was no update called
        verify(observer1, times(0)).update(message);
        //change object
        message = new Object();
        //register two observers
        myObservable.addObserver(observer1);
        myObservable.addObserver(observer2);
        //notify
        myObservable.notify(message);
        //check correct method called on mocks
        verify(observer1).update(message);
        verify(observer2).update(message);
        //remove both
        myObservable.removeObserver(observer1);
        myObservable.removeObserver(observer2);
        //change object
        message = new Object();
        //notify
        myObservable.notify(message);
        //check not called
        verify(observer1, times(0)).update(message);
        verify(observer2, times(0)).update(message);

        //we are reasonably sure it works :)
    }
}