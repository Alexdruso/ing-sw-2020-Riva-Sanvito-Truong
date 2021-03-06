package it.polimi.ingsw.utils.observer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * This class should be extended by all classes that wish to implement the Observer design pattern.
 * This implementation of the design pattern uses Functional interfaces to define how the Observable should update its
 * observers.
 * @param <T> the type of message that should be passed along during a notify call
 */
public class LambdaObservable<T> {
    /**
     * The list of observers
     */
    private final Map<LambdaObserver, BiConsumer<LambdaObserver, T>> observers = new HashMap<>();

    /**
     * This method can be used to register an observer on the current object
     *
     * @param observer the observer to be registered
     * @param lambda the method to be run whenever the Observable wants to send a notify to the observer
     */
    public void addObserver(LambdaObserver observer, BiConsumer<LambdaObserver, T> lambda){
        synchronized (observers) {
            observers.put(observer, lambda);
            observers.notifyAll();
        }
    }

    /**
     * This method can be used to de-register an observer from the current object
     *
     * @param observer the observer to be de-registered
     */
    public void removeObserver(LambdaObserver observer){
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
     * @param message the message that is sent to all observers
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
            for(Map.Entry<LambdaObserver, BiConsumer<LambdaObserver, T>> entry: observers.entrySet()){
                entry.getValue().accept(entry.getKey(), message);
            }
        }
    }

}
