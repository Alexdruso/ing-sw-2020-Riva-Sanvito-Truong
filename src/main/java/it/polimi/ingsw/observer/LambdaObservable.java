package it.polimi.ingsw.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class LambdaObservable {
    /**
     * The list of observers
     */
    private final List<LambdaObserver> observers = new ArrayList<>();

    /**
     * This method can be used to register an observer on the current object
     *
     * @param observer the observer to be registered
     */
    public void addObserver(LambdaObserver observer){
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
    public void removeObserver(LambdaObserver observer){
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    /**
     * This method is called when the Observable changes state and needs to update its Observers.
     *
     * @param lambda the function to be executed on all observers
     */
    protected void notify(Consumer<LambdaObserver> lambda){
        notify(lambda, false);
    }

    /**
     * This method is called when the Observable changes state and needs to update its Observers.
     *
     * @param lambda the function to be executed on all observers
     * @param requireAtLeastOneObserver if true, puts the current thread in wait until at least one observer is present
     */
    protected void notify(Consumer<LambdaObserver> lambda, boolean requireAtLeastOneObserver){
        synchronized (observers) {
            while (requireAtLeastOneObserver && observers.size() == 0) {
                try {
                    observers.wait();
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                }
            }
            for(LambdaObserver observer : observers){
                lambda.accept(observer);
            }
        }
    }

}
