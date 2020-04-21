package it.polimi.ingsw.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Observable class from the Observer design pattern, written with generics.
 * Source: Emanuele Del Sozzo's Tutor Lessons
 *
 * @param <T> The type of the message that is to be sent on update
 */
public class Observable<T> {
    /**
     * The list of observers
     */
    private final List<Observer<T>> observers = new ArrayList<>();

    /**
     * This method can be used to register an observer on the current object
     *
     * @param observer the observer to be registered
     */
    public void addObserver(Observer<T> observer){
        synchronized (observers) {
            observers.add(observer);
            observers.notifyAll();
        }
    }

    /**
     * This method can be used to de-register an observer from the current object
     *
     * @param observer the observer to be de-registered
     */
    public void removeObserver(Observer<T> observer){
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    /**
     * This method is called when the Observable changes state and needs to update its Observers.
     *
     * @param message the message that is sent to all observers
     */
    protected void notify(T message){
        notify(message, false);
    }

    /**
     * This method is called when the Observable changes state and needs to update its Observers.
     *
     * @param message                   the message that is sent to all observers
     * @param requireAtLeastOneObserver if true, puts the current thread in wait until at least one observer is present
     */
    protected void notify(T message, boolean requireAtLeastOneObserver){
        synchronized (observers) {
            while (requireAtLeastOneObserver && observers.size() == 0) {
                try {
                    observers.wait();
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                }
            }
            for(Observer<T> observer : observers){
                observer.update(message);
            }
        }
    }
}
